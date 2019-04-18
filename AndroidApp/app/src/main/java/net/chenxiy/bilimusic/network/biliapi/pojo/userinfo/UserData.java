
package net.chenxiy.bilimusic.network.biliapi.pojo.userinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "userData")
public class UserData {

    @PrimaryKey
    @SerializedName("mid")
    @Expose
    private Integer mid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("face")
    @Expose
    private String face;
    @SerializedName("sign")
    @Expose
    private String sign;
    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("level")
    @Expose
    private Integer level;
    @SerializedName("jointime")
    @Expose
    private Integer jointime;
    @SerializedName("moral")
    @Expose
    private Integer moral;
    @SerializedName("silence")
    @Expose
    private Integer silence;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("coins")
    @Expose
    private Double coins;
    @SerializedName("fans_badge")
    @Expose
    private Boolean fansBadge;
    @Embedded
    @SerializedName("official")
    @Expose
    private Official official;
    @Embedded
    @SerializedName("vip")
    @Expose
    private Vip vip;
    @SerializedName("is_followed")
    @Expose
    private Boolean isFollowed;
    @SerializedName("top_photo")
    @Expose
    private String topPhoto;
    @Embedded
    @SerializedName("theme")
    @Expose
    private Theme theme;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getJointime() {
        return jointime;
    }

    public void setJointime(Integer jointime) {
        this.jointime = jointime;
    }

    public Integer getMoral() {
        return moral;
    }

    public void setMoral(Integer moral) {
        this.moral = moral;
    }

    public Integer getSilence() {
        return silence;
    }

    public void setSilence(Integer silence) {
        this.silence = silence;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Double getCoins() {
        return coins;
    }

    public void setCoins(Double coins) {
        this.coins = coins;
    }

    public Boolean getFansBadge() {
        return fansBadge;
    }

    public void setFansBadge(Boolean fansBadge) {
        this.fansBadge = fansBadge;
    }

    public Official getOfficial() {
        return official;
    }

    public void setOfficial(Official official) {
        this.official = official;
    }

    public Vip getVip() {
        return vip;
    }

    public void setVip(Vip vip) {
        this.vip = vip;
    }

    public Boolean getIsFollowed() {
        return isFollowed;
    }

    public void setIsFollowed(Boolean isFollowed) {
        this.isFollowed = isFollowed;
    }

    public String getTopPhoto() {
        return topPhoto;
    }

    public void setTopPhoto(String topPhoto) {
        this.topPhoto = topPhoto;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

}
