
package net.chenxiy.bilimusic.network.biliapi.pojo.av.download;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SegmentBase {

    @SerializedName("Initialization")
    @Expose
    private String initialization;
    @SerializedName("indexRange")
    @Expose
    private String indexRange;

    public String getInitialization() {
        return initialization;
    }

    public void setInitialization(String initialization) {
        this.initialization = initialization;
    }

    public String getIndexRange() {
        return indexRange;
    }

    public void setIndexRange(String indexRange) {
        this.indexRange = indexRange;
    }

}
