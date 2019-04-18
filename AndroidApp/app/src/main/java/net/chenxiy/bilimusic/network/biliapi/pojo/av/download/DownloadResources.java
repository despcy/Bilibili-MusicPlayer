
package net.chenxiy.bilimusic.network.biliapi.pojo.av.download;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DownloadResources {

    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("minBufferTime")
    @Expose
    private Double minBufferTime;
    @SerializedName("video")
    @Expose
    private List<Video> video = null;
    @SerializedName("audio")
    @Expose
    private List<Audio> audio = null;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getMinBufferTime() {
        return minBufferTime;
    }

    public void setMinBufferTime(Double minBufferTime) {
        this.minBufferTime = minBufferTime;
    }

    public List<Video> getVideo() {
        return video;
    }

    public void setVideo(List<Video> video) {
        this.video = video;
    }

    public List<Audio> getAudio() {
        return audio;
    }

    public void setAudio(List<Audio> audio) {
        this.audio = audio;
    }

}
