package net.chenxiy.bilimusic.viewmodel;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.session.MediaSession;
import android.os.AsyncTask;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import net.chenxiy.bilimusic.Constants;
import net.chenxiy.bilimusic.Repository;
import net.chenxiy.bilimusic.mediaclient.MediaBrowserHelper;
import net.chenxiy.bilimusic.mediaclient.MediaBrowserHelperCallback;
import net.chenxiy.bilimusic.network.biliapi.pojo.av.AvData;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content.FavSong;
import net.chenxiy.bilimusic.service.MediaService;
import net.chenxiy.bilimusic.view.BottomSheetPlayList;
import net.chenxiy.bilimusic.view.PlayingActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class MainActivityViewModel extends AndroidViewModel {
    private static final String TAG = "MainActivityViewModelLog";
    private static UpdateUIBroadcastReceiver mUpdateUIBroadcastReceiver;
    private static UpdatePlayListBroadcastReceiver mUpdatePlayListBroadcastReceiver;
    private static UpdateSeekBarBroadcastReceiver mUpdateSeekBarBroadcastReceiver;
    private static UpdateRepeatModeBroadcastReceiver mUpdateRepeatModeBroadcastReceiver;
    private static LoadNowPlayingAvDataFromId loadNowPlayingAvDataFromId;
    public  static MediaBrowserHelper appMediaBrowserHelper;
    public  static MutableLiveData<AvData> nowPlayingAvData=new MutableLiveData<>();
    public  static MutableLiveData<Boolean> isPlaying=new MutableLiveData<>();
    public static MutableLiveData<Long> playProgress=new MutableLiveData<>();
    public static MutableLiveData<Long> maxProgress=new MutableLiveData<>();
    public static MutableLiveData<Long> bufferPosition=new MutableLiveData<>();
    public static MutableLiveData<Integer> repeatMode=new MutableLiveData<>();
    //这里的playlist跟queue同步，也是反的
    public static MutableLiveData<ArrayList<AvData>> currentPlayList=new MutableLiveData<>();//TODO:shared preference store last playList

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

           start();

            Log.d(TAG, "MainActivityViewModel: Create");


    }



    @NonNull
    private void initService(Context context){
        appMediaBrowserHelper=new MediaBrowserHelper(context, MediaService.class);
        appMediaBrowserHelper.setMediaBrowserHelperCallback(new MediaBrowserHelperCallback() {
            @Override
            public void onPlaybackStateChanged(PlaybackStateCompat state) {
                if(state.getState()==PlaybackStateCompat.STATE_PLAYING){
                    isPlaying.postValue(true);
                }else{
                    isPlaying.postValue(false);
                }
            }

            @Override
            public void onMediaControllerConnected(MediaControllerCompat mediaController) {
                Log.d(TAG, "onMediaControllerConnected: ");
                refreshPlayList(mediaController.getQueue());
                if(mediaController.getPlaybackState()!=null&&
                        mediaController.getPlaybackState().getState()==PlaybackStateCompat.STATE_PLAYING){
                    isPlaying.postValue(true);
                }else{
                    isPlaying.postValue(false);
                }
                if(mediaController.getMetadata()!=null) {
                    loadNowPlayingAvDataFromId.execute
                            (Integer.valueOf(
                                    mediaController.getMetadata().getDescription().getMediaId()
                            ));

                }

            }
        });





        //TODO: shared preference update init value of songs and list


        loadNowPlayingAvDataFromId=new LoadNowPlayingAvDataFromId();
        appMediaBrowserHelper.onStart();
    }

    public void start(){

        if(mUpdateUIBroadcastReceiver==null){
            initUpdateUIBroadcastReceiver();
        }
        if(mUpdatePlayListBroadcastReceiver==null){
            initUpdatePlayListBroadcastReceiver();
        }
        if(mUpdateSeekBarBroadcastReceiver==null){
            initUpdateSeekBarBroadcastReceiver();
        }
        if(mUpdateRepeatModeBroadcastReceiver==null){
            initUpdateRepeatModeBroadcastReceiver();
        }

        if(appMediaBrowserHelper==null){ 
            Log.d(TAG, "start new appMediaHelper: ");
            initService(getApplication().getApplicationContext());
        }else if(appMediaBrowserHelper.getController()==null){
            Log.d(TAG, "start: media helper onstart");
            appMediaBrowserHelper.onStart();
        }



    }





    private class UpdateUIBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String newMediaId = intent.getStringExtra(Constants.BROADCAST_MEDIA_ID);
            Log.d(TAG, "onReceive: UpdataUIBroadcast: " + newMediaId);

            loadNowPlayingAvDataFromId.doInBackground(Integer.valueOf(newMediaId));

        }

    }

    private void initUpdateUIBroadcastReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_UPDATE_UI);
        mUpdateUIBroadcastReceiver = new UpdateUIBroadcastReceiver();
        getApplication().getApplicationContext().registerReceiver(mUpdateUIBroadcastReceiver, intentFilter);
    }



    private class UpdatePlayListBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int deleteIndex=intent.getIntExtra(Constants.BROADCAST_DELETE_INDEX,-1);
            if(deleteIndex!=-1){
                if(appMediaBrowserHelper.getController()!=null) {
                    Log.d(TAG, "onReceiveRemoveIndex: "+deleteIndex);
                    ArrayList<AvData> list= new ArrayList<AvData>(currentPlayList.getValue());


                    list.remove(deleteIndex);//Remove 里边千万不要用Integer，这是一个坑!

                    currentPlayList.postValue(list);
                }
                return;
            }
            Integer index=intent.getIntExtra(Constants.BROADCAST_NOW_PLAY_INDEX,-1);
            if(index!=-1){//the playlist is not empty

                if(appMediaBrowserHelper.getController()!=null) {
                    refreshPlayList(appMediaBrowserHelper.getController().getQueue());
                }

            }else{
                //set the playList Empty
                currentPlayList.postValue(new ArrayList<>());

            }


        }

    }

    private void initUpdatePlayListBroadcastReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_UPDATE_PLAYLIST);
        mUpdatePlayListBroadcastReceiver = new UpdatePlayListBroadcastReceiver();
        getApplication().getApplicationContext().registerReceiver(mUpdatePlayListBroadcastReceiver, intentFilter);
    }


    private void refreshPlayList(List<MediaSessionCompat.QueueItem> queueItems){
        if(queueItems!=null) {
            Log.d(TAG, "refreshPlayList: ");
            LoadPlayListFormQueue(queueItems);
        }

    }

    private void initUpdateSeekBarBroadcastReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_UPDATE_SEEKBAR);
        mUpdateSeekBarBroadcastReceiver = new UpdateSeekBarBroadcastReceiver();
        repeatMode.postValue(MediaService.REPEAT_ALL);
        getApplication().registerReceiver(mUpdateSeekBarBroadcastReceiver, intentFilter);
    }
    private class UpdateSeekBarBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            long progress=intent.getLongExtra(Constants.SEEK_BAR_PROGRESS,0);
            long max=intent.getLongExtra(Constants.SEEK_BAR_MAX,0);
            long mBufferPosition=intent.getLongExtra(Constants.SEEK_BAR_BUFFER_POSITION,0);

            playProgress.postValue(progress);
            maxProgress.postValue(max);
            bufferPosition.postValue(mBufferPosition);




        }

    }

    private void initUpdateRepeatModeBroadcastReceiver(){
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(Constants.ACTION_UPDATE_REPEAT_MODE);
        mUpdateRepeatModeBroadcastReceiver=new UpdateRepeatModeBroadcastReceiver();
        getApplication().registerReceiver(mUpdateRepeatModeBroadcastReceiver,intentFilter);


    }

    private class  UpdateRepeatModeBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Integer repeatModeInteger=intent.getIntExtra(Constants.BROADCAST_REPEAT_MODE,MediaService.REPEAT_ALL);
            repeatMode.postValue(repeatModeInteger);
            Log.d(TAG, "onReceive:repeatMode "+repeatModeInteger);
        }
    }





    private void LoadPlayListFormQueue (List<MediaSessionCompat.QueueItem> list) {

        ArrayList<AvData> newPlayList=new ArrayList<>();
        final Integer[] counter=new Integer[1];
        counter[0]=0;

            for(int i=0;i<list.size();i++){

                newPlayList.add(new AvData());
                MediaSessionCompat.QueueItem item=list.get(i);
                Integer avId=Integer.valueOf(item.getDescription().getMediaId());

                Repository.getInstance(getApplication().getApplicationContext()).getAvDataFromId(i,avId, new Repository.onReceiveAvData() {
                    @Override
                    public void receiveData(Integer i, AvData data) {
                         newPlayList.set(i,data);
                         counter[0]++;
                         if(counter[0]>=list.size()-1) {//quite wired. why???
                             currentPlayList.postValue(newPlayList);
                         }

                    }

                });

            }


    }

    private class LoadNowPlayingAvDataFromId extends AsyncTask<Integer,Void,Void>{
        @Override
        protected Void doInBackground(Integer... integers) {
            Repository.getInstance(getApplication().getApplicationContext()).getAvDataFromId(0,integers[0], new Repository.onReceiveAvData() {
                @Override
                public void receiveData(Integer index,AvData data) {
                    nowPlayingAvData.postValue(data);
                }
            });

            return null;
        }

    }





}
