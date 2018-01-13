package com.sxmoc.bq.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/1/13/013.
 *
 * @author ZhangJieBo
 */

public class UserProfitdetailed {

    /**
     * status : 1
     * info : 获取成功
     * page : {"page":1,"pageTotal":2,"pageSize":15,"dataTotal":29}
     * data : [{"id":153,"title":"老二的订单商品返佣20%","money":"0.02","create_time":"2018.01.12"},{"id":149,"title":"老二的订单商品返佣20%","money":"0.02","create_time":"2018.01.12"},{"id":145,"title":"老二的订单商品返佣20%","money":"0.02","create_time":"2018.01.12"},{"id":141,"title":"老二的订单商品返佣20%","money":"0.02","create_time":"2018.01.12"},{"id":123,"title":"余额支付成功","money":"-0.10","create_time":"2018.01.12"},{"id":116,"title":"老二的订单商品返佣20%","money":"0.02","create_time":"2018.01.12"},{"id":109,"title":"老二的订单商品返佣20%","money":"0.20","create_time":"2018.01.12"},{"id":105,"title":"老二的订单商品返佣20%","money":"0.20","create_time":"2018.01.12"},{"id":102,"title":"老二的订单商品返佣20%","money":"0.20","create_time":"2018.01.12"},{"id":99,"title":"老二的订单商品返佣20%","money":"0.10","create_time":"2018.01.12"},{"id":95,"title":"老二的订单商品返佣20%","money":"0.02","create_time":"2018.01.12"},{"id":82,"title":"老二的订单商品返佣20%","money":"0.04","create_time":"2018.01.12"},{"id":75,"title":"老二订单商品返佣20%","money":"0.00","create_time":"2018.01.12"},{"id":73,"title":"测试","money":"10.00","create_time":"2018.01.12"},{"id":43,"title":"benzxx订单商品返佣20%","money":"0.00","create_time":"2018.01.11"}]
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
         * dataTotal : 29
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
         * id : 153
         * title : 老二的订单商品返佣20%
         * money : 0.02
         * create_time : 2018.01.12
         */

        private int id;
        private String title;
        private String money;
        private String create_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
