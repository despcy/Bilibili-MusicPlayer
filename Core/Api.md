# Api for getting data from the server

### retrofit Reference: https://guides.codepath.com/android/consuming-apis-with-retrofit

### Json2Pojo:http://www.jsonschema2pojo.org/

## **所有api返回的code必须是0才是正确**

## Api 请求步骤：

### 1.登录获取cookie：

https://passport.bilibili.com/login

测试用cookie：（bilimusic那个号的）
```
sid=8b8x8hix; buvid3=2B21B26A-4F8D-4F88-9012-C23FEBB4D24984615infoc; DedeUserID=402053210; DedeUserID__ckMd5=f2a48e4e47b02818; SESSDATA=b29154fa%2C1553240075%2C9411a221; bili_jct=48bc047e993dafc8b1a9c938ee056153
```
后边有的用到CSRF:

就是cookie里边的这个字段
bili_jct=8f098bed761800407923c14b43406673

**其中DedeUserID就是userid**

### 2.[获取用户头像以及其他信息](./UserResponse.md)：

https://api.bilibili.com/x/space/acc/info?mid=402053210&jsonp=jsonp

#### Retrofit Interface:

```java
@GET("x/space/acc/info?jsonp=jsonp")
Call<UserResponse> getUser(@Query("mid") Integer userUid);
```

### 3.[获取用户收藏夹](./FavFolderInfo.md)：

https://api.bilibili.com/x/space/fav/nav?mid=402053210&jsonp=jsonp

#### Retrofit Interface:

```java
@GET("x/space/fav/nav?jsonp=jsonp")
Call<FavFolderInfoResponse> getFavFolderInfo(@Header("Cookie") String cookie,
@Query("mid")Integer userUid);
```

### 4.[根据收藏夹ID获取内容](./FavFolderContentInfo.md)：

https://api.bilibili.com/x/space/fav/arc?vmid=402053210&ps=30&fid=616695&tid=0&pn=1&order=fav_time&jsonp=jsonp

tid为分区：[相关定义见此 tid.json](./tid.json)

ps默认30，改了没用，去了也可以

pn是页数

order：最新收藏=fav_time   最多播放=click  最新投稿=pubdate

#### Retrofit Interface:

```java
@GET("x/space/fav/arc?jsonp=jsonp&ps=30")
Call<FavFolderContentInfoResponse> getFavFolderContentInfo(@Header("Cookie") String cookie,
                                                           @Query("vmid") Integer userUid,
                                                           @Query("fid") Integer folderId,
                                                           @Query("tid") Integer sortTid,
                                                           @Query("pn") Integer pageNum,
                                                           @Query("order") String order);
```


### 5.[根据av号获取信息](./AvInfo.md)：

https://api.bilibili.com/x/web-interface/view?aid=42463258

#### Retrofit Interface:

```java
@GET("x/web-interface/view")
Call<AvInfoResponse> getAvInfo(@Query("aid") Integer avId);
```

### 6.[根据av号跟分pid获取相关的下载链接](./DownloadInfo,md)：

https://api.bilibili.com/x/player/playurl?avid=42463258&cid=74506946&fnval=16&otype=json

**下载的时候记得添加 `referer=https://www.bilibili.com/`**

#### Retrofit Interface:

```java
@GET("x/player/playurl?fnval=16&otype=json")
Call<DownloadInfoResponse> getDownloadInfo(@Query("avid") Integer avId,@Query("cid") Integer cid);
```


### 7.[新建/删除收藏夹](./AddDelFavFolder.md)

添加：

https://api.bilibili.com/x/v2/fav/folder/add

```
POST

HEADER:
Content-Type: application/x-www-form-urlencoded
Cookie: ...

BODY:

name=test&public=0&jsonp=jsonp&csrf=48bc047e993dafc8b1a9c938ee056153

以上public=0->公开 public->1 私有
```

删除：

https://api.bilibili.com/x/v2/fav/folder/del

```
POST

HEADER:
Content-Type: application/x-www-form-urlencoded
Cookie: ...

BODY:

fid=3266531&jsonp=jsonp&csrf=48bc047e993dafc8b1a9c938ee056153
```

#### Retrofit Interface:
添加：
```java
@FormUrlEncoded
@POST("x/v2/fav/folder/add")
Call<AddDelFolderResponse> createFavFolder(@Header ("Cookie") String cookie,
                                           @Field("name") String folderName,
                                           @Field("public") Integer publicVal,
                                           @Field("csrf") String bili_jct,
                                           @Field("jsonp")String jsonp);

```
删除：
```java
@FormUrlEncoded
@POST("x/v2/fav/folder/del")
Call<AddDelFolderResponse> deleteFavFolder(@Header ("Cookie") String cookie,
                                           @Field("fid") Integer fid,
                                           @Field("csrf") String bili_jct,
                                           @Field("jsonp")String jsonp);
```


### 8.[添加/删除收藏夹内容](./AddDelSongInFavFolder.md)

添加：

https://api.bilibili.com/x/v2/fav/video/add

删除:

https://api.bilibili.com/x/v2/fav/video/del


```
POST:

Header:
Content-Type: application/x-www-form-urlencoded; charset=UTF-8
Cookie: ...

BODY:

aid=43783589&fid=3252985&jsonp=jsonp&csrf=48bc047e993dafc8b1a9c938ee056153
```

#### Retrofit Interface:

添加：
```java
@FormUrlEncoded
@POST("x/v2/fav/video/add")
Call<CodeResponse> addSongToFavFolder(@Header ("Cookie") String cookie,
                                           @Field("aid") Integer avId,
                                           @Field("fid") Integer fid,
                                           @Field("csrf") String bili_jct,
                                           @Field("jsonp")String jsonp);
```
删除：
```java
@FormUrlEncoded
@POST("x/v2/fav/video/del")
Call<CodeResponse> delSongFromFavFolder(@Header ("Cookie") String cookie,
                                        @Field("aid") Integer avId,
                                        @Field("fid") Integer fid,
                                        @Field("csrf") String bili_jct,
                                        @Field("jsonp")String jsonp);
```
### ~~9.获取关注列表~~

### ~~10.获取up主动态更新~~
```
@Deprecated
@Reason:too much irrelavant dynamic
https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/space_history?host_uid=354077280&offset_dynamic_id=0
```

**B站新功能，添加别人的收藏夹：**

https://api.bilibili.com/medialist/gateway/base/collected?pn=1&ps=20&up_mid=402053210&jsonp=jsonp


### 11.[获取音乐区播放数最多列表](./dynamicResponse.md)：


https://s.search.bilibili.com/cate/search?main_ver=v3&search_type=video&view_type=hot_rank&order=click&cate_id=28&page=1&pagesize=50&jsonp=jsonp&time_from=20190401&time_to=20190411

|分区|cate_id|
|-----|-------|
|原创音乐|28|
|翻唱|31|
|VOCALOID·UTAU|30|
|电音|194|
|演奏|59|
|MV|193|
|音乐现场|29|
|音乐综合|130|

#### Retrofit interface

```java
@GET("https://s.search.bilibili.com/cate/search?main_ver=v3&search_type=video&view_type=hot_rank&order=click&jsonp=jsonp")
Call<DynamicResponse> getTrendingPlaylist(@Query("cate_id")Integer cateId,
                                          @Query("page") Integer page,
                                          @Query("pagesize") Integer pageSize,
                                          @Query("time_from") String timeFrom,
                                          @Query("time_to") String timeTo);
```
---------------------------------
```json
CSRF Fail CODE:
{
    "code": -111,
    "message": "csrf 校验失败",
    "ttl": 1
}
```
