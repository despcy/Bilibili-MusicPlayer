package net.chenxiy.bilimusic.database;

import android.content.Context;
import android.util.Log;

import net.chenxiy.bilimusic.database.converters.AvPageConverter;
import net.chenxiy.bilimusic.database.converters.DurlConverter;
import net.chenxiy.bilimusic.database.converters.FolderCoverConverter;
import net.chenxiy.bilimusic.network.biliapi.pojo.av.AvData;
import net.chenxiy.bilimusic.network.biliapi.pojo.av.download.Audio;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.FolderArchive;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content.FavFolderContentData;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content.FavSong;
import net.chenxiy.bilimusic.network.biliapi.pojo.userinfo.UserData;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {UserData.class,FolderArchive.class, FavFolderContentData.class,FavSong.class, AvData.class, Audio.class},version = 1,exportSchema = false)
@TypeConverters({FolderCoverConverter.class, AvPageConverter.class, DurlConverter.class})
public abstract class BiliDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "BiliDB";

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static BiliDatabase sInstance;

    public static BiliDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        BiliDatabase.class, BiliDatabase.DATABASE_NAME).allowMainThreadQueries().build();

            }
        }
        return sInstance;
    }

    // The associated DAOs for the database
    public abstract BiliDao biliDao();
}
