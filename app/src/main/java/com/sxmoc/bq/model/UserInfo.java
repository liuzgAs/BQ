package com.sxmoc.bq.model;

import java.io.Serializable;

public class UserInfo implements Serializable {
    /**
     * uid : 2
     * headImg : http://192.168.1.181/Uploads/avstar.png
     * userName : 15871105320
     * status : 1
     * info : 操作成功！
     */

    private String uid;
    private String headImg;
    private String userName;
    private int status;
    private String info;
    private String yunToken;

    public String getYunToken() {
        return yunToken;
    }

    public void setYunToken(String yunToken) {
        this.yunToken = yunToken;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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