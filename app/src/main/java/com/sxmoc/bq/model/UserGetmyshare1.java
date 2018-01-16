package com.sxmoc.bq.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/1/16/016.
 *
 * @author ZhangJieBo
 */

public class UserGetmyshare1 {
    /**
     * data : [{"count_des":"一级：0人   二级：0人   累计: 0人","grade_name":"普通用户","grade_type":5,"id":14,"img":"http://www.qizhibq.com/Uploads/attachment/20180106/e6ebeb5e124cb9e4866e7eb196aef129.png","name":"老三"}]
     * grade_name : 普通用户
     * info : 获取成功
     * name : 老二
     * page : {"dataTotal":1,"page":1,"pageSize":10,"pageTotal":1}
     * share_num : 1
     * status : 1
     * z_img : /Uploads/attachment/20180106/e6ebeb5e124cb9e4866e7eb196aef129.png
     */

    private String grade_name;
    private String info;
    private String name;
    private PageBean page;
    private int share_num;
    private int status;
    private String z_img;
    private List<DataBean> data;

    public String getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
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

    public String getZ_img() {
        return z_img;
    }

    public void setZ_img(String z_img) {
        this.z_img = z_img;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class PageBean {
        /**
         * dataTotal : 1
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
         * count_des : 一级：0人   二级：0人   累计: 0人
         * grade_name : 普通用户
         * grade_type : 5
         * id : 14
         * img : http://www.qizhibq.com/Uploads/attachment/20180106/e6ebeb5e124cb9e4866e7eb196aef129.png
         * name : 老三
         */

        private String count_des;
        private String grade_name;
        private int grade_type;
        private int id;
        private String img;
        private String name;

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

        public int getGrade_type() {
            return grade_type;
        }

        public void setGrade_type(int grade_type) {
            this.grade_type = grade_type;
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
