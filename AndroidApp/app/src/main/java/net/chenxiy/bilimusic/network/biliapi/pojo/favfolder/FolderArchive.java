
package net.chenxiy.bilimusic.network.biliapi.pojo.favfolder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "folderArchive")
public class FolderArchive {

    @SerializedName("id")
    @Expose
    private Integer mediaId;
    @PrimaryKey
    @SerializedName("fid")
    @Expose
    private Integer fid;
    @SerializedName("mid")
    @Expose
    private Integer mid;
    @SerializedName("title")
    @Expose
    private String name;

    @SerializedName("media_count")
    @Expose
    private Integer curCount;

    @SerializedName("cover")
    @Expose
    private String cover;

    public Integer getMediaId() {
        return mediaId;
    }

    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getCurCount() {
        return curCount;
    }

    public void setCurCount(Integer curCount) {
        this.curCount = curCount;
    }
}
