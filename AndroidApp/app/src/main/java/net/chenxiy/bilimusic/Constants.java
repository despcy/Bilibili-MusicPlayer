package net.chenxiy.bilimusic;

import java.util.ArrayList;
import java.util.Arrays;

public class Constants {
    //NETWORK RELATED:
    public static final String BASE_URL="https://api.bilibili.com/";
    public static final String JSONP="JSONP";
    public static final String ORDER_FAV_TIMES="fav_time";
    public static final String ORDER_WATCH="click";
    public static final String ORDER_PUBLISH="pubdate";
    public static final Integer PUBLIC_FAV_FOLDER=0;
    public static final Integer PRIVATE_FAV_FOLDER=1;

    //PAGER VIEW
    public static final Integer NUM_PAGES=3;
    public static final Integer SONG_LIST_INDEX=0;
    public static final Integer WEB_VIEW_INDEX=1;
    public static final Integer DYNAMIC_VIEW_INDEX=2;

    //INTENT_EXTRA
    public static final Integer TYPE_FAV_FOLDER=0;
    public static final Integer TYPE_ARTIST_FOLDER=1;

    //-------------LIVE DATA SIGNAL-------------
    public static final String SUCCESS="success";
    public static final String ERROR="error";

    //-------------Notification Channel----------
        public static final String PLAYER_CHANNEL_ID="BiliMusic";
        public static final Integer PLAYER_NOTIFICATION_ID  =1;
    //-------------Bili Audio Quality ID-------
    public static final Integer AUDIO_QUALITY_HIGH=30280;
    public static final Integer AUDIO_QUALITY_LOW=30216;
    //--------------Bili Catagory ID----------
    public static final Integer ORIG=28;
    public static final Integer COVER=31;
    public static final Integer VOCAL=30;
    public static final Integer ELEC=194;
    public static final Integer PLAY=59;
    public static final Integer MV=193;
    public static final Integer LIVE=29;
    public static final Integer MISC=130;
    //-----------Client Service Communication---
    public static final String QUEUE_NEW_PLAYLIST="newPlayList";
    public static final String MEDIA_QUEUE_POSITION="newPosition";
    public static final String ACTION_UPDATE_UI="net.chenxiy.bilimusic.broadcast_ui_update";
    public static final String ACTION_UPDATE_SEEKBAR="net.chenxiy.bilimusic.broadcast_seekbar_update";
    public static final String ACTION_UPDATE_PLAYLIST="net.chenxiy.bilimusic.broadcast_playlist_update";
    public static final String ACTION_UPDATE_REPEAT_MODE="net.chenxiy.bilimusic.broadcast_repeatMode_update";
    public static final String SEEK_BAR_PROGRESS="SEEK_BAR_PROGRESS";
    public static final String SEEK_BAR_MAX="SEEK_BAR_MAX";
    public static final String SEEK_BAR_BUFFER_POSITION="SEEK_BAR_BUFFER_POSITION";
    public static final String BROADCAST_MEDIA_ID="broadcast_new_media_id";
    public static final String BROADCAST_NOW_PLAY_INDEX="broadcast_now_play_index";
    public static final String BROADCAST_DELETE_INDEX="broadcast_DELETE_index";
    public static final String BROADCAST_REPEAT_MODE="broadcast_repeatMode_update";
    //----------------SharedPreferenceKey--------
    public static final String KEY_SONG_QUALITY="songQuality";
    public static final String KEY_PLAY_LOOP="songLoop";


}
