package net.chenxiy.bilimusic;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import net.chenxiy.bilimusic.database.BiliDao;
import net.chenxiy.bilimusic.database.BiliDatabase;
import net.chenxiy.bilimusic.network.biliapi.ApiEndpointInterface;
import net.chenxiy.bilimusic.network.biliapi.RetrofitInstance;
import net.chenxiy.bilimusic.network.biliapi.pojo.av.AvData;
import net.chenxiy.bilimusic.network.biliapi.pojo.av.AvInfoResponse;
import net.chenxiy.bilimusic.network.biliapi.pojo.av.download.Audio;
import net.chenxiy.bilimusic.network.biliapi.pojo.av.download.DownloadInfoResponse;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.FavFolderInfoResponse;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.FolderArchive;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.add_del_folder.AddDelFolderResponse;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.add_del_music.CodeResponse;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content.FavFolderContentData;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content.FavFolderContentInfoResponse;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content.FavSong;
import net.chenxiy.bilimusic.network.biliapi.pojo.userinfo.UserData;
import net.chenxiy.bilimusic.network.biliapi.pojo.userinfo.UserResponse;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    public final Integer defaultAVID=17405102;//彩蛋

    private static final String TAG = "RepositoryLog";
    private static final Object LOCK = new Object();
    private static Repository repository=null;
    private Context mContext;
    public  ApiEndpointInterface getApiService() {
        return apiService;
    }

    private ApiEndpointInterface apiService;
    private BiliDao biliDao;
    private static Executor DiskIO= Executors.newFixedThreadPool(4);
    public static MutableLiveData<String> loadResourceMessager;//a bus to communicate with mainThread for network resource loading

    public static Repository getInstance(Context context){
        if(repository==null){
            synchronized (LOCK) {
                repository = new Repository(context);

            }
        }
        return repository;
    }

    public Repository(Context context) {
        mContext=context;
        apiService= RetrofitInstance.getInstance().create(ApiEndpointInterface.class);
        biliDao=BiliDatabase.getInstance(context).biliDao();
        loadResourceMessager=new MutableLiveData<>();
    }


    //--------------------------------------------------------------------




//-----------------------------------------------------------------------


    public MutableLiveData<String> fetchFavFolderData(String cookie,Integer userId){

        final MutableLiveData<String> flag=new MutableLiveData<>();
        apiService.getFavFolderInfo(cookie,userId)
                .enqueue(new Callback<FavFolderInfoResponse>() {
                    @Override
                    public void onResponse(Call<FavFolderInfoResponse> call, Response<FavFolderInfoResponse> response) {
                        FavFolderInfoResponse favFolderInfoResponse=response.body();
                        if(favFolderInfoResponse.getCode()==0){
                            DiskIO.execute(new Runnable() {
                                @Override
                                public void run() {
                                    List<FolderArchive> archives=favFolderInfoResponse.getData().getFolderArchive();
                                    biliDao.deleteAllFolderArchive();
                                    biliDao.insertFavFolder(archives);
                                    for (FolderArchive archive:archives){
                                        fetchFavFolderContentData(cookie,userId,archive.getFid(),0,1,Constants.ORDER_FAV_TIMES);
                                    }

                                    loadResourceMessager.postValue(Constants.SUCCESS);
                                    flag.postValue(Constants.SUCCESS);
                                }
                            });



                        }else{
                            Log.d(TAG, "onResponse: error message code");
                            //error handling
                            loadResourceMessager.postValue(Constants.ERROR);
                            flag.postValue(favFolderInfoResponse.getMessage());

                        }
                    }

                    @Override
                    public void onFailure(Call<FavFolderInfoResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure: fail to load fav folder");
                        loadResourceMessager.postValue(Constants.ERROR);
                        flag.postValue(Constants.ERROR);
                    }
                });
        return flag;

    }

    public LiveData<List<FolderArchive>> getFavFolderInfoLiveData(String cookie,Integer userId){

        return biliDao.getUserFavFolderInfo(userId);
    }
//--------------------------
    public void fetchUserInfoData(Integer userId){

        apiService.getUser(userId).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse=response.body();
                if(userResponse.getCode()==0){
                    DiskIO.execute(new Runnable() {
                        @Override
                        public void run() {
                            biliDao.insertUserData(response.body().getUserData());
                        }
                    });


                }else{

                    Log.d(TAG, "onResponse: fail");
                    //error handling
                    loadResourceMessager.postValue(Constants.ERROR);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

                Log.d(TAG, "onFailure: ");
                loadResourceMessager.postValue(Constants.ERROR);
            }
        });
    }


    public LiveData<UserData> getUserData(Integer userId){

        return biliDao.getUserData(userId);
    }
