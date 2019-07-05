package net.chenxiy.bilimusic.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import net.chenxiy.bilimusic.Constants;
import net.chenxiy.bilimusic.DataUtils;
import net.chenxiy.bilimusic.R;
import net.chenxiy.bilimusic.Repository;
import net.chenxiy.bilimusic.network.biliapi.pojo.av.AvData;
import net.chenxiy.bilimusic.player.ExoPlayerAdapter;
import net.chenxiy.bilimusic.player.PlayBackInfoListener;
import net.chenxiy.bilimusic.view.PlayingActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.media.MediaBrowserServiceCompat;
import androidx.media.session.MediaButtonReceiver;

import static net.chenxiy.bilimusic.Constants.MEDIA_QUEUE_POSITION;
import static net.chenxiy.bilimusic.Constants.QUEUE_NEW_PLAYLIST;


public class MediaService extends MediaBrowserServiceCompat {//这个是一个media background service 注意add android manifest
  /*
为了提高用户体验，这里的Queue是当做stack FIFO那样使用的


  */
    public static final List<MediaSessionCompat.QueueItem> mPlaylist = new ArrayList<>();//这里其实只是简单的存储media Id的
    private  static int mQueueIndex = -1;
    private  static MediaMetadataCompat mPreparedMedia;
    private static Bitmap mediaBitmap;
    public static final MutableLiveData<String> NetStatus=new MutableLiveData<>();
    private static final String TAG = "MediaServiceLog";
    public static final Integer REPEAT_ALL=0;
    public static final Integer REPEAT_SINGLE=1;
    public static final Integer REPEAT_SHUFFLE=2;
    public static final String COMMAND_SET_PLAYLIST="setPlayList";
    public static final String COMMAND_DIE="RIP";
    public static final String EXTRA_PLAYLIST="extra_playlist";
    public static final String EXTRA_CURSOR="extra_cursor";
    private  MediaSessionCompat mSession;//-holds the media play sesstion all things communicate with this object
    private  ExoPlayerAdapter mPlayback;
    public  static Integer currentRepeatMode=REPEAT_ALL;
    private  List<Integer> mShufflePlaylistSequence;
    public static Integer AUDIO_QUALITY=Constants.AUDIO_QUALITY_HIGH;
    NotificationManagerCompat notificationManager;
    public  SharedPreferences.OnSharedPreferenceChangeListener spChanged;
    private SharedPreferences sharedPref;
    //    private MyApplication mMyApplication;
//    private MyPreferenceManager mMyPrefManager;
    private boolean mIsServiceStarted;

    @Override
    public void onCreate() {
        super.onCreate();
//        mMyApplication = MyApplication.getInstance();
//        mMyPrefManager = new MyPreferenceManager(this);

        Log.d(TAG, "onCreate: media");
        //Build the MediaSession
        mSession = new MediaSessionCompat(this, TAG);

        mSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |//就是说这个media sesstion 都能参与哪些功能
                // https://developer.android.com/guide/topics/media-apps/mediabuttons#mediabuttons-and-active-mediasessions
                // Media buttons on the device
                // (handles the PendingIntents for MediaButtonReceiver.buildMediaButtonPendingIntent)

                MediaSessionCompat.FLAG_HANDLES_QUEUE_COMMANDS); // Control the items in the queue (aka playlist)
        // See https://developer.android.com/guide/topics/media-apps/mediabuttons for more info on flags

        mSession.setCallback(new MediaSessionCallback());//底下有一个自定义的callback class

        // A token that can be used to create a MediaController for this session
        setSessionToken(mSession.getSessionToken());
        mPlayback = new ExoPlayerAdapter(this, new MediaPlayerListener());

        // Create a new Notification


        createNotificationChannel();


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();

        StrictMode.setThreadPolicy(policy);//service is able to run network request on main thread

