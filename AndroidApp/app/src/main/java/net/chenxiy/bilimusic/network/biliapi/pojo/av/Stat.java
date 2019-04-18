
package net.chenxiy.bilimusic.network.biliapi.pojo.av;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stat {

    @SerializedName("aid")
    @Expose
    private Integer aid;
    @SerializedName("view")
    @Expose
    private Integer view;
    @SerializedName("danmaku")
    @Expose
    private Integer danmaku;
    @SerializedName("reply")
    @Expose
    private Integer reply;
    @SerializedName("favorite")
    @Expose
    private Integer favorite;
    @SerializedName("coin")
    @Expose
    private Integer coin;
    @SerializedName("share")
    @Expose
    private Integer share;
    @SerializedName("now_rank")
    @Expose
    private Integer nowRank;
    @SerializedName("his_rank")
    @Expose
    private Integer hisRank;
    @SerializedName("like")
    @Expose
    private Integer like;
    @SerializedName("dislike")
    @Expose
    private Integer dislike;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public Integer getDanmaku() {
        return danmaku;
    }

    public void setDanmaku(Integer danmaku) {
        this.danmaku = danmaku;
    }

    public Integer getReply() {
        return reply;
    }

    public void setReply(Integer reply) {
        this.reply = reply;
    }

    public Integer getFavorite() {
        return favorite;
    }

    public void setFavorite(Integer favorite) {
        this.favorite = favorite;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public Integer getShare() {
        return share;
    }

    public void setShare(Integer share) {
        this.share = share;
    }

    public Integer getNowRank() {
        return nowRank;
    }

    public void setNowRank(Integer nowRank) {
        this.nowRank = nowRank;
    }

    public Integer getHisRank() {
        return hisRank;
    }

    public void setHisRank(Integer hisRank) {
        this.hisRank = hisRank;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getDislike() {
        return dislike;
    }

    public void setDislike(Integer dislike) {
        this.dislike = dislike;
    }

}
