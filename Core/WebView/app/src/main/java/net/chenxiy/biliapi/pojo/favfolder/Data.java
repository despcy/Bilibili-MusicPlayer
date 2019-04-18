
package net.chenxiy.biliapi.pojo.favfolder;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("archive")
    @Expose
    private List<FolderArchive> folderArchive = null;
    @SerializedName("playlist")
    @Expose
    private Integer playlist;
    @SerializedName("topic")
    @Expose
    private Integer topic;
    @SerializedName("article")
    @Expose
    private Integer article;
    @SerializedName("album")
    @Expose
    private Integer album;
    @SerializedName("movie")
    @Expose
    private Integer movie;

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

    public Integer getTopic() {
        return topic;
    }

    public void setTopic(Integer topic) {
        this.topic = topic;
    }

    public Integer getArticle() {
        return article;
    }

    public void setArticle(Integer article) {
        this.article = article;
    }

    public Integer getAlbum() {
        return album;
    }

    public void setAlbum(Integer album) {
        this.album = album;
    }

    public Integer getMovie() {
        return movie;
    }

    public void setMovie(Integer movie) {
        this.movie = movie;
    }

}
