
package net.chenxiy.bilimusic.network.biliapi.pojo.favfolder.add_del_folder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("fid")
    @Expose
    private Integer fid;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

}
