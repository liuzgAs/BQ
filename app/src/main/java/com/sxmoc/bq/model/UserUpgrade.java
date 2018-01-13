package com.sxmoc.bq.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/1/13/013.
 *
 * @author ZhangJieBo
 */

public class UserUpgrade {
    private List<DataBean> data;
    private List<GradeBean> grade;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<GradeBean> getGrade() {
        return grade;
    }

    public void setGrade(List<GradeBean> grade) {
        this.grade = grade;
    }

    public static class DataBean {
        /**
         * name : 直接推荐的共享用户
         * value : 0
         * dw : 人
         */

        private String name;
        private int value;
        private String dw;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getDw() {
            return dw;
        }

        public void setDw(String dw) {
            this.dw = dw;
        }
    }

    public static class GradeBean {
        /**
         * value : 1
         * isLock : 1
         * name : 准合伙人
         * des : 市场合伙人最高等级
         * isUp : 0
         */

        private int value;
        private int isLock;
        private String name;
        private String des;
        private int isUp;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getIsLock() {
            return isLock;
        }

        public void setIsLock(int isLock) {
            this.isLock = isLock;
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

        public int getIsUp() {
            return isUp;
        }

        public void setIsUp(int isUp) {
            this.isUp = isUp;
        }
    }
}
