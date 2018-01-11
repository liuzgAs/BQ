package com.sxmoc.bq.model;

/**
 * Created by zhangjiebo on 2018/1/11/011.
 *
 * @author ZhangJieBo
 */

public class UserGetbalance {
    /**
     * status : 1
     * info : 获取成功
     * balance : 10.1
     */

    private int status;
    private String info;
    private double balance;

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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
