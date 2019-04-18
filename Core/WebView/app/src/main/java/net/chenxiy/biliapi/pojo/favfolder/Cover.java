
package net.chenxiy.biliapi.pojo.favfolder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cover {

    @SerializedName("aid")
    @Expose
    private Integer aid;
    @SerializedName("pic")
    @Expose
    private String pic;
    @SerializedName("type")
    @Expose
    private Integer type;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
