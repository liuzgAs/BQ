package com.sxmoc.bq.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhangjiebo on 2018/1/23/023.
 *
 * @author ZhangJieBo
 */

public class PayWxpay {
    /**
     * status : 1
     * info : success
     * appid :
     * partnerid : fsfdsgfre654t
     * prepayid : gdfgffd5646543gd
     * nonceStr : fdsy754hgfdhgbd
     * timeStamp : 1526352452
     * package : Sign=WXPay
     * sign : 54yhhbgffdgsfdbgdfgbfgfncewrqGsMfIfd=
     */

    private int status;
    private String info;
    private String appid;
    private String partnerid;
    private String prepayid;
    private String nonceStr;
    private String timeStamp;
    @SerializedName("package")
    private String packageX;
    private String sign;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
