
package net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.content;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.FolderArchive;

import java.util.List;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

@Entity(tableName = "favFolderContentData",indices = {@Index(value = {"fid"}, unique = true)})//,foreignKeys = @ForeignKey(entity = FolderArchive.class,parentColumns = "fid",childColumns = "fid",onDelete = ForeignKey.CASCADE))
public class FavFolderContentData {

    @SerializedName("seid")
    @Expose
    private String seid;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("pagesize")
    @Expose
    private Integer pagesize;
    @SerializedName("pagecount")
    @Expose
    private Integer pagecount;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("suggest_keyword")
    @Expose
    private String suggestKeyword;
    @SerializedName("mid")
    @Expose
    private Integer mid;
    @SerializedName("fid")
    @Expose
    @PrimaryKey
    private Integer fid;
    @SerializedName("tid")
    @Expose
    private Integer tid;
    @SerializedName("order")
    @Expose
    private String order;
    @SerializedName("keyword")
    @Expose
    private String keyword;
    @SerializedName("archives")
    @Expose
    @Ignore
    private List<FavSong> favSongs = null;

    public String getSeid() {
        return seid;
    }

    public void setSeid(String seid) {
        this.seid = seid;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Integer getPagecount() {
        return pagecount;
    }

    public void setPagecount(Integer pagecount) {
        this.pagecount = pagecount;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getSuggestKeyword() {
        return suggestKeyword;
    }

    public void setSuggestKeyword(String suggestKeyword) {
        this.suggestKeyword = suggestKeyword;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<FavSong> getFavSongs() {
        return favSongs;
    }

    public void setFavSongs(List<FavSong> favSongs) {
        this.favSongs = favSongs;
    }

}
