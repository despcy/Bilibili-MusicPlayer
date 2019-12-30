
package net.chenxiy.bilimusic.network.biliapi.pojo.favfolder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("list")
    @Expose
    private List<FolderArchive> folderArchive = null;
    @SerializedName("count")
    @Expose
    private Integer playlist;


    public List<FolderArchive> getFolderArchive() {
        return folderArchive;
    }

    public void setFolderArchive(List<FolderArchive> folderArchive) {
        this.folderArchive = folderArchive;
    }

    public Integer getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Integer playlist) {
        this.playlist = playlist;
    }



}
