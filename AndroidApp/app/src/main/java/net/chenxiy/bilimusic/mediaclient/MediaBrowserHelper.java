package net.chenxiy.bilimusic.mediaclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.exoplayer2.extractor.flv.FlvExtractor;

import net.chenxiy.bilimusic.R;
import net.chenxiy.bilimusic.service.MediaService;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media.MediaBrowserServiceCompat;


public class MediaBrowserHelper {

    private static final String TAG = "MediaBrowserHelper";

    private final Context mContext;
    private final Class<? extends MediaBrowserServiceCompat> mMediaBrowserServiceClass;

    private MediaBrowserCompat mMediaBrowser;
    private MediaControllerCompat mMediaController;


    private MediaBrowserConnectionCallback mMediaBrowserConnectionCallback;//这里是callback，是这个class的一个subclass
    private final MediaBrowserSubscriptionCallback mMediaBrowserSubscriptionCallback;
    private MediaControllerCallback mMediaControllerCallback;
    private MediaBrowserHelperCallback mMediaBrowserCallback;



    public MediaBrowserHelper(Context context, Class<? extends MediaBrowserServiceCompat> serviceClass) {
        mContext = context;
        mMediaBrowserServiceClass = serviceClass;


        mMediaBrowserConnectionCallback = new MediaBrowserConnectionCallback();
        mMediaBrowserSubscriptionCallback = new MediaBrowserSubscriptionCallback();
        mMediaControllerCallback = new MediaControllerCallback();
        mMediaBrowserCallback = new MediaBrowserHelperCallback() {
            @Override
            public void onPlaybackStateChanged(PlaybackStateCompat state) {

            }

            @Override
            public void onMediaControllerConnected(MediaControllerCompat mediaController) {

            }
        };
    }

    public void setMediaBrowserHelperCallback(MediaBrowserHelperCallback callback){
        mMediaBrowserCallback = callback;
    }

    // Receives callbacks from the MediaController and updates the UI state,
    // i.e.: Which is the current item, whether it's playing or paused, etc.
    private class MediaControllerCallback extends MediaControllerCompat.Callback {

        @Override
        public void onPlaybackStateChanged(@Nullable final PlaybackStateCompat state) {
            Log.d(TAG, "onPlaybackStateChanged: CALLED");
            if(mMediaBrowserCallback != null){
                mMediaBrowserCallback.onPlaybackStateChanged(state);
            }
        }



        // This might happen if the MusicService is killed while the Activity is in the
        // foreground and onStart() has been called (but not onStop()).
        @Override
        public void onSessionDestroyed() {
            onPlaybackStateChanged(null);
        }
    }

    public void subscribeToNewPlaylist(String currentPlaylistId, String newPlaylistId){
        if(!currentPlaylistId.equals("")){
            mMediaBrowser.unsubscribe(currentPlaylistId);
        }
        mMediaBrowser.subscribe(newPlaylistId, mMediaBrowserSubscriptionCallback);
    }

    public void stopService(){
        mMediaController.getTransportControls().sendCustomAction(MediaService.COMMAND_DIE,new Bundle());
    };

    public void onStart() {



        if (mMediaBrowser == null) {

            mContext.startService(new Intent(mContext,mMediaBrowserServiceClass));
            mMediaBrowser =
                    new MediaBrowserCompat(
                            mContext,
                            new ComponentName(mContext, mMediaBrowserServiceClass),
                            mMediaBrowserConnectionCallback,
                            null);
            mMediaBrowser.connect();
        }
        Log.d(TAG, "onStart: CALLED: Creating MediaBrowser, and connecting");
    }

    public void onStop() {
        if (mMediaController != null) {
            mMediaController.unregisterCallback(mMediaControllerCallback);
            mMediaController = null;
        }
        if (mMediaBrowser != null && mMediaBrowser.isConnected()) {
            mMediaBrowser.disconnect();
            mMediaBrowser = null;
        }
        Log.d(TAG, "onStop: CALLED: Releasing MediaController, Disconnecting from MediaBrowser");
    }



    // Receives callbacks from the MediaBrowser when it has successfully connected to the
    // MediaBrowserService (MusicService).
    private class MediaBrowserConnectionCallback extends MediaBrowserCompat.ConnectionCallback {  //这个就是connect to service的时候的callback

        // Happens as a result of onStart().
        @Override
        public void onConnected() {
            Log.d(TAG, "onConnected: CALLED");
            try {
                // Get a MediaController for the MediaSession.
                mMediaController =
                        new MediaControllerCompat(mContext, mMediaBrowser.getSessionToken());//连接后进行controller连接
                mMediaController.registerCallback(mMediaControllerCallback);


            } catch (RemoteException e) {
                Log.d(TAG, String.format("onConnected: Problem: %s", e.toString()));
                throw new RuntimeException(e);
            }

            mMediaBrowser.subscribe(mMediaBrowser.getRoot(), mMediaBrowserSubscriptionCallback);//get playlist
            Log.d(TAG, "onConnected: CALLED: subscribing to: " + mMediaBrowser.getRoot());

            mMediaBrowserCallback.onMediaControllerConnected(mMediaController);
        }


    }



    // Receives callbacks from the MediaBrowser when the MediaBrowserService has loaded new media
    // that is ready for playback.

    public class MediaBrowserSubscriptionCallback extends MediaBrowserCompat.SubscriptionCallback { //在mediaBrowserConnectionCallback里边用户成功subscribe

        @Override
        public void onChildrenLoaded(@NonNull String parentId,
                                     @NonNull List<MediaBrowserCompat.MediaItem> children) {
            Log.d(TAG, "onChildrenLoaded: CALLED: " + parentId + ", " + children.toString());

           // mMediaBrowserCallback.onGetChildPlayList(children);

        }
    }

    public MediaControllerCompat getController() {//可以得到控制播放的函数call
//        if (mMediaController == null) {
//            Log.d(TAG, "getTransportControls: MediaController is null!");
//            throw new IllegalStateException("MediaController is null!");
//        }


        return mMediaController;
    }

    public int getPlayState(){
        if (mMediaController == null) {
            return PlaybackStateCompat.STATE_STOPPED;
        }

        PlaybackStateCompat state=mMediaController.getPlaybackState();
        if(state==null){
            return PlaybackStateCompat.STATE_STOPPED;
        }else{
            return state.getState();
        }
    }
    public MediaMetadataCompat getCurrentPlaying(){
        if(mMediaController==null){
            return null;
        }
        return  mMediaController.getMetadata();
    }

    public void addPlayListQueueItem(Integer avid){
        AddPlayListQueue task=new AddPlayListQueue();
        task.execute(avid);
    }
    class AddPlayListQueue extends AsyncTask<Integer,Void,Void>{

        @Override
        protected Void doInBackground(Integer... integers) {
            MediaDescriptionCompat descriptionCompat=new MediaDescriptionCompat.Builder()
                    .setMediaId(String.valueOf(integers[0])).build();
            if(mMediaController!=null) {
                mMediaController.addQueueItem(descriptionCompat);
            }
            return null;
        }
    }

    //mutable live data avData get queue
}
