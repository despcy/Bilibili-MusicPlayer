
package net.chenxiy.bilimusic.network.biliapi.pojo.av.download;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("baseUrl")
    @Expose
    private String baseUrl;
    @SerializedName("backupUrl")
    @Expose
    private Object backupUrl;
    @SerializedName("bandwidth")
    @Expose
    private Integer bandwidth;
    @SerializedName("mimeType")
    @Expose
    private String mimeType;
    @SerializedName("codecs")
    @Expose
    private String codecs;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("frameRate")
    @Expose
    private String frameRate;
    @SerializedName("sar")
    @Expose
    private String sar;
    @SerializedName("startWithSap")
    @Expose
    private Integer startWithSap;
    @SerializedName("SegmentBase")
    @Expose
    private SegmentBase segmentBase;
    @SerializedName("codecid")
    @Expose
    private Integer codecid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Object getBackupUrl() {
        return backupUrl;
    }

    public void setBackupUrl(Object backupUrl) {
        this.backupUrl = backupUrl;
    }

    public Integer getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(Integer bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getCodecs() {
        return codecs;
    }

    public void setCodecs(String codecs) {
        this.codecs = codecs;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(String frameRate) {
        this.frameRate = frameRate;
    }

    public String getSar() {
        return sar;
    }

    public void setSar(String sar) {
        this.sar = sar;
    }

    public Integer getStartWithSap() {
        return startWithSap;
    }

    public void setStartWithSap(Integer startWithSap) {
        this.startWithSap = startWithSap;
    }

    public SegmentBase getSegmentBase() {
        return segmentBase;
    }

    public void setSegmentBase(SegmentBase segmentBase) {
        this.segmentBase = segmentBase;
    }

    public Integer getCodecid() {
        return codecid;
    }

    public void setCodecid(Integer codecid) {
        this.codecid = codecid;
    }

}