//---------------------------------
    //TODO: fetch the artist UserData from the database and return the livedata




//------------------------
    private void fetchFavFolderContentData(String cookie,Integer userUid,Integer folderId,Integer sortTid,Integer pageNum,String order){
        apiService.getFavFolderContentInfo(cookie,userUid,folderId,sortTid,pageNum,order)
        .enqueue(new Callback<FavFolderContentInfoResponse>() {
            @Override
            public void onResponse(Call<FavFolderContentInfoResponse> call, Response<FavFolderContentInfoResponse> response) {
                if(response.body().getCode()==0) {

                    DiskIO.execute(new Runnable() {
                        @Override
                        public void run() {
                            FavFolderContentData data=response.body().getFavFolderContentData();
                            biliDao.insertFavFolderContent(data);
                            for(int i=1;i<=data.getPagecount();i++){
                                fetchFavSongData(cookie,userUid,folderId,sortTid,i,order);
                            }
                        }
                    });

                }else{
                    loadResourceMessager.postValue(Constants.ERROR);
                }
            }

            @Override
            public void onFailure(Call<FavFolderContentInfoResponse> call, Throwable t) {

                loadResourceMessager.postValue(Constants.ERROR);
            }
        });


    }


    //---------
    private void fetchFavSongData(String cookie,Integer userUid,Integer folderId,Integer sortTid,Integer pageNum,String order){
        apiService.getFavFolderContentInfo(cookie,userUid,folderId,sortTid,pageNum,order)
                .enqueue(new Callback<FavFolderContentInfoResponse>() {
                    @Override
                    public void onResponse(Call<FavFolderContentInfoResponse> call, Response<FavFolderContentInfoResponse> response) {
                        if(response.body().getCode()==0) {

                            DiskIO.execute(new Runnable() {
                                @Override
                                public void run() {
                                    List<FavSong> favSongs=response.body().getFavFolderContentData().getFavSongs();
                                    for(FavSong song:favSongs){song.setFid(folderId);};
                                    biliDao.insertFavSongs(favSongs);
                                }
                            });


                        }else{
                            loadResourceMessager.postValue(Constants.ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<FavFolderContentInfoResponse> call, Throwable t) {

                        loadResourceMessager.postValue(Constants.ERROR);
                    }
                });


    }
    //-----------
    public LiveData<List<FavSong>>getFavSongs(Integer fid){

        return biliDao.getAllSongsFromFolder(fid);
    }

    //-------------
    public DataSource.Factory<Integer,FavSong> getFavFolderPageArtistInfo(){
        return biliDao.getArtistList();
    }
    public LiveData<List<FavSong>>getArtistSongs(Integer mid){return biliDao.getArtistSongs(mid);}

    //-------------------getAvData----------
    public void getAvDataFromId(Integer index,Integer avId,onReceiveAvData callback){

        DiskIO.execute(new Runnable() {
            @Override
            public void run() {
                List<AvData> avDataList=biliDao.getAvData(avId);
                if(avDataList.size()!=0) {
                    AvData avData = avDataList.get(0);
                    if (avData != null) {
                        Log.d(TAG, "onResponse: " + avData.getAid());
                        callback.receiveData(index,avData);
                        return;
                    }
                }

                 Log.d(TAG, "onResponseNetwork: "+avId);
                       apiService.getAvInfo(avId).enqueue(new Callback<AvInfoResponse>() {
                            @Override
                            public void onResponse(Call<AvInfoResponse> call, Response<AvInfoResponse> response) {
                                if(response.body().getCode()==0){

                                    AvData avData=response.body().getAvData();
                                    biliDao.insertAvData(avData);
                                    Log.d(TAG, "onResponse: "+avData.getAid());
                                    callback.receiveData(index,avData);

                                }else{
                                    Log.d(TAG, "onResponseError: "+response.message());
                                    //error handle
                                }
                            }

                            @Override
                       public void onFailure(Call<AvInfoResponse> call, Throwable t) {

                       }
                 });
               }


        });

    }
    public interface onReceiveAvData{
        void receiveData(Integer index,AvData data);
    }

    public AvData getAvDataFromIdMainThread(Integer avId) {
        List<AvData> avDataList = biliDao.getAvData(avId);
        AvData avData;
        if (avDataList.size() != 0) {
            avData = avDataList.get(0);
            if (avData != null) {

                return avData;
            }

        }
            try {
                Response<AvInfoResponse> response = apiService.getAvInfo(avId).execute();
              if(response.body()==null){

                  Toast.makeText(mContext,"由于触发哔哩哔哩安全风控策略，该次访问请求被拒绝。\n" +
                          "The request was rejected because of the bilibili security control policy.   ",Toast.LENGTH_LONG);

              }

                avData = response.body().getAvData();
              if(avData==null)return getAvDataFromIdMainThread(defaultAVID);
                biliDao.insertAvData(avData);
                return avData;
            } catch (IOException e) {
                e.printStackTrace();
            }

        return null;
    }
    //------------------Fav Folder Operation---------

    public void addNewFavFolder(String folderName,Integer type,Callback<AddDelFolderResponse> callback){
        String cookie= DataUtils.getCookie();
        String CSRF= DataUtils.getCSRFTokenFromCookie();

       apiService.createFavFolder(cookie,folderName,type,CSRF,"jsonp")
                .enqueue(callback);
    }

    public void deleteNewFavFolder(Integer fid,Callback<AddDelFolderResponse> callback){
        String cookie= DataUtils.getCookie();
        String CSRF= DataUtils.getCSRFTokenFromCookie();

       apiService.deleteFavFolder(cookie,fid,CSRF,"jsonp").enqueue(callback);
    }

    public void deleteSongFromFolder(Integer avid,Integer fid,Callback<CodeResponse> callback){
        String cookie= DataUtils.getCookie();
        String CSRF= DataUtils.getCSRFTokenFromCookie();
        apiService.delSongFromFavFolder(cookie,avid,fid,CSRF,"jsonp").enqueue(callback);
    }

    public void addSongToFolder(Integer avid,Integer fid,Callback<CodeResponse> callback){
        String cookie= DataUtils.getCookie();
        String CSRF= DataUtils.getCSRFTokenFromCookie();
        apiService.addSongToFavFolder(cookie,avid,fid,CSRF,"jsonp").enqueue(callback);
    }

    //------------------audio download source url------
    public String getDownloadUrl(Integer avId,Integer cid,Integer quality){
        List<Audio> audioSource=biliDao.getAudioSource(avId,cid);
        try {

            Audio audio=null;
            if(audioSource.size()!=0){
                audio=audioSource.get(0);
            }
            if(audio==null||audio.getQualityId()!=quality||checkIfUrlExpire(audio.getBaseUrl())){//not in database


               DownloadInfoResponse response= apiService.getDownloadInfo(avId,cid).execute().body();
                if (response.getCode()==0){//request success
                    if(response.getData().getDownloadResources()==null){
                        //https://api.bilibili.com/x/player/playurl?avid=26305734&cid=45176667&fnval=16&otype=json&qn=16 这种居然没有audio接口！
                        String url=response.getData().getDurl().get(0).getUrl();
                        audio=new Audio();
                        audio.setBaseUrl(url);

                    }else {
                        List<Audio> resources = response.getData().getDownloadResources().getAudio();
                        audio = resources.get(0);//set default
                        for (Audio items : resources) {
                            if (items.getQualityId().equals(quality)) {
                                audio = items;
                                break;
                            }
                        }
                    }
                    audio.setAid(avId);
                    audio.setCid(cid);
                    biliDao.insertAudioSource(audio);
                }else{

                }

            }



            return audio.getBaseUrl();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
//http://upos-hz-mirrorwcsu.acgvideo.com/upgcxcode/46/69/74506946/74506946-1-30280.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEVEuxTEto8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&deadline=1550709028&gen=playurl&nbs=1&oi=2850703064&os=wcsu&platform=pc&trid=c621c311c72743849d98f7a347af60fe&uipk=5&upsig=6d215be6848cb8e518c127c10dacf5f0
    private boolean checkIfUrlExpire(String url){
        String s=url;
        s = s.substring(s.indexOf("deadline=") + 9);
        s = s.substring(0, s.indexOf("&"));
        Long timeStamp=Long.valueOf(s)-300;//set some time ahead
        Long CurTime=System.currentTimeMillis()/1000;
        if(timeStamp>CurTime){
            return true;
        }
        return false;
    }

}
