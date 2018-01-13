package com.sxmoc.bq.model;

/**
 * Created by zhangjiebo on 2018/1/13/013.
 *
 * @author ZhangJieBo
 */

public class UserApply {
    /**
     * tipsTitle : 提交成功
     * tipsDes : 我们会尽快为您审核
     * state : 1
     * status : 1
     * info : 操作成功！
     */

    private String tipsTitle;
    private String tipsDes;
    private int state;
    private int status;
    private String info;

    public String getTipsTitle() {
        return tipsTitle;
    }

    public void setTipsTitle(String tipsTitle) {
        this.tipsTitle = tipsTitle;
    }

    public String getTipsDes() {
        return tipsDes;
    }

    public void setTipsDes(String tipsDes) {
        this.tipsDes = tipsDes;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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
