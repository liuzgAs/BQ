package com.sxmoc.bq.model;

/**
 * Created by zhangjiebo on 2018/1/11/011.
 *
 * @author ZhangJieBo
 */

public class UserBuyerindex {
    /**
     * money : 19999.58
     * report_num : 314
     * headimg : http://bq.sxmoc.com/Uploads/avstar.png
     * nickname : benzxx
     * grade : 0
     * grade_name : 普通会员
     * mobile : 18250131297
     * status : 1
     * info : 返回成功！
     */

    private double money;
    private String report_num;
    private String headimg;
    private String nickname;
    private int grade;
    private String grade_name;
    private String mobile;
    private int status;
    private String info;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getReport_num() {
        return report_num;
    }

    public void setReport_num(String report_num) {
        this.report_num = report_num;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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