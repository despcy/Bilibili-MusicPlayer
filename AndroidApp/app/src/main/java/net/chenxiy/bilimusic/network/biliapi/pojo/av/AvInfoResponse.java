
package net.chenxiy.bilimusic.network.biliapi.pojo.av;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvInfoResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("ttl")
    @Expose
    private Integer ttl;
    @SerializedName("data")
    @Expose
    private AvData avData;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public AvData getAvData() {
        return avData;
    }

    public void setAvData(AvData avData) {
        this.avData = avData;
    }

}