        sharedPref =PreferenceManager.getDefaultSharedPreferences(this);
        AUDIO_QUALITY=Integer.valueOf(sharedPref.getString("songQuality",String.valueOf(Constants.AUDIO_QUALITY_HIGH)));
        currentRepeatMode=Integer.valueOf(sharedPref.getString("songLoop",String.valueOf(Constants.AUDIO_QUALITY_HIGH)));

        Log.d(TAG, "onCreate: Audio:"+AUDIO_QUALITY+"  songloop:"+currentRepeatMode);
        Set<String> playListSet=sharedPref.getStringSet("playList",null);
        Integer cursor=sharedPref.getInt("cursor",0);
        if(playListSet!=null) {


           //TODO: initPlayList(playListSet,cursor);
        }




        spChanged=new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                Log.d(TAG, "onSharedPreferenceChanged:"+key);
                if(key.equals("songQuality")){
                    AUDIO_QUALITY=Integer.valueOf(sharedPref.getString("songQuality",String.valueOf(Constants.AUDIO_QUALITY_HIGH)));
                }else if(key.equals("songLoop")){

                    currentRepeatMode=Integer.valueOf(sharedPref.getString("songLoop",String.valueOf(Constants.AUDIO_QUALITY_HIGH)));
                    Log.d(TAG, "onSharedPreferenceChanged: "+currentRepeatMode);
                    Intent intent=new Intent();
                    intent.setAction(Constants.ACTION_UPDATE_REPEAT_MODE);
                    intent.putExtra(Constants.BROADCAST_REPEAT_MODE,currentRepeatMode);
                    sendBroadcast(intent);
                }
            }
        };


        sharedPref.registerOnSharedPreferenceChangeListener(spChanged);

    }

    private void initPlayList(Set<String> playListSet,Integer cursor){
        mPlaylist.clear();
        for(String id:playListSet){
            MediaDescriptionCompat description=new MediaDescriptionCompat.Builder().setMediaId(id).build();
            mPlaylist.add(new MediaSessionCompat.QueueItem(description, description.hashCode()));
        }


        Log.d(TAG, "initPlayList: "+mPlaylist.size());



        if(cursor>=0&&cursor<playListSet.size()){
            mQueueIndex=cursor;
        }

       updateSessionQueue(mPlaylist);

      GetMediaData task=new GetMediaData(){
          @Override
          protected void onPostExecute(MediaMetadataCompat mediaMetadataCompat) {
              super.onPostExecute(mediaMetadataCompat);
              mSession.setMetadata(mediaMetadataCompat);
              Intent intent = new Intent();
              intent.setAction(Constants.ACTION_UPDATE_PLAYLIST);
              intent.putExtra(Constants.BROADCAST_NOW_PLAY_INDEX, mQueueIndex);
              sendBroadcast(intent);
          }
      };
      task.execute(Integer.valueOf(mPlaylist.get(cursor).getDescription().getMediaId()));



    }
    
    private void updateSessionQueue(List<MediaSessionCompat.QueueItem> updatePlaylist){

        mSession.setQueue(updatePlaylist);
        mShufflePlaylistSequence=new ArrayList<>();
        for (int i = 0; i < updatePlaylist.size(); ++i) mShufflePlaylistSequence.add(i);
        Collections.shuffle(mShufflePlaylistSequence);

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {//就是那个手指滑动结束了app
        Log.d(TAG, "onTaskRemoved: stopped");
        super.onTaskRemoved(rootIntent);
        killMySelf();
    }

    private void killMySelf(){
        mPlayback.stop();
        //save current playlist
        SharedPreferences.Editor editor=sharedPref.edit();
        Set<String> playListSet=new HashSet<>();
        for(MediaSessionCompat.QueueItem item:mPlaylist){
            playListSet.add(item.getDescription().getMediaId());
        }

        editor.putStringSet("playList",playListSet);
        editor.putInt("cursor",mQueueIndex);
        editor.apply();
        Log.d(TAG, "killMySelf: "+playListSet.size());
        stopForeground(true);
        stopSelf();
    }

    @Override
    public void onDestroy() {//被后台kill activity执行这个
        mSession.release();//service 死了之后release session
        sharedPref.unregisterOnSharedPreferenceChangeListener(spChanged);//This is important

        Log.d(TAG, "onDestroy: MediaPlayerAdapter stopped, and MediaSession released");
    }


    @Override
    public BrowserRoot onGetRoot(String clientPackageName, int clientUid,
                                 Bundle rootHints) {

        // (Optional) Control the level of access for the specified package name.
        // You'll need to write your own logic to do this.
        if (clientPackageName.equals(getApplicationContext().getPackageName())) {
            // Returns a root ID that clients can use with onLoadChildren() to retrieve
            // the content hierarchy.
            return new BrowserRoot("MY_MEDIA_ROOT_ID", null);
        } else {
            // Clients can connect, but this BrowserRoot is an empty hierachy
            // so onLoadChildren returns nothing. This disables the ability to browse for content.
            return new BrowserRoot("MY_EMPTY_MEDIA_ROOT_ID", null);
        }
    }

    @Override
    public void onSubscribe(String id, Bundle option) {//on controller connected

        mSession.setMetadata(mPreparedMedia);
        updateSessionQueue(mPlaylist);

//        if(mPlayback.isPlaying()){
//            mSession.setPlaybackState();
//        }else{
//            mSession.setPlaybackState();
//        }

    }

    @Override
    public void onLoadChildren(@NonNull String s, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {//service compat
        Log.d(TAG, "onLoadChildren: called: " + s + ", " + result);

        //  Browsing not allowed
        if (TextUtils.equals("MY_EMPTY_MEDIA_ROOT_ID", s)) {
            result.sendResult(null);
            return;
        }

        List<MediaBrowserCompat.MediaItem> avPlayList=new ArrayList<>();
        for(MediaSessionCompat.QueueItem queueItem:mPlaylist){
            avPlayList.add(new MediaBrowserCompat.MediaItem(
                    queueItem.getDescription(), MediaBrowserCompat.MediaItem.FLAG_PLAYABLE));
        }


        //这里其实这个sendresult是没有用的，我没有用这种设计，我通过把sesstion之中的作为static存在service里边
        //每次client重连的时候就把这个回加载到session里边去
        result.sendResult(avPlayList); // return media in the current playlist

    }

    public interface GetMediaCallBack{
        void MediaMetadataCompatFinished(MediaMetadataCompat compat);
    }

    public class GetMediaData extends AsyncTask<Integer,Void,MediaMetadataCompat>{

        @Override
        protected MediaMetadataCompat doInBackground(Integer... integers) {
            NetStatus.postValue("Loading");

            AvData data=Repository.getInstance(getBaseContext()).getAvDataFromIdMainThread(integers[0]);
            if (data==null){
                //throw exception
            }else{
                Glide.with(getApplicationContext())
                        .asBitmap()
                        .load(data.getPic())
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                mediaBitmap=resource;
                                createNotification(getApplicationContext());
                            }
                        });

                MediaMetadataCompat dataCompat=  DataUtils.AvData2MediaMetadataCompat(data,getBaseContext(),AUDIO_QUALITY);//TODO: audio quality: shared preference
                NetStatus.postValue("Finish");
                return dataCompat;
            }
            NetStatus.postValue("Finish");
            return null;
        }



    }
    



    public class MediaSessionCallback extends MediaSessionCompat.Callback {//media controller 给这个发信号



        public boolean checkNetStatus(){
            ConnectivityManager cm =
                    (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            return isConnected;
        }

        private void resetPlaylist() {
            mPlaylist.clear();
            mQueueIndex = -1;
        }

        private void getmPreparedMedia(Integer avid,GetMediaCallBack callBack){



           GetMediaData task=new GetMediaData(){
               @Override
               protected void onPostExecute(MediaMetadataCompat mediaMetadataCompat) {
                   super.onPostExecute(mediaMetadataCompat);
                   mPreparedMedia=mediaMetadataCompat;
                   createNotification(getApplicationContext());
                   callBack.MediaMetadataCompatFinished(mediaMetadataCompat);
               }
           };
           task.execute(avid);


        }





        @Override
        public void onPlayFromMediaId(String mediaId, Bundle extras) {//给一个mediaId，然后就play
            Log.d(TAG, "onPlayFromMediaId: CALLED.");
            if(!checkNetStatus()){
                Log.d(TAG, "onPlayFromMediaId: NETerror");
                Toast.makeText(getApplication(),R.string.noInternet,Toast.LENGTH_LONG).show();
                return;
                
            }
            if (extras.getBoolean(QUEUE_NEW_PLAYLIST, false)) {//发过来的bundle这个选项告诉service要重新建一个playlist
                resetPlaylist();
            }

            getmPreparedMedia(Integer.valueOf(mediaId), new GetMediaCallBack() {
                @Override
                public void MediaMetadataCompatFinished(MediaMetadataCompat compat) {
                    mSession.setMetadata(mPreparedMedia);
                    if (!mSession.isActive()) {
                        mSession.setActive(true);
                    }
                    mPlayback.playFromMedia(mPreparedMedia);

                    int newQueuePosition = extras.getInt(MEDIA_QUEUE_POSITION, -1);
                    if (newQueuePosition == -1) {
                        mQueueIndex++;
                    } else {
                        mQueueIndex = extras.getInt(MEDIA_QUEUE_POSITION);
                    }
                    Intent intent = new Intent();
                    intent.setAction(Constants.ACTION_UPDATE_PLAYLIST);
                    intent.putExtra(Constants.BROADCAST_NOW_PLAY_INDEX, mQueueIndex);
                    sendBroadcast(intent);

                }
            });

//            mMyPrefManager.saveQueuePosition(mQueueIndex);
//            mMyPrefManager.saveLastPlayedMedia(mPreparedMedia.getDescription().getMediaId());
        }

        @Override
        public void onCustomAction(String action, Bundle extras) {
            super.onCustomAction(action, extras);
            if(action.equals(MediaService.COMMAND_SET_PLAYLIST)){
                if(!checkNetStatus()){
                    Log.d(TAG, "onPlayFromMediaId: NETerror");
                    Toast.makeText(getApplication(),R.string.noInternet,Toast.LENGTH_LONG).show();
                    return;

                }

                ArrayList<Integer> songs=extras.getIntegerArrayList(MediaService.EXTRA_PLAYLIST);

                mPlaylist.clear();
                for(Integer song:songs){
                    MediaDescriptionCompat description=new MediaDescriptionCompat.Builder().setMediaId(String.valueOf(song)).build();
                    mPlaylist.add(new MediaSessionCompat.QueueItem(description, description.hashCode()));
                }

                Integer cursor=extras.getInt(MediaService.EXTRA_CURSOR,-1);
                if(cursor>=0&&cursor<songs.size()){
                    mQueueIndex=cursor;
                }
                updateSessionQueue(mPlaylist);
                mPreparedMedia=null;
                onPlay();
                if(mPreparedMedia!=null) {//on Play Success
                    Intent intent = new Intent();
                    intent.setAction(Constants.ACTION_UPDATE_PLAYLIST);
                    intent.putExtra(Constants.BROADCAST_NOW_PLAY_INDEX, mQueueIndex);
                    sendBroadcast(intent);
                }
            }else if(action.equals(MediaService.COMMAND_DIE)){
                killMySelf();
            }


        }

        @Override
        public void onAddQueueItem(MediaDescriptionCompat description) {
            
            //add the same media as current playing
            if(mQueueIndex!=-1&&
                    description.getMediaId().equals(mPlaylist.get(mQueueIndex).getDescription().getMediaId())){
                return;
            }
            //delete the old one if the item is already in playList
            onRemoveQueueItem(new MediaDescriptionCompat.Builder()
                    .setMediaId(description.getMediaId()).build());
            mPlaylist.add(mQueueIndex+1,new MediaSessionCompat.QueueItem(description, description.hashCode()));
            //Here the queue is used like a stack, in order to create a better user interaction, first in first out
            Log.d(TAG, "onAddQueueItem: CALLED: list playListSize: " + mPlaylist.size());

            updateSessionQueue(mPlaylist);
            mQueueIndex++;
            mPreparedMedia=null;
            onPlay();

        }

        @Override
        public void onRemoveQueueItem(MediaDescriptionCompat description) {//这里的Description 的格式可以按照这种new MediaDescriptionCompat.Builder().setMediaId(String.valueOf(avid)).build()
            int index=-1;
            for(int i=0;i<mPlaylist.size();i++){
                if(mPlaylist.get(i).getDescription().getMediaId().equals(description.getMediaId())){
                    Log.d(TAG, "onRemoveQueueItem: "+mPlaylist.get(i).getDescription().getMediaId());
                    mPlaylist.remove(i);
                    index=i;
                    break;
                }
            }

            //no item is removed
            if(index==-1){
                return;
            }
            Log.d(TAG, "afterRemoveQueueItem: index:"+index+"queueIndex:"+mQueueIndex);

            updateSessionQueue(mPlaylist);
        if(index==mQueueIndex&&!mPlaylist.isEmpty()){
                mQueueIndex=(mQueueIndex-1)%mPlaylist.size();
                mPreparedMedia=null;
                onPlay();

            }else {
    if (mPlaylist.isEmpty()) {
        mQueueIndex = -1;
        mPreparedMedia = null;
        onStop();
    } else if (index < mQueueIndex) {
        mQueueIndex--;
    }

    Intent intent = new Intent();
    intent.setAction(Constants.ACTION_UPDATE_PLAYLIST);
    if (mQueueIndex != -1) {
        intent.putExtra(Constants.BROADCAST_DELETE_INDEX, index);
    }
    intent.putExtra(Constants.BROADCAST_NOW_PLAY_INDEX, mQueueIndex);
    sendBroadcast(intent);
}
        }


        @Override
        public void onPrepare() {
            if (mQueueIndex < 0 && mPlaylist.isEmpty()) {
                // Nothing to play.
                return;
            }
            Log.d(TAG, "onPrepare: index"+mQueueIndex);

            String mediaId = mPlaylist.get(mQueueIndex).getDescription().getMediaId();
            getmPreparedMedia(Integer.valueOf(mediaId), new GetMediaCallBack() {
                @Override
                public void MediaMetadataCompatFinished(MediaMetadataCompat compat) {
                    if(mPreparedMedia==null){//失效视频
                        onRemoveQueueItem(new MediaDescriptionCompat.Builder()
                                .setMediaId(mediaId).build());

                        return;
                    }

                    mSession.setMetadata(mPreparedMedia);

                    if (!mSession.isActive()) {
                        mSession.setActive(true);
                    }
                    onPlay();
                }
            });

        }

        @Override
        public void onPlay() {

            if (!isReadyToPlay()) {
                // Nothing to play.
                return;
            }

            if (mPreparedMedia == null) {
                onPrepare();
                return;
            }

//            if(mPreparedMedia!=null) {
//                //TODO: here is only for testing
//                String data="--";
//
//                for(int i=0;i<mPlaylist.size();i++){
//
//                    data=data.concat("|").concat(mPlaylist.get(i).getDescription().getMediaId());
//                }
//                Log.d(TAG, "onPlay: PlayListSize" + mPlaylist.size());
//                Log.d(TAG, "onPlay: PlayList "+ data);
//                Log.d(TAG, "onPlay: Cursor "+mQueueIndex);
//                mPlayback.playFromMedia(mPreparedMedia);
//            }else{
//                //TODO:set an error BroadCast
//            }

            if(mPreparedMedia!=null) {//on Play Success
                mPlayback.playFromMedia(mPreparedMedia);
                Intent intent = new Intent();
                intent.setAction(Constants.ACTION_UPDATE_PLAYLIST);
                intent.putExtra(Constants.BROADCAST_NOW_PLAY_INDEX, mQueueIndex);
                sendBroadcast(intent);
            }
//            mMyPrefManager.saveQueuePosition(mQueueIndex);
//            mMyPrefManager.saveLastPlayedMedia(mPreparedMedia.getDescription().getMediaId());
        }

        @Override
        public void onPause() {
            mPlayback.pause();
        }

        @Override
        public void onStop() {
            mPlayback.stop();
            mSession.setActive(false);
        }

        @Override
        public void onSkipToPrevious() {
            Log.d(TAG, "onSkipToPrevious: SKIP TO PREVIOUS");
            // increment and then check using modulus

            if(currentRepeatMode==MediaService.REPEAT_SHUFFLE){
                Integer newQueueIndex=-1;
                for (int i=0;i<mShufflePlaylistSequence.size();i++){
                    if(mShufflePlaylistSequence.get(i)==mQueueIndex){
                        mQueueIndex=mShufflePlaylistSequence.get((i+1)%mShufflePlaylistSequence.size());
                        break;
                    }

                }

            }else {
                skipPrev();
            }
                mPreparedMedia =null;
                onPlay();




        }
        private void skipPrev(){
            if(mPlaylist.size()!=0){
                mQueueIndex = ((mQueueIndex+1) % mPlaylist.size());
            }
        }

        @Override
        public void onSkipToNext() {
            Log.d(TAG, "onSkipToNext: SKIP TO NEXT");

            if(currentRepeatMode==MediaService.REPEAT_SHUFFLE){

                for (int i=0;i<mShufflePlaylistSequence.size();i++){
                    if(mShufflePlaylistSequence.get(i)==mQueueIndex){
                        i = i > 0 ? i - 1 : mShufflePlaylistSequence.size() - 1;
                        mQueueIndex=mShufflePlaylistSequence.get(i);
                        break;
                    }

                }
            }else{
                skipNext();
            }


                mPreparedMedia = null;
                onPlay();



        }

        private void skipNext(){
            mQueueIndex = mQueueIndex > 0 ? mQueueIndex - 1 : mPlaylist.size() - 1;

        }


        @Override
        public void onSkipToQueueItem(long id) {
            super.onSkipToQueueItem(id);
            for(int i=0;i<mPlaylist.size();i++){
                if(mPlaylist.get(i).getDescription().getMediaId().equals(String.valueOf(id))){

                    mQueueIndex=i;
                    mPreparedMedia=null;
                    onPlay();
                    break;
                }
            }

        }

        @Override
        public void onSetRepeatMode(int repeatMode) {
            Intent intent=new Intent();
            intent.setAction(Constants.ACTION_UPDATE_REPEAT_MODE);
            intent.putExtra(Constants.BROADCAST_REPEAT_MODE,repeatMode);
            sendBroadcast(intent);
            currentRepeatMode=repeatMode;

            SharedPreferences.Editor editor=sharedPref.edit();
            editor.putString("songLoop",String.valueOf(repeatMode));
            editor.apply();
            Log.d(TAG, "onSetRepeatMode: "+repeatMode);


        }



        //TODO: update seek bar for not completely loaded audio
        @Override
        public void onSeekTo(long pos) {
            mPlayback.seekTo(pos);
        }

        private boolean isReadyToPlay() {
            return (!mPlaylist.isEmpty());
        }


    }


    public class MediaPlayerListener implements PlayBackInfoListener {//这个是通过exoplayer的callback然后来通过BroadCastReveicer updateUI的

        //  private final ServiceManager mServiceManager;


        MediaPlayerListener() {
            //   mServiceManager = new ServiceManager();
        }

        @Override
        public void updateUI(String newMediaId) {
            Log.d(TAG, "updateUI: CALLED: " + newMediaId);
            Intent intent = new Intent();
            intent.setAction(Constants.ACTION_UPDATE_UI);
            intent.putExtra(Constants.BROADCAST_MEDIA_ID, newMediaId);
            sendBroadcast(intent);
        }

        @Override
        public void onPlaybackStateChange(PlaybackStateCompat state) {
            // Report the state to the MediaSession.
            mSession.setPlaybackState(state);


            if(mPreparedMedia!=null) {
                createNotification(getApplicationContext());
            }



        }

        @Override
        public void onSeekTo(long progress,long bufferPosition, long max) {
            Intent intent = new Intent();
            intent.setAction(Constants.ACTION_UPDATE_SEEKBAR);
            intent.putExtra(Constants.SEEK_BAR_BUFFER_POSITION,bufferPosition);
            intent.putExtra(Constants.SEEK_BAR_PROGRESS, progress);
            intent.putExtra(Constants.SEEK_BAR_MAX, max);
            sendBroadcast(intent);
        }

        @Override
        public void onPlaybackComplete() {
            Log.d(TAG, "onPlaybackComplete: SKIPPING TO NEXT.");

            if(currentRepeatMode==MediaService.REPEAT_SINGLE){
                mSession.getController().getTransportControls().play();

            }else {
                mSession.getController().getTransportControls().skipToNext();
            }





        }


    }




    public void createNotification( Context context) {
        NotificationCompat.Builder builder;




            PendingIntent playingActivity=PendingIntent.getActivity(this,0,
                    new Intent(getApplicationContext(), PlayingActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    ,PendingIntent.FLAG_UPDATE_CURRENT);
            builder=new NotificationCompat.Builder(context,Constants.PLAYER_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setShowWhen(false)
                    .setColor(ContextCompat.getColor(context,R.color.colorTheme))
                    .setOngoing(true)//常驻通知栏
                    .setPriority(NotificationCompat.PRIORITY_MAX)//设置最大优先级
                    .setContentIntent(playingActivity)
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle().
                            setMediaSession(mSession.getSessionToken())
//                            .setShowCancelButton(true)
//                            .setCancelButtonIntent(mainActivity)
                            .setShowActionsInCompactView(0, 1, 2))
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
                    .setCategory(NotificationCompat.CATEGORY_TRANSPORT);

            if(mediaBitmap!=null){
                builder.setLargeIcon(mediaBitmap);
            }else{
                builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.album_empty));//TODO:set default notification
            }
            if(mPreparedMedia!=null){
                builder.setContentTitle(mPreparedMedia.getDescription().getTitle())
                        .setContentText(mPreparedMedia.getString(MediaMetadataCompat.METADATA_KEY_ARTIST));
            }else{
                builder.setContentTitle("Track title")
                        .setContentText("Artist");
                return;
            }

            Integer state=PlaybackStateCompat.STATE_STOPPED;
            if(mSession.getController()!=null&&mSession.getController().getPlaybackState()!=null){
                state=mSession.getController().getPlaybackState().getState();
            }

        builder.addAction(R.drawable.exo_icon_previous, "prev", MediaButtonReceiver.buildMediaButtonPendingIntent(
                MediaService.this,
                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS));

            if((state != null) && (state == PlaybackStateCompat.STATE_PLAYING)){

                   builder.addAction(R.drawable.exo_controls_pause, "pause/play",  MediaButtonReceiver.buildMediaButtonPendingIntent(
                        MediaService.this,
                        PlaybackStateCompat.ACTION_PAUSE));
                //pause
            }else {
                   builder.addAction(R.drawable.exo_controls_play, "pause/play",  MediaButtonReceiver.buildMediaButtonPendingIntent(
                        MediaService.this,
                        PlaybackStateCompat.ACTION_PLAY));
                //play
            }

            builder.addAction(R.drawable.exo_icon_next, "next", MediaButtonReceiver.buildMediaButtonPendingIntent(
                        MediaService.this,
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT));

        Notification notification = builder.build();



        startForeground(Constants.PLAYER_NOTIFICATION_ID,notification);

    }



    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "BiliMusic";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel mChannel = null;
            mChannel = new NotificationChannel(Constants.PLAYER_CHANNEL_ID, name, importance);

            manager.createNotificationChannel(mChannel);
        }

    }




}
