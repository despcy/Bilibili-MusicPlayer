package net.chenxiy.bilimusic;

import android.content.Context;

import net.chenxiy.bilimusic.database.BiliDao;
import net.chenxiy.bilimusic.database.BiliDatabase;
import net.chenxiy.bilimusic.network.biliapi.ApiEndpointInterface;
import net.chenxiy.bilimusic.network.biliapi.RetrofitInstance;
import net.chenxiy.bilimusic.network.biliapi.pojo.av.Owner;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content.FavFolderContentData;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content.FavFolderContentInfoResponse;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content.FavSong;
import net.chenxiy.bilimusic.network.biliapi.pojo.userinfo.UserData;
import net.chenxiy.bilimusic.network.biliapi.pojo.userinfo.Vip;
import net.chenxiy.bilimusic.view.MainActivity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import androidx.room.Room;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@RunWith(AndroidJUnit4.class)
public class DBtest {
    private BiliDao biliDao;
    private BiliDatabase db;

    private Context context;

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, BiliDatabase.class).build();
        biliDao = db.biliDao();

    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

//    @Test
//    public void writeUserAndReadInList() throws Exception {
//
//        UserData data=new UserData();
//
//       // data.setMid(2333);
//        Vip vip=new Vip();
//        vip.setType(1234);
//        data.setVip(vip);
//        biliDao.insertUserData(data);
//        LiveData<List<UserData>> data1;
//        data1=biliDao.getAllUserData();
//
//        data1.observeForever(new Observer<List<UserData>>() {
//            @Override
//            public void onChanged(List<UserData> userData) {
//                System.out.println(userData.get(0).getVip().getType());
//
//                Assert.assertEquals("success", new Integer(1234),userData.get(0).getVip().getType());
//            }
//        });
//    }

//    @Test
//    public void favFolderDataTest() throws Exception{
//        ApiEndpointInterface apiService=Repository.getInstance(context).getApiService();
//        Call<FavFolderContentInfoResponse> call=apiService.getFavFolderContentInfo(Constants.COOKIE,Constants.USER_UID,Constants.PUB_FID,0
//        ,1,Constants.ORDER_FAV_TIMES);
//        call.enqueue(new Callback<FavFolderContentInfoResponse>() {
//            @Override
//            public void onResponse(Call<FavFolderContentInfoResponse> call, Response<FavFolderContentInfoResponse> response) {
//                FavFolderContentData data=response.body().getFavFolderContentData();
//                biliDao.insertFavFolderContent(data);
//                List<FavSong> songs=data.getFavSongs();
//                for (FavSong song:songs) {song.setFid(data.getFid());}
//                biliDao.insertFavSongs(songs);
//                List<FavSong> getSongs=biliDao.getAllSongsFromFolder(data.getFid());
//                Assert.assertArrayEquals(songs.toArray(),getSongs.toArray());
//
//
//            }
//
//            @Override
//            public void onFailure(Call<FavFolderContentInfoResponse> call, Throwable t) {
//
//            }
//        });
//        //Thread.sleep(10000);
//    }

    @Test
    public void artistCardViewDBTest() throws Exception{


        Thread.sleep(10000);
    }
}