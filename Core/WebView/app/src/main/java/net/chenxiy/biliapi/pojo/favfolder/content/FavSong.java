
package net.chenxiy.biliapi.pojo.favfolder.content;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavSong {

    @SerializedName("aid")
    @Expose
    private Integer aid;
    @SerializedName("videos")
    @Expose
    private Integer videos;
    @SerializedName("tid")
    @Expose
    private Integer tid;
    @SerializedName("tname")
    @Expose
    private String tname;
    @SerializedName("copyright")
    @Expose
    private Integer copyright;
    @SerializedName("pic")
    @Expose
    private String pic;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("pubdate")
    @Expose
    private Integer pubdate;
    @SerializedName("ctime")
    @Expose
    private Integer ctime;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("state")
    @Expose
    private Integer state;
    @SerializedName("attribute")
    @Expose
    private Integer attribute;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("rights")
    @Expose
    private Rights rights;
    @SerializedName("owner")
    @Expose
    private Owner owner;
    @SerializedName("stat")
    @Expose
    private Stat stat;
    @SerializedName("dynamic")
    @Expose
    private String dynamic;
    @SerializedName("cid")
    @Expose
    private Integer cid;
    @SerializedName("dimension")
    @Expose
    private Dimension dimension;
    @SerializedName("fav_at")
    @Expose
    private Integer favAt;
    @SerializedName("play_num")
    @Expose
    private String playNum;
    @SerializedName("highlight_title")
    @Expose
    private String highlightTitle;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getVideos() {
        return videos;
    }

    public void setVideos(Integer videos) {
        this.videos = videos;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public Integer getCopyright() {
        return copyright;
    }

    public void setCopyright(Integer copyright) {
        this.copyright = copyright;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPubdate() {
        return pubdate;
    }

    public void setPubdate(Integer pubdate) {
        this.pubdate = pubdate;
    }

    public Integer getCtime() {
        return ctime;
    }

    public void setCtime(Integer ctime) {
        this.ctime = ctime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Rights getRights() {
        return rights;
    }

    public void setRights(Rights rights) {
        this.rights = rights;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }

    public String getDynamic() {
        return dynamic;
    }

    public void setDynamic(String dynamic) {
        this.dynamic = dynamic;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public Integer getFavAt() {
        return favAt;
    }

    public void setFavAt(Integer favAt) {
        this.favAt = favAt;
    }

    public String getPlayNum() {
        return playNum;
    }

    public void setPlayNum(String playNum) {
        this.playNum = playNum;
    }

    public String getHighlightTitle() {
        return highlightTitle;
    }

    public void setHighlightTitle(String highlightTitle) {
        this.highlightTitle = highlightTitle;
    }

}
