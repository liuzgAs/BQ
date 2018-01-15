package com.sxmoc.bq.model;

/**
 * Created by zhangjiebo on 2018/1/15/015.
 *
 * @author ZhangJieBo
 */

public class UserShare {
    /**
     * status : 1
     * info : 获取成功
     * can_share : 1
     * share_url : http://bq.sxmoc.com
     */

    private int status;
    private String info;
    private int can_share;
    private String share_url;

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

    public int getCan_share() {
        return can_share;
    }

    public void setCan_share(int can_share) {
        this.can_share = can_share;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }
}
