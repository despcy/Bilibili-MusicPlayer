package net.chenxiy.bilimusic.player;

import android.support.v4.media.session.PlaybackStateCompat;

public interface PlayBackInfoListener {

    void onPlaybackStateChange(PlaybackStateCompat state);//

    void onSeekTo(long progress, long bufferPosition,long max);

    void onPlaybackComplete();

    void updateUI(String newMediaId);
}
