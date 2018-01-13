package com.sxmoc.bq.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/1/13/013.
 *
 * @author ZhangJieBo
 */

public class IndexFindindex {
    /**
     * banner : [{"code":"","img":"http://www.qizhibq.com","item_id":0},{"code":"","img":"http://www.qizhibq.com/Uploads/attachment/20180110/45bbdb32ecf6084d2d6b25540e0bb6ce.jpg","item_id":0},{"code":"","img":"http://www.qizhibq.com/Uploads/attachment/20180110/c2beca0e2ce7d40fbb97ad0550ee9ad9.jpg","item_id":0},{"code":"","img":"http://www.qizhibq.com/Uploads/attachment/20180110/051dd894e5ab0948b59a09ecb8002ff0.jpg","item_id":0}]
     * data : [{"des":"2018-01-11","img":"http://www.qizhibq.com","title":"完美素质家教启蒙全书：想象力启蒙书","url":"http://www.qizhibq.com/api/Article/info/id/29/uid/7"},{"des":"2017-12-12","img":"http://www.qizhibq.com/Uploads/attachment/20180110/3420f7d7f509ece544555a4dea9db43c.jpg","title":"乖宝启蒙：一看就会手印画大全A [3~6岁]（男孩篇）","url":"http://www.qizhibq.com/api/Article/info/id/27/uid/7"}]
     * info : 操作成功！
     * page : {"dataTotal":2,"page":"1","pageSize":10,"pageTotal":1}
     * recom : [{"img":"http://www.qizhibq.com/Uploads/attachment/20180110/824274394131c4d10b3c03303b1a1c03.jpg","title":"启蒙之后的的启蒙：启蒙世界观的内在逻辑与当代反思","url":"http://www.qizhibq.com/api/Article/info/id/26/uid/7"}]
     * status : 1
     */

    private String info;
    private PageBean page;
    private int status;
    private List<BannerBean> banner;
    private List<DataBean> data;
    private List<RecomBean> recom;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<RecomBean> getRecom() {
        return recom;
    }

    public void setRecom(List<RecomBean> recom) {
        this.recom = recom;
    }

    public static class PageBean {
        /**
         * dataTotal : 2
         * page : 1
         * pageSize : 10
         * pageTotal : 1
         */

        private int dataTotal;
        private String page;
        private int pageSize;
        private int pageTotal;

        public int getDataTotal() {
            return dataTotal;
        }

        public void setDataTotal(int dataTotal) {
            this.dataTotal = dataTotal;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
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

    public static class BannerBean {
        /**
         * code :
         * img : http://www.qizhibq.com
         * item_id : 0
         */

        private String code;
        private String img;
        private int item_id;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getItem_id() {
            return item_id;
        }

        public void setItem_id(int item_id) {
            this.item_id = item_id;
        }
    }

    public static class DataBean {
        /**
         * des : 2018-01-11
         * img : http://www.qizhibq.com
         * title : 完美素质家教启蒙全书：想象力启蒙书
         * url : http://www.qizhibq.com/api/Article/info/id/29/uid/7
         */

        private String des;
        private String img;
        private String title;
        private String url;

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class RecomBean {
        /**
         * img : http://www.qizhibq.com/Uploads/attachment/20180110/824274394131c4d10b3c03303b1a1c03.jpg
         * title : 启蒙之后的的启蒙：启蒙世界观的内在逻辑与当代反思
         * url : http://www.qizhibq.com/api/Article/info/id/26/uid/7
         */

        private String img;
        private String title;
        private String url;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
