## 用户基本信息：

#### net.chenxiy.bilimusic.network.biliapi.pojo.userinfo.UserData

|Annotation|Content|
|----------|-------|
|tableName|userData|
|PrimaryKey|mid|
|Embeded|theme|
|Embeded|vip|
|Embeded|official|

## 歌曲具体信息：

#### net.chenxiy.bilimusic.network.biliapi.pojo.av.AvData

|Annotation|Content|
|----------|-------|
|tableName |avData|
|PrimaryKey|aid|
|Embeded|rights|
|Embeded|owner|
|Embeded|stat|
|Embeded|dimension|
|Ignore|subtitle|
|typeconverter|pages|


## 音频下载信息：

#### net.chenxiy.bilimusic.network.biliapi.pojo.av.download.Audio
|Annotation|Content|
|----------|-------|
|tableName |audioSource|
|PrimaryKey(autoGenerate = true)|id|
|Integer |aid(not from api)|
|Integer |cid(not from api)|
|ForeignKey|@ForeignKey(entity = AvData.class,parentColumns = "aid",childColumns = "aid",onDelete = ForeignKey.CASCADE)|
|SerializedName("id")|Integer qualityId|



## 用户收藏夹信息

#### net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.FolderArchive

|Annotation|Content|
|----------|-------|
|tableName|folderArchive|
|ForeignKey|@ForeignKey(entity = FolderArchive.class,parentColumns = "fid",childColumns = "fid",onDelete = ForeignKey.CASCADE)|
|PrimaryKey|fid|
|typecoverter|Cover|

## 收藏夹内容：

#### net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content.FavFolderContentData

|Annotation|Content|
|----------|-------|
|tableName|favFolderContentData|
|indices |{@Index(value = {"fid"}, unique = true)}|
|PrimaryKey|fid|
|@Ignore|List<FavSongs\>|

#### net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content.FavSong

|Annotation|Content|
|----------|-------|
|tableName|FavSong|
|foreignKeys|@ForeignKey(entity = FavFolderContentData.class,parentColumns = "fid",childColumns = "fid",onDelete = ForeignKey.CASCADE)|
|indices |{@Index(value = {"fid"})}|
|PrimaryKey(autoGenerate=true)|id|
|createMyself|fid|
|Embeded|rights|
|Embeded|owner|
|Embeded|stat|
|Embeded|dimension|
