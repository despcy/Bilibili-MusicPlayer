
package net.chenxiy.bilimusic.network.biliapi.pojo.av.download;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Durl {

    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("length")
    @Expose
    private Integer length;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("ahead")
    @Expose
    private String ahead;
    @SerializedName("vhead")
    @Expose
    private String vhead;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("backup_url")
    @Expose
    private List<String> backupUrl = null;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getAhead() {
        return ahead;
    }

    public void setAhead(String ahead) {
        this.ahead = ahead;
    }

    public String getVhead() {
        return vhead;
    }

    public void setVhead(String vhead) {
        this.vhead = vhead;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getBackupUrl() {
        return backupUrl;
    }

    public void setBackupUrl(List<String> backupUrl) {
        this.backupUrl = backupUrl;
    }

}
