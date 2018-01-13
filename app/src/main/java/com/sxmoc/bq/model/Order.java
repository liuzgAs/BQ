package com.sxmoc.bq.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/1/13/013.
 *
 * @author ZhangJieBo
 */

public class Order {
    /**
     * data : [{"createtime":"2018-01-10 13:55","goods_type":1,"id":47,"logistics_name":"","logistics_url":"https://m.kuaidi100.com/index_all.html?type=&postid=","order_no":"2018011013550247","order_status":1,"products":[{"amount":4410,"id":3,"img":"http://www.qizhibq.com/Uploads/attachment/20180106/334f7c9ab830cc4ced9adf074ad11c7c.png","num":3,"price":"1470.00","title":"Q友套餐"}]},{"createtime":"2018-01-09 10:04","goods_type":4,"id":12,"logistics_name":"","logistics_url":"https://m.kuaidi100.com/index_all.html?type=&postid=","order_no":"2018010910040112","order_status":1,"products":[{"amount":10,"id":0,"img":"","num":1,"price":"10.00","title":"充值"}]},{"createtime":"2018-01-08 20:00","goods_type":1,"id":5,"logistics_name":"","logistics_url":"https://m.kuaidi100.com/index_all.html?type=&postid=","order_no":"201801082000525","order_status":1,"products":[{"amount":1470,"id":1,"img":"http://www.qizhibq.com/Uploads/attachment/20180110/bc281fa179d440f1387a4227557015dd.jpg","num":1,"price":"1470.00","title":"大脑雷达智力测试仪"}]}]
     * info : 返回成功！
     * page : {"dataTotal":3,"page":1,"pageSize":15,"pageTotal":1}
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
         * dataTotal : 3
         * page : 1
         * pageSize : 15
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
         * createtime : 2018-01-10 13:55
         * goods_type : 1
         * id : 47
         * logistics_name :
         * logistics_url : https://m.kuaidi100.com/index_all.html?type=&postid=
         * order_no : 2018011013550247
         * order_status : 1
         * products : [{"amount":4410,"id":3,"img":"http://www.qizhibq.com/Uploads/attachment/20180106/334f7c9ab830cc4ced9adf074ad11c7c.png","num":3,"price":"1470.00","title":"Q友套餐"}]
         */

        private String createtime;
        private int goods_type;
        private int id;
        private String logistics_name;
        private String logistics_url;
        private String order_no;
        private int order_status;
        private List<ProductsBean> products;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getGoods_type() {
            return goods_type;
        }

        public void setGoods_type(int goods_type) {
            this.goods_type = goods_type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogistics_name() {
            return logistics_name;
        }

        public void setLogistics_name(String logistics_name) {
            this.logistics_name = logistics_name;
        }

        public String getLogistics_url() {
            return logistics_url;
        }

        public void setLogistics_url(String logistics_url) {
            this.logistics_url = logistics_url;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public List<ProductsBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public static class ProductsBean {
            /**
             * amount : 4410
             * id : 3
             * img : http://www.qizhibq.com/Uploads/attachment/20180106/334f7c9ab830cc4ced9adf074ad11c7c.png
             * num : 3
             * price : 1470.00
             * title : Q友套餐
             */

            private String amount;
            private int id;
            private String img;
            private int num;
            private String price;
            private String title;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
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

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
