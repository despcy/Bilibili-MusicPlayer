
package net.chenxiy.bilimusic.network.biliapi.pojo.dynamic;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DynamicResponse {

    @SerializedName("exp_list")
    @Expose
    private Object expList;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("cost_time")
    @Expose
    private CostTime costTime;
    @SerializedName("result")
    @Expose
    private List<HotSong> hotSong = null;
    @SerializedName("show_column")
    @Expose
    private Integer showColumn;
    @SerializedName("rqt_type")
    @Expose
    private String rqtType;
    @SerializedName("numPages")
    @Expose
    private Integer numPages;
    @SerializedName("numResults")
    @Expose
    private Integer numResults;
    @SerializedName("crr_query")
    @Expose
    private String crrQuery;
    @SerializedName("pagesize")
    @Expose
    private Integer pagesize;
    @SerializedName("suggest_keyword")
    @Expose
    private String suggestKeyword;
    @SerializedName("egg_info")
    @Expose
    private Object eggInfo;
    @SerializedName("exp_bits")
    @Expose
    private Integer expBits;
    @SerializedName("seid")
    @Expose
    private String seid;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("egg_hit")
    @Expose
    private Integer eggHit;
    @SerializedName("page")
    @Expose
    private Integer page;

    public Object getExpList() {
        return expList;
    }

    public void setExpList(Object expList) {
        this.expList = expList;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public CostTime getCostTime() {
        return costTime;
    }

    public void setCostTime(CostTime costTime) {
        this.costTime = costTime;
    }

    public List<HotSong> getHotSong() {
        return hotSong;
    }

    public void setHotSong(List<HotSong> hotSong) {
        this.hotSong = hotSong;
    }

    public Integer getShowColumn() {
        return showColumn;
    }

    public void setShowColumn(Integer showColumn) {
        this.showColumn = showColumn;
    }

    public String getRqtType() {
        return rqtType;
    }

    public void setRqtType(String rqtType) {
        this.rqtType = rqtType;
    }

    public Integer getNumPages() {
        return numPages;
    }

    public void setNumPages(Integer numPages) {
        this.numPages = numPages;
    }

    public Integer getNumResults() {
        return numResults;
    }

    public void setNumResults(Integer numResults) {
        this.numResults = numResults;
    }

    public String getCrrQuery() {
        return crrQuery;
    }

    public void setCrrQuery(String crrQuery) {
        this.crrQuery = crrQuery;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public String getSuggestKeyword() {
        return suggestKeyword;
    }

    public void setSuggestKeyword(String suggestKeyword) {
        this.suggestKeyword = suggestKeyword;
    }

    public Object getEggInfo() {
        return eggInfo;
    }

    public void setEggInfo(Object eggInfo) {
        this.eggInfo = eggInfo;
    }

    public Integer getExpBits() {
        return expBits;
    }

    public void setExpBits(Integer expBits) {
        this.expBits = expBits;
    }

    public String getSeid() {
        return seid;
    }

    public void setSeid(String seid) {
        this.seid = seid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getEggHit() {
        return eggHit;
    }

    public void setEggHit(Integer eggHit) {
        this.eggHit = eggHit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

}
