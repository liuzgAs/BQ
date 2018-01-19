package com.sxmoc.bq.model;

/**
 * Created by zhangjiebo on 2018/1/19/019.
 *
 * @author ZhangJieBo
 */

public class BuyerSavedata {
    /**
     * message : 获取短信成功
     * statue : 1
     */

    private String info;
    private int status;
    private int sid;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

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
}
