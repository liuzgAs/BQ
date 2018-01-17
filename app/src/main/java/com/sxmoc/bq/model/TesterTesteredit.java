package com.sxmoc.bq.model;

/**
 * Created by zhangjiebo on 2018/1/17/017.
 *
 * @author ZhangJieBo
 */

public class TesterTesteredit {
    /**
     * status : 1
     * info : 获取成功
     * data : {"uid":7,"bid":"191","name":"搬家","birthday":"2018.01.16","sex":0,"school_name":"空军建军节","mailbox":"gddd@fqq.com","grade":6,"create_time":"2018.01.16"}
     */

    private int status;
    private String info;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * uid : 7
         * bid : 191
         * name : 搬家
         * birthday : 2018.01.16
         * sex : 0
         * school_name : 空军建军节
         * mailbox : gddd@fqq.com
         * grade : 6
         * create_time : 2018.01.16
         */

        private int uid;
        private String bid;
        private String name;
        private String birthday;
        private int sex;
        private String school_name;
        private String mailbox;
        private int grade;
        private String create_time;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getMailbox() {
            return mailbox;
        }

        public void setMailbox(String mailbox) {
            this.mailbox = mailbox;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
