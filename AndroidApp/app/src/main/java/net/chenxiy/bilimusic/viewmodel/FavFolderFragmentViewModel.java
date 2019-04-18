package net.chenxiy.bilimusic.viewmodel;

import android.app.Application;

import net.chenxiy.bilimusic.DataUtils;
import net.chenxiy.bilimusic.Repository;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.FolderArchive;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content.FavSong;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class FavFolderFragmentViewModel extends AndroidViewModel {
    public static MutableLiveData<Boolean> shouldRefresh;
    public static ArrayList<FolderArchive> favFolderArchives;
    Repository repository;
    Integer myUserId= DataUtils.getUserIDFromCookie();
    public static Integer PageSize=10;

    public FavFolderFragmentViewModel(@NonNull Application application) {
        super(application);
        repository=Repository.getInstance(application.getApplicationContext());
    }


    public LiveData<List<FolderArchive>>getFavFolderInfo(){
        return repository.getFavFolderInfoLiveData(DataUtils.getCookie(),myUserId);
    }

    public LiveData<PagedList<FavSong>> getArtistList(){
        PagedList.Config config=new PagedList.Config.Builder().setPageSize(10).setEnablePlaceholders(false).build();

        return new LivePagedListBuilder<>(repository.getFavFolderPageArtistInfo(),config).build();

    }


    public MutableLiveData<String> refreshFavFolderInfoData(){
        return  repository.fetchFavFolderData(DataUtils.getCookie(),myUserId);
    }


}
