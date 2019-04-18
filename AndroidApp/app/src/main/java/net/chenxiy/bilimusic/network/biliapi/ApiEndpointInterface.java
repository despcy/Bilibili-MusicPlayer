package net.chenxiy.bilimusic.network.biliapi;

import net.chenxiy.bilimusic.network.biliapi.pojo.av.AvInfoResponse;
import net.chenxiy.bilimusic.network.biliapi.pojo.av.download.DownloadInfoResponse;
import net.chenxiy.bilimusic.network.biliapi.pojo.dynamic.DynamicResponse;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.FavFolderInfoResponse;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.add_del_folder.AddDelFolderResponse;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.add_del_music.CodeResponse;
import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content.FavFolderContentInfoResponse;
import net.chenxiy.bilimusic.network.biliapi.pojo.userinfo.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiEndpointInterface {

    //get basic user Info
    @GET("x/space/acc/info?jsonp=jsonp")
    Call<UserResponse> getUser(@Query("mid") Integer userUid);

    //create a new fav folder
    @FormUrlEncoded
    @POST("x/v2/fav/folder/add")
    Call<AddDelFolderResponse> createFavFolder(@Header("Cookie") String cookie,
                                               @Field("name") String folderName,
                                               @Field("public") Integer publicVal,
                                               @Field("csrf") String bili_jct,
                                               @Field("jsonp") String jsonp);

    //delete a new fav folder
    @FormUrlEncoded
    @POST("x/v2/fav/folder/del")
    Call<AddDelFolderResponse> deleteFavFolder(@Header("Cookie") String cookie,
                                               @Field("fid") Integer fid,
                                               @Field("csrf") String bili_jct,
                                               @Field("jsonp") String jsonp);

    //get all fav folder list for a user
    @GET("x/space/fav/nav?jsonp=jsonp")
    Call<FavFolderInfoResponse> getFavFolderInfo(@Header("Cookie") String cookie,
                                                 @Query("mid") Integer userUid);

    //get the content of a fav folder
    @GET("x/space/fav/arc?jsonp=jsonp&ps=30")
    Call<FavFolderContentInfoResponse> getFavFolderContentInfo(@Header("Cookie") String cookie,
                                                               @Query("vmid") Integer userUid,
                                                               @Query("fid") Integer folderId,
                                                               @Query("tid") Integer sortTid,
                                                               @Query("pn") Integer pageNum,
                                                               @Query("order") String order);

    //get av info give an avid
    @GET("x/web-interface/view")
    Call<AvInfoResponse> getAvInfo(@Query("aid") Integer avId);



    //get download link info given a avid and cid
    @GET("x/player/playurl?fnval=16&otype=json")
    Call<DownloadInfoResponse> getDownloadInfo(@Query("avid") Integer avId, @Query("cid") Integer cid);


    //add a new video to fav folder
    @FormUrlEncoded
    @POST("x/v2/fav/video/add")
    Call<CodeResponse> addSongToFavFolder(@Header("Cookie") String cookie,
                                          @Field("aid") Integer avId,
                                          @Field("fid") Integer fid,
                                          @Field("csrf") String bili_jct,
                                          @Field("jsonp") String jsonp);

    //delete a new video to fav folder
    @FormUrlEncoded
    @POST("x/v2/fav/video/del")
    Call<CodeResponse> delSongFromFavFolder(@Header("Cookie") String cookie,
                                            @Field("aid") Integer avId,
                                            @Field("fid") Integer fid,
                                            @Field("csrf") String bili_jct,
                                            @Field("jsonp") String jsonp);



    //get the hot music list
    @GET("https://s.search.bilibili.com/cate/search?main_ver=v3&search_type=video&view_type=hot_rank&order=click&jsonp=jsonp")
    Call<DynamicResponse> getTrendingPlaylist(@Query("cate_id") Integer cateId,
                                              @Query("page") Integer page,
                                              @Query("pagesize") Integer pageSize,
                                              @Query("time_from") String timeFrom,
                                              @Query("time_to") String timeTo);



}
