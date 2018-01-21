package com.sxmoc.bq.model;

/**
 * Created by zhangjiebo on 2018/1/21/021.
 *
 * @author ZhangJieBo
 */

public class UserGetbluetooth {
    /**
     * status : 1
     * info : 获取成功
     * bluetooth_name : Mind Link
     */

    private int status;
    private String info;
    private String bluetooth_name;

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

    public String getBluetooth_name() {
        return bluetooth_name;
    }

    public void setBluetooth_name(String bluetooth_name) {
        this.bluetooth_name = bluetooth_name;
    }
}
