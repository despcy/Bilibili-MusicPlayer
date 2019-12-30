
package net.chenxiy.bilimusic.network.biliapi.pojo.favfolder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavFolderInfoResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private Data data;

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



    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
