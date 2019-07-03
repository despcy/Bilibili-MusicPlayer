package net.chenxiy.bilimusic.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import net.chenxiy.bilimusic.Repository;
import net.chenxiy.bilimusic.datasource.HotSongDataSource;
import net.chenxiy.bilimusic.datasource.HotSongDataSourceFactory;
import net.chenxiy.bilimusic.network.biliapi.pojo.dynamic.HotSong;

import java.security.PrivateKey;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import retrofit2.Retrofit;

public class HotSongViewModel extends AndroidViewModel {
    //private DataSource<Integer,HotSong> mDataSource;
    public  SharedPreferences.OnSharedPreferenceChangeListener spChanged;
    LiveData<PagedList<HotSong>> songs;
    public MutableLiveData<String> netLoadStatus=HotSongDataSource.networkStatus;
    public HotSongViewModel(@NonNull Application application) {
        super(application);

    }



    public LiveData<PagedList<HotSong>> getHotSongs(Integer category){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplication());
        Integer PERIOD=Integer.valueOf(sharedPref.getString("dynamicPeriod","7"));
        HotSongDataSourceFactory factory=new HotSongDataSourceFactory(category,PERIOD);
        spChanged=new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if(key.equals("dynamicPeriod")){
                    Integer p=Integer.valueOf(sharedPref.getString("dynamicPeriod","7"));
                    factory.refresh(category,p);
                }
            }
        };
        sharedPref.registerOnSharedPreferenceChangeListener(spChanged);


        PagedList.Config config=new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(HotSongDataSource.PAGE_SIZE).build();
       // mDataSource=factory.create();
        songs= new LivePagedListBuilder(factory,config).build();
        return songs;
    }

    public LiveData<PagedList<HotSong>> getPagedListLiveData(Integer Cate_ID){
        return getHotSongs(Cate_ID);
    }

    public void invalidateData(){

        //mDataSource.invalidate();
        songs.getValue().getDataSource().invalidate();
    }





}
