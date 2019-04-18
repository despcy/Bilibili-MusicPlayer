package net.chenxiy.bilimusic.mediaclient;

import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import java.util.List;

public interface MediaBrowserHelperCallback {


    void onPlaybackStateChanged(PlaybackStateCompat state);

    void onMediaControllerConnected(MediaControllerCompat mediaController);

    //netvoid onGetChildPlayList(List<MediaBrowserCompat.MediaItem> children);

}









