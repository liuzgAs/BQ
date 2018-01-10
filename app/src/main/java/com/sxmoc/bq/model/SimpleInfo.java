package com.sxmoc.bq.model;

/**
 * Created by zhangjiebo on 2017/8/22 0022.
 */
public class SimpleInfo {
    /**
     * message : 获取短信成功
     * statue : 1
     */

    private String info;
    private int status;

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
