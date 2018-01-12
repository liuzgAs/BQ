package com.sxmoc.bq.model;

/**
 * Created by zhangjiebo on 2018/1/12/012.
 *
 * @author ZhangJieBo
 */

public class BuyerAddinfo {
    /**
     * status : 1
     * info : 提交成功
     * bid : 6
     */

    private int status;
    private String info;
    private int bid;

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

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }
}
