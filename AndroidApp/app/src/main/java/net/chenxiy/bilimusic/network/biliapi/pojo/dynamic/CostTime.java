
package net.chenxiy.bilimusic.network.biliapi.pojo.dynamic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CostTime {

    @SerializedName("params_check")
    @Expose
    private String paramsCheck;
    @SerializedName("illegal_handler")
    @Expose
    private String illegalHandler;
    @SerializedName("as_response_format")
    @Expose
    private String asResponseFormat;
    @SerializedName("as_request")
    @Expose
    private String asRequest;
    @SerializedName("deserialize_response")
    @Expose
    private String deserializeResponse;
    @SerializedName("as_request_format")
    @Expose
    private String asRequestFormat;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("main_handler")
    @Expose
    private String mainHandler;

    public String getParamsCheck() {
        return paramsCheck;
    }

    public void setParamsCheck(String paramsCheck) {
        this.paramsCheck = paramsCheck;
    }

    public String getIllegalHandler() {
        return illegalHandler;
    }

    public void setIllegalHandler(String illegalHandler) {
        this.illegalHandler = illegalHandler;
    }

    public String getAsResponseFormat() {
        return asResponseFormat;
    }

    public void setAsResponseFormat(String asResponseFormat) {
        this.asResponseFormat = asResponseFormat;
    }

    public String getAsRequest() {
        return asRequest;
    }

    public void setAsRequest(String asRequest) {
        this.asRequest = asRequest;
    }

    public String getDeserializeResponse() {
        return deserializeResponse;
    }

    public void setDeserializeResponse(String deserializeResponse) {
        this.deserializeResponse = deserializeResponse;
    }

    public String getAsRequestFormat() {
        return asRequestFormat;
    }

    public void setAsRequestFormat(String asRequestFormat) {
        this.asRequestFormat = asRequestFormat;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getMainHandler() {
        return mainHandler;
    }

    public void setMainHandler(String mainHandler) {
        this.mainHandler = mainHandler;
    }

}
