package net.chenxiy.bilimusic.database;


import net.chenxiy.bilimusic.network.biliapi.pojo.av.AvData;
import net.chenxiy.bilimusic.network.biliapi.pojo.av.Owner;
import net.chenxiy.bilimusic.network.biliapi.pojo.av.download.Audio;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.FolderArchive;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content.FavFolderContentData;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content.FavSong;
import net.chenxiy.bilimusic.network.biliapi.pojo.userinfo.UserData;

import java.util.ArrayList;
import java.util.List;


import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface BiliDao {

    @Query("select * from userData where mid= :userId")
    LiveData<UserData>getUserData(Integer userId);

    @Query("select * from userData")
    LiveData<List<UserData>>getAllUserData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserData(UserData userData);

    @Query("select * from folderArchive where mid = :userId")
    LiveData<List<FolderArchive>> getUserFavFolderInfo(Integer userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavFolder(List<FolderArchive> folderArchives);

    @Query("delete from folderArchive")
    void deleteAllFolderArchive();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavFolderContent(FavFolderContentData favFolderContentData);

    @Query("select * from favFolderContentData where fid = :fid")
    FavFolderContentData getFavFolderContent(Integer fid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavSongs(List<FavSong> favSongs);

    @Query("select * from favSong where fid=:fid")
    LiveData<List<FavSong>> getAllSongsFromFolder(Integer fid);


    @Query("select distinct * from favSong group by mid order by count(mid) DESC")
    DataSource.Factory<Integer,FavSong> getArtistList();

    @Query("select distinct * from favSong where mid = :userId group by aid")
    LiveData<List<FavSong>>getArtistSongs(Integer userId);

    @Query("select * from audioSource where aid= :avId and cid= :cid")
    List<Audio> getAudioSource(Integer avId,Integer cid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAudioSource(Audio audio);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAvData(AvData avData);

    @Query("select * from avData where aid= :avId")
    List<AvData> getAvData(Integer avId);






}
