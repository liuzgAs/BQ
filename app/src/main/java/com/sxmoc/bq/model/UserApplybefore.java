package com.sxmoc.bq.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangjiebo on 2018/1/13/013.
 *
 * @author ZhangJieBo
 */

public class UserApplybefore implements Serializable {
    /**
     * bank : []
     * data : {"name":"朱亚文","mobile":"15259712170","card":"350430199103254515","grade":1,"area":"山西-太原市-小店区","address":"厦门市故居"}
     * tipsTitle : 恭喜你已通过审核
     * tipsDes :
     * state : 1
     * grade : [{"value":1,"name":"准合伙人","des":"2台脑波采集仪+60份报告","money":"50000"},{"value":1,"name":"高级合伙人","des":"4台脑波采集仪+120份报告","money":"100000"},{"value":1,"name":"席位合伙人","des":"8台脑波采集仪+240份报告","money":"200000"}]
     * status : 1
     * info : 返回成功！
     */

    private DataBean data;
    private String tipsTitle;
    private String tipsDes;
    private int state;
    private int status;
    private int isup;
    private String info;
    private Bank bank;
    private List<GradeBean> grade;

    public int getIsup() {
        return isup;
    }

    public void setIsup(int isup) {
        this.isup = isup;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public class Bank {
        private String code;
        private String account;
        private String bank;
        private String company;
        private String receiving;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getReceiving() {
            return receiving;
        }

        public void setReceiving(String receiving) {
            this.receiving = receiving;
        }
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public List<GradeBean> getGrade() {
        return grade;
    }

    public void setGrade(List<GradeBean> grade) {
        this.grade = grade;
    }

    public static class DataBean implements Serializable{
        /**
         * name : 朱亚文
         * mobile : 15259712170
         * card : 350430199103254515
         * grade : 1
         * area : 山西-太原市-小店区
         * address : 厦门市故居
         */

        private String name;
        private String mobile;
        private String card;
        private int grade;
        private String area;
        private String address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class GradeBean implements Serializable{
        /**
         * value : 1
         * name : 准合伙人
         * des : 2台脑波采集仪+60份报告
         * money : 50000
         */

        private int value;
        private String name;
        private String des;
        private String money;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
