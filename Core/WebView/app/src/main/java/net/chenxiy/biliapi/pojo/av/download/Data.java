
package net.chenxiy.biliapi.pojo.av.download;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("quality")
    @Expose
    private Integer quality;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("timelength")
    @Expose
    private Integer timelength;
    @SerializedName("accept_format")
    @Expose
    private String acceptFormat;
    @SerializedName("accept_description")
    @Expose
    private List<String> acceptDescription = null;
    @SerializedName("accept_quality")
    @Expose
    private List<Integer> acceptQuality = null;
    @SerializedName("video_codecid")
    @Expose
    private Integer videoCodecid;
    @SerializedName("seek_param")
    @Expose
    private String seekParam;
    @SerializedName("seek_type")
    @Expose
    private String seekType;
    @SerializedName("dash")
    @Expose
    private DownloadResources downloadResources;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getTimelength() {
        return timelength;
    }

    public void setTimelength(Integer timelength) {
        this.timelength = timelength;
    }

    public String getAcceptFormat() {
        return acceptFormat;
    }

    public void setAcceptFormat(String acceptFormat) {
        this.acceptFormat = acceptFormat;
    }

    public List<String> getAcceptDescription() {
        return acceptDescription;
    }

    public void setAcceptDescription(List<String> acceptDescription) {
        this.acceptDescription = acceptDescription;
    }

    public List<Integer> getAcceptQuality() {
        return acceptQuality;
    }

    public void setAcceptQuality(List<Integer> acceptQuality) {
        this.acceptQuality = acceptQuality;
    }

    public Integer getVideoCodecid() {
        return videoCodecid;
    }

    public void setVideoCodecid(Integer videoCodecid) {
        this.videoCodecid = videoCodecid;
    }

    public String getSeekParam() {
        return seekParam;
    }

    public void setSeekParam(String seekParam) {
        this.seekParam = seekParam;
    }

    public String getSeekType() {
        return seekType;
    }

    public void setSeekType(String seekType) {
        this.seekType = seekType;
    }

    public DownloadResources getDownloadResources() {
        return downloadResources;
    }

    public void setDownloadResources(DownloadResources downloadResources) {
        this.downloadResources = downloadResources;
    }

}
