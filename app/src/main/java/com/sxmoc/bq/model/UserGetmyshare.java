package com.sxmoc.bq.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/1/15/015.
 *
 * @author ZhangJieBo
 */

public class UserGetmyshare {
    /**
     * data : [{"count_des":"一级：1人   二级：0人   累计: 1人","grade_name":"普通用户","id":10,"img":"http://www.qizhibq.com/Uploads/attachment/20180109/1_1515465749_1.png","name":"老二"},{"count_des":"一级：0人   二级：0人   累计: 0人","grade_name":"准合伙人","id":1,"img":"http://www.qizhibq.com/Uploads/attachment/20180106/334f7c9ab830cc4ced9adf074ad11c7c.png","name":"15860026753"}]
     * info : 获取成功
     * page : {"dataTotal":2,"page":1,"pageSize":10,"pageTotal":1}
     * partner_num : 1
     * share_num : 3
     * status : 1
     */

    private String info;
    private PageBean page;
    private int partner_num;
    private int share_num;
    private int status;
    private List<DataBean> data;

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

    public int getPartner_num() {
        return partner_num;
    }

    public void setPartner_num(int partner_num) {
        this.partner_num = partner_num;
    }

    public int getShare_num() {
        return share_num;
    }

    public void setShare_num(int share_num) {
        this.share_num = share_num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class PageBean {
        /**
         * dataTotal : 2
         * page : 1
         * pageSize : 10
         * pageTotal : 1
         */

        private int dataTotal;
        private int page;
        private int pageSize;
        private int pageTotal;

        public int getDataTotal() {
            return dataTotal;
        }

        public void setDataTotal(int dataTotal) {
            this.dataTotal = dataTotal;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageTotal() {
            return pageTotal;
        }

        public void setPageTotal(int pageTotal) {
            this.pageTotal = pageTotal;
        }
    }

    public static class DataBean {
        /**
         * count_des : 一级：1人   二级：0人   累计: 1人
         * grade_name : 普通用户
         * id : 10
         * img : http://www.qizhibq.com/Uploads/attachment/20180109/1_1515465749_1.png
         * name : 老二
         */

        private String count_des;
        private String grade_name;
        private int id;
        private int grade_type;
        private String img;
        private String name;

        public int getGrade_type() {
            return grade_type;
        }

        public void setGrade_type(int grade_type) {
            this.grade_type = grade_type;
        }

        public String getCount_des() {
            return count_des;
        }

        public void setCount_des(String count_des) {
            this.count_des = count_des;
        }

        public String getGrade_name() {
            return grade_name;
        }

        public void setGrade_name(String grade_name) {
            this.grade_name = grade_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
