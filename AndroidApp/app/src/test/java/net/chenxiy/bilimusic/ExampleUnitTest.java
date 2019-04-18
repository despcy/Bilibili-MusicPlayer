package net.chenxiy.bilimusic;

import net.chenxiy.bilimusic.network.biliapi.ApiEndpointInterface;
import net.chenxiy.bilimusic.network.biliapi.RetrofitInstance;
import net.chenxiy.bilimusic.network.biliapi.pojo.dynamic.DynamicResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(JUnit4.class)
public class ExampleUnitTest {

    @Test
    public void test(){
        ApiEndpointInterface api= RetrofitInstance.getInstance().create(ApiEndpointInterface.class);
        try {
            //DownloadInfoResponse response= api.getDownloadInfo(45830128,80306490).execute().body();
            //response.getCode();
            DynamicResponse response=api.getTrendingPlaylist(Constants.VOCAL,1,30,"20190401","20190430")
                    .execute().body();
            System.out.println(response.getHotSong().size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -3000);
        Date yesterday = calendar.getTime();

        System.out.println(sdf.format(yesterday));
    }




}