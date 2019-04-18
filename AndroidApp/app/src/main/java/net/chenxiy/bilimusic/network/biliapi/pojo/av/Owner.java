
package net.chenxiy.bilimusic.network.biliapi.pojo.av;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Owner {

    @SerializedName("mid")
    @Expose
    private Integer mid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("face")
    @Expose
    private String face;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

}
