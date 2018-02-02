package com.sxmoc.bq.model;

/**
 * Created by zhangjiebo on 2018/2/2/002.
 *
 * @author ZhangJieBo
 */

public class UserTransfertips {
    /**
     * status : 1
     * info : 获取成功
     * d_nickname :
     */

    private int status;
    private String info;
    private String d_nickname;

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

    public String getD_nickname() {
        return d_nickname;
    }

    public void setD_nickname(String d_nickname) {
        this.d_nickname = d_nickname;
    }
}
