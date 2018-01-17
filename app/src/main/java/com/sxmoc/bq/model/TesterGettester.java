package com.sxmoc.bq.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/1/17/017.
 *
 * @author ZhangJieBo
 */

public class TesterGettester {
    /**
     * status : 1
     * info : 成功
     * page : {"page":1,"pageTotal":2,"pageSize":15,"dataTotal":30}
     * data : [{"uid":7,"name":"yyyu","sex":0,"birthday":"2018-01-17","bid":208},{"uid":7,"name":"阿爸","sex":0,"birthday":"2018-01-17","bid":197},{"uid":7,"name":"阿宝","sex":0,"birthday":"2018-01-17","bid":196},{"uid":7,"name":"班","sex":0,"birthday":"2018-01-17","bid":195},{"uid":7,"name":"搬家","sex":0,"birthday":"2018-01-16","bid":191},{"uid":7,"name":"将计就计","sex":1,"birthday":"2018-01-16","bid":190},{"uid":7,"name":"Mon","sex":0,"birthday":"2018-01-16","bid":189},{"uid":7,"name":"本命","sex":0,"birthday":"2018-01-16","bid":188},{"uid":7,"name":"接口","sex":0,"birthday":"2018-01-16","bid":187},{"uid":7,"name":"看见了","sex":0,"birthday":"2018-01-16","bid":186},{"uid":7,"name":"Mon","sex":0,"birthday":"2018-01-16","bid":184},{"uid":7,"name":"看见了","sex":0,"birthday":"2018-01-12","bid":183},{"uid":7,"name":"阿宝","sex":0,"birthday":"2018-01-16","bid":182},{"uid":7,"name":"阿爸","sex":0,"birthday":"2018-01-16","bid":181},{"uid":7,"name":"班","sex":0,"birthday":"2018-01-16","bid":179}]
     */

    private int status;
    private String info;
    private PageBean page;
    private List<DataBean> data;

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

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class PageBean {
        /**
         * page : 1
         * pageTotal : 2
         * pageSize : 15
         * dataTotal : 30
         */

        private int page;
        private int pageTotal;
        private int pageSize;
        private int dataTotal;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPageTotal() {
            return pageTotal;
        }

        public void setPageTotal(int pageTotal) {
            this.pageTotal = pageTotal;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getDataTotal() {
            return dataTotal;
        }

        public void setDataTotal(int dataTotal) {
            this.dataTotal = dataTotal;
        }
    }

    public static class DataBean {
        /**
         * uid : 7
         * name : yyyu
         * sex : 0
         * birthday : 2018-01-17
         * bid : 208
         */

        private int uid;
        private String name;
        private int sex;
        private String birthday;
        private int bid;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getBid() {
            return bid;
        }

        public void setBid(int bid) {
            this.bid = bid;
        }
    }
}
