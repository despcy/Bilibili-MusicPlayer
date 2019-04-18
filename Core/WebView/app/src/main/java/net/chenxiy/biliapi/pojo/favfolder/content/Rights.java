
package net.chenxiy.biliapi.pojo.favfolder.content;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rights {

    @SerializedName("bp")
    @Expose
    private Integer bp;
    @SerializedName("elec")
    @Expose
    private Integer elec;
    @SerializedName("download")
    @Expose
    private Integer download;
    @SerializedName("movie")
    @Expose
    private Integer movie;
    @SerializedName("pay")
    @Expose
    private Integer pay;
    @SerializedName("hd5")
    @Expose
    private Integer hd5;
    @SerializedName("no_reprint")
    @Expose
    private Integer noReprint;
    @SerializedName("autoplay")
    @Expose
    private Integer autoplay;
    @SerializedName("ugc_pay")
    @Expose
    private Integer ugcPay;
    @SerializedName("is_cooperation")
    @Expose
    private Integer isCooperation;

    public Integer getBp() {
        return bp;
    }

    public void setBp(Integer bp) {
        this.bp = bp;
    }

    public Integer getElec() {
        return elec;
    }

    public void setElec(Integer elec) {
        this.elec = elec;
    }

    public Integer getDownload() {
        return download;
    }

    public void setDownload(Integer download) {
        this.download = download;
    }

    public Integer getMovie() {
        return movie;
    }

    public void setMovie(Integer movie) {
        this.movie = movie;
    }

    public Integer getPay() {
        return pay;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
    }

    public Integer getHd5() {
        return hd5;
    }

    public void setHd5(Integer hd5) {
        this.hd5 = hd5;
    }

    public Integer getNoReprint() {
        return noReprint;
    }

    public void setNoReprint(Integer noReprint) {
        this.noReprint = noReprint;
    }

    public Integer getAutoplay() {
        return autoplay;
    }

    public void setAutoplay(Integer autoplay) {
        this.autoplay = autoplay;
    }

    public Integer getUgcPay() {
        return ugcPay;
    }

    public void setUgcPay(Integer ugcPay) {
        this.ugcPay = ugcPay;
    }

    public Integer getIsCooperation() {
        return isCooperation;
    }

    public void setIsCooperation(Integer isCooperation) {
        this.isCooperation = isCooperation;
    }

}
