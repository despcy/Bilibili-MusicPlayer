package net.chenxiy.bilimusic.datasource;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import net.chenxiy.bilimusic.Constants;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class HotSongDataSourceFactory extends DataSource.Factory {
    Integer cateId;
    Integer period;
    public HotSongDataSourceFactory(Integer CateId,Integer Period) {
        cateId=CateId;
        period=Period;

    }

    public void refresh(Integer CateId,Integer Period){
        cateId=CateId;
        period=Period;
        this.create();
    }


    @Override
    public DataSource create() {
        HotSongDataSource source= new HotSongDataSource(cateId,period);


        return source;
    }
}
