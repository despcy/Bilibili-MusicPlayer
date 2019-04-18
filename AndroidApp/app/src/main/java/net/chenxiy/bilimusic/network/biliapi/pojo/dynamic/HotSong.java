
package net.chenxiy.bilimusic.network.biliapi.pojo.dynamic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HotSong {

    @SerializedName("senddate")
    @Expose
    private Integer senddate;
    @SerializedName("rank_offset")
    @Expose
    private Integer rankOffset;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("rank_score")
    @Expose
    private Integer rankScore;
    @SerializedName("badgepay")
    @Expose
    private Boolean badgepay;
    @SerializedName("pubdate")
    @Expose
    private String pubdate;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("review")
    @Expose
    private Integer review;
    @SerializedName("mid")
    @Expose
    private Integer mid;
    @SerializedName("is_union_video")
    @Expose
    private Integer isUnionVideo;
    @SerializedName("rank_index")
    @Expose
    private Integer rankIndex;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("arcrank")
    @Expose
    private String arcrank;
    @SerializedName("play")
    @Expose
    private String play;
    @SerializedName("pic")
    @Expose
    private String pic;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("video_review")
    @Expose
    private Integer videoReview;
    @SerializedName("is_pay")
    @Expose
    private Integer isPay;
    @SerializedName("favorites")
    @Expose
    private Integer favorites;
    @SerializedName("arcurl")
    @Expose
    private String arcurl;
    @SerializedName("author")
    @Expose
    private String author;

    public Integer getSenddate() {
        return senddate;
    }

    public void setSenddate(Integer senddate) {
        this.senddate = senddate;
    }

    public Integer getRankOffset() {
        return rankOffset;
    }

    public void setRankOffset(Integer rankOffset) {
        this.rankOffset = rankOffset;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRankScore() {
        return rankScore;
    }

    public void setRankScore(Integer rankScore) {
        this.rankScore = rankScore;
    }

    public Boolean getBadgepay() {
        return badgepay;
    }

    public void setBadgepay(Boolean badgepay) {
        this.badgepay = badgepay;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReview() {
        return review;
    }

    public void setReview(Integer review) {
        this.review = review;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getIsUnionVideo() {
        return isUnionVideo;
    }

    public void setIsUnionVideo(Integer isUnionVideo) {
        this.isUnionVideo = isUnionVideo;
    }

    public Integer getRankIndex() {
        return rankIndex;
    }

    public void setRankIndex(Integer rankIndex) {
        this.rankIndex = rankIndex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArcrank() {
        return arcrank;
    }

    public void setArcrank(String arcrank) {
        this.arcrank = arcrank;
    }

    public String getPlay() {
        return play;
    }

    public void setPlay(String play) {
        this.play = play;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVideoReview() {
        return videoReview;
    }

    public void setVideoReview(Integer videoReview) {
        this.videoReview = videoReview;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public Integer getFavorites() {
        return favorites;
    }

    public void setFavorites(Integer favorites) {
        this.favorites = favorites;
    }

    public String getArcurl() {
        return arcurl;
    }

    public void setArcurl(String arcurl) {
        this.arcurl = arcurl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
