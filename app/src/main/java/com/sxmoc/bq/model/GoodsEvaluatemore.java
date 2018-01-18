package com.sxmoc.bq.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/1/18/018.
 *
 * @author ZhangJieBo
 */

public class GoodsEvaluatemore {
    /**
     * status : 1
     * info : 获取成功
     * page : {"page":1,"pageTotal":1,"pageSize":10,"dataTotal":10}
     * evaluate : [{"name":"benzxx","content":"好的 不错哦 okok a","headimg":"http://www.qizhibq.com/Uploads/avstar.png","create_time":"2018.01.17"},{"name":"benzxx","content":"好的 不错哦 okok a","headimg":"http://www.qizhibq.com/Uploads/avstar.png","create_time":"2018.01.17"},{"name":"benzxx","content":"好的 不错哦 okok a","headimg":"http://www.qizhibq.com/Uploads/avstar.png","create_time":"2018.01.17"},{"name":"benzxx","content":"好的 不错哦 okok a","headimg":"http://www.qizhibq.com/Uploads/avstar.png","create_time":"2018.01.17"},{"name":"benzxx","content":"好的 不错哦 okok a","headimg":"http://www.qizhibq.com/Uploads/avstar.png","create_time":"2018.01.17"},{"name":"benzxx","content":"好的 不错哦 okok a","headimg":"http://www.qizhibq.com/Uploads/avstar.png","create_time":"2018.01.17"},{"name":"benzxx","content":"好的 不错哦 okok a","headimg":"http://www.qizhibq.com/Uploads/avstar.png","create_time":"2018.01.17"},{"name":"benzxx","content":"好的 不错哦 okok a","headimg":"http://www.qizhibq.com/Uploads/avstar.png","create_time":"2018.01.17"},{"name":"benzxx","content":"好的 不错哦 okok a","headimg":"http://www.qizhibq.com/Uploads/avstar.png","create_time":"2018.01.17"},{"name":"benzxx","content":"好的 不错哦","headimg":"http://www.qizhibq.com/Uploads/avstar.png","create_time":"2018.01.17"}]
     * flag : ["很先进3","分析很细致3","很牛牛牛牛3","回头客1"]
     */

    private int status;
    private String info;
    private PageBean page;
    private List<EvaluateBean> evaluate;
    private List<String> flag;

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

    public List<EvaluateBean> getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(List<EvaluateBean> evaluate) {
        this.evaluate = evaluate;
    }

    public List<String> getFlag() {
        return flag;
    }

    public void setFlag(List<String> flag) {
        this.flag = flag;
    }

    public static class PageBean {
        /**
         * page : 1
         * pageTotal : 1
         * pageSize : 10
         * dataTotal : 10
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


}
