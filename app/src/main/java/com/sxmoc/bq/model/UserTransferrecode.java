package com.sxmoc.bq.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/1/16/016.
 *
 * @author ZhangJieBo
 */

public class UserTransferrecode {
    /**
     * status : 1
     * info : 获取成功
     * page : {"page":1,"pageTotal":1,"pageSize":15,"dataTotal":9}
     * data : [{"id":99,"num":10,"create_time":"2018.01.12 17:16","des":""},{"id":96,"num":10,"create_time":"2018.01.12 17:07","des":""},{"id":76,"num":6,"create_time":"2018.01.11 14:22","des":""},{"id":60,"num":2,"create_time":"2018.01.11 11:48","des":""},{"id":58,"num":2,"create_time":"2018.01.11 09:33","des":""},{"id":43,"num":1,"create_time":"1970.01.01 08:00","des":""},{"id":57,"num":300,"create_time":"1970.01.01 08:00","des":""},{"id":62,"num":5,"create_time":"1970.01.01 08:00","des":""},{"id":63,"num":6,"create_time":"1970.01.01 08:00","des":""}]
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
         * pageTotal : 1
         * pageSize : 15
         * dataTotal : 9
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
         * id : 99
         * num : 10
         * create_time : 2018.01.12 17:16
         * des :
         */

        private int id;
        private int num;
        private String create_time;
        private String des;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }
}
