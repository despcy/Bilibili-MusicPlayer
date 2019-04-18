package net.chenxiy.bilimusic.datasource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class HotSongDataSourceFactory extends DataSource.Factory {
    Integer cateId;
    public HotSongDataSourceFactory(Integer CateId) {
        cateId=CateId;
    }


    @Override
    public DataSource create() {
        HotSongDataSource source= new HotSongDataSource(cateId);

        return source;
    }
}
