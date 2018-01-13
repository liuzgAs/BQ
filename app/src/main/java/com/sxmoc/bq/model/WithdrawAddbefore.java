package com.sxmoc.bq.model;

/**
 * Created by zhangjiebo on 2018/1/13/013.
 *
 * @author ZhangJieBo
 */

public class WithdrawAddbefore {
    /**
     * money : 3509
     * moneyDes : 当前帐户余额￥3509
     * min : 10
     * des : 提现手续费及代缴税费为6%，到帐时间为T+1
     * status : 1
     * info : 返回成功！
     */

    private double money;
    private String moneyDes;
    private String min;
    private String des;
    private int status;
    private String info;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getMoneyDes() {
        return moneyDes;
    }

    public void setMoneyDes(String moneyDes) {
        this.moneyDes = moneyDes;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
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
