package net.chenxiy.bilimusic.datasource;

import net.chenxiy.bilimusic.Repository;
import net.chenxiy.bilimusic.network.biliapi.ApiEndpointInterface;
import net.chenxiy.bilimusic.network.biliapi.RetrofitInstance;
import net.chenxiy.bilimusic.network.biliapi.pojo.dynamic.DynamicResponse;
import net.chenxiy.bilimusic.network.biliapi.pojo.dynamic.HotSong;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HotSongDataSource extends PageKeyedDataSource<Integer, HotSong> {
    private ApiEndpointInterface apiService= RetrofitInstance.getInstance().create(ApiEndpointInterface.class);
    public static MutableLiveData<String> networkStatus=new MutableLiveData<>();
    public static final int PAGE_SIZE=30;
    public static final int FIRST_PAGE=1;
    public static final int DATE_INTERVAL=30;
    public Integer cateId=0;

    public HotSongDataSource(Integer cateId) {
        this.cateId = cateId;
    }



    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, HotSong> callback) {
       apiService.getTrendingPlaylist(cateId,FIRST_PAGE,PAGE_SIZE,getTimeParam(DATE_INTERVAL),getTimeParam(0)).enqueue(new Callback<DynamicResponse>() {
           @Override
           public void onResponse(Call<DynamicResponse> call, Response<DynamicResponse> response) {
               callback.onResult(response.body().getHotSong(),null,FIRST_PAGE+1);
               networkStatus.postValue("finish");
           }

           @Override
           public void onFailure(Call<DynamicResponse> call, Throwable t) {
               networkStatus.postValue("fail");
           }
       });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, HotSong> callback) {
        apiService.getTrendingPlaylist(cateId,params.key,PAGE_SIZE,getTimeParam(DATE_INTERVAL),getTimeParam(0)).enqueue(new Callback<DynamicResponse>() {
            @Override
            public void onResponse(Call<DynamicResponse> call, Response<DynamicResponse> response) {
                Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                if(response.body().getCode()==0){
                    callback.onResult(response.body().getHotSong(),adjacentKey);
                }
                networkStatus.postValue("finish");
            }

            @Override
            public void onFailure(Call<DynamicResponse> call, Throwable t) {
                networkStatus.postValue("fail");
            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, HotSong> callback) {
        apiService.getTrendingPlaylist(cateId,params.key,PAGE_SIZE,getTimeParam(DATE_INTERVAL),getTimeParam(0)).enqueue(new Callback<DynamicResponse>() {
            @Override
            public void onResponse(Call<DynamicResponse> call, Response<DynamicResponse> response) {
                if(response.body().getCode()==0){
                    if(response.body().getNumPages()==response.body().getPage()){
                        //end
                        callback.onResult(response.body().getHotSong(),null);
                    }
                    callback.onResult(response.body().getHotSong(),params.key+1);
                }
                networkStatus.postValue("finish");
            }

            @Override
            public void onFailure(Call<DynamicResponse> call, Throwable t) {
                networkStatus.postValue("fail");
            }
        });
    }

    private String getTimeParam(Integer offset){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, (1-offset));//1 for 时区
        return sdf.format(calendar.getTime());
    }
}
