
package net.chenxiy.bilimusic.network.biliapi.pojo.av;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Page {

    @SerializedName("cid")
    @Expose
    private Integer cid;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("part")
    @Expose
    private String part;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("vid")
    @Expose
    private String vid;
    @SerializedName("weblink")
    @Expose
    private String weblink;
    @SerializedName("dimension")
    @Expose
    private Dimension dimension;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getWeblink() {
        return weblink;
    }

    public void setWeblink(String weblink) {
        this.weblink = weblink;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

}
