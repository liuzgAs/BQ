package com.sxmoc.bq.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2017/12/13 0013.
 *
 * @author ZhangJieBo
 */

public class Faq {
    /**
     * data : [{"intro":"常见问题常见问题常见问题常见问题常见问题常见问题常见问题","title":"常见问题常见问题"},{"intro":"常见问题常见问题常见问题常见问题常见问题常见问题常见问题","title":"常见问题常见问题"},{"intro":"常见问题常见问题常见问题常见问题常见问题常见问题常见问题","title":"常见问题常见问题"},{"intro":"常见问题常见问题常见问题常见问题常见问题常见问题常见问题","title":"常见问题常见问题"},{"intro":"常见问题常见问题常见问题常见问题常见问题常见问题常见问题","title":"常见问题常见问题"}]
     * info : 返回成功！
     * page : {"dataTotal":5,"page":"1","pageSize":10,"pageTotal":1}
     * status : 1
     */

    private String info;
    private PageBean page;
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
         * dataTotal : 5
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

    public static class DataBean {
        /**
         * intro : 常见问题常见问题常见问题常见问题常见问题常见问题常见问题
         * title : 常见问题常见问题
         */

        private String intro;
        private String title;

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
