package com.sxmoc.bq.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/1/13/013.
 *
 * @author ZhangJieBo
 */

public class Article {
    /**
     * data : [{"url":"http://www.qizhibq.com/api/Article/info/id/30","title":"小婴孩智能启蒙全书：数学启蒙","img":"http://www.qizhibq.com","des":"2018-01-11"},{"url":"http://www.qizhibq.com/api/Article/info/id/26","title":"启蒙之后的的启蒙：启蒙世界观的内在逻辑与当代反思","img":"http://www.qizhibq.com/Uploads/attachment/20180110/824274394131c4d10b3c03303b1a1c03.jpg","des":"2017-12-12"}]
     * page : {"page":"1","pageTotal":1,"pageSize":10,"dataTotal":2}
     * status : 1
     * info : 返回成功！
     */

    private PageBean page;
    private int status;
    private String info;
    private List<DataBean> data;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
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
         * pageSize : 10
         * dataTotal : 2
         */

        private String page;
        private int pageTotal;
        private int pageSize;
        private int dataTotal;

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
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
         * url : http://www.qizhibq.com/api/Article/info/id/30
         * title : 小婴孩智能启蒙全书：数学启蒙
         * img : http://www.qizhibq.com
         * des : 2018-01-11
         */

        private String url;
        private String title;
        private String img;
        private String des;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }
}
