
package net.chenxiy.biliapi.pojo.favfolder;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FolderArchive {

    @SerializedName("media_id")
    @Expose
    private Integer mediaId;
    @SerializedName("fid")
    @Expose
    private Integer fid;
    @SerializedName("mid")
    @Expose
    private Integer mid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("max_count")
    @Expose
    private Integer maxCount;
    @SerializedName("cur_count")
    @Expose
    private Integer curCount;
    @SerializedName("atten_count")
    @Expose
    private Integer attenCount;
    @SerializedName("favoured")
    @Expose
    private Integer favoured;
    @SerializedName("state")
    @Expose
    private Integer state;
    @SerializedName("ctime")
    @Expose
    private Integer ctime;
    @SerializedName("mtime")
    @Expose
    private Integer mtime;
    @SerializedName("cover")
    @Expose
    private List<Cover> cover = null;

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

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Integer getCurCount() {
        return curCount;
    }

    public void setCurCount(Integer curCount) {
        this.curCount = curCount;
    }

    public Integer getAttenCount() {
        return attenCount;
    }

    public void setAttenCount(Integer attenCount) {
        this.attenCount = attenCount;
    }

    public Integer getFavoured() {
        return favoured;
    }

    public void setFavoured(Integer favoured) {
        this.favoured = favoured;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getCtime() {
        return ctime;
    }

    public void setCtime(Integer ctime) {
        this.ctime = ctime;
    }

    public Integer getMtime() {
        return mtime;
    }

    public void setMtime(Integer mtime) {
        this.mtime = mtime;
    }

    public List<Cover> getCover() {
        return cover;
    }

    public void setCover(List<Cover> cover) {
        this.cover = cover;
    }

}
