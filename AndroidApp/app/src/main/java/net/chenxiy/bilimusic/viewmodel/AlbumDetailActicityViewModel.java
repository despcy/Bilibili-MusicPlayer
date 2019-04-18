package net.chenxiy.bilimusic.viewmodel;

import android.app.Application;

import net.chenxiy.bilimusic.Constants;
import net.chenxiy.bilimusic.Repository;
import net.chenxiy.bilimusic.mediaclient.MediaBrowserHelper;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content.FavSong;
import net.chenxiy.bilimusic.service.MediaService;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class AlbumDetailActicityViewModel extends AndroidViewModel {

    Repository repository;


    private MediaBrowserHelper mMediaBrowserHelper;
    public AlbumDetailActicityViewModel(@NonNull Application application) {
        super(application);
        repository=Repository.getInstance(application.getApplicationContext());
        mMediaBrowserHelper=new MediaBrowserHelper(getApplication(), MediaService.class);
        mMediaBrowserHelper.onStart();

    }

    public LiveData<List<FavSong>>getFavSongList(Integer fid){
        return repository.getFavSongs(fid);
    }

    public LiveData<List<FavSong>>getArtistSongs(Integer mid){
        return repository.getArtistSongs(mid);
    }

    public MediaBrowserHelper getmMediaBrowserHelper() {
        return mMediaBrowserHelper;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mMediaBrowserHelper.onStop();
    }
}
