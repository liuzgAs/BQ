package com.sxmoc.bq.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/1/16/016.
 *
 * @author ZhangJieBo
 */

public class OrderGetorderdetail {
    /**
     * address : 福建省-厦门市-思明区观音山商务区6号
     * amount : 1470
     * consignee : benzx
     * create_time : 2018.01.10
     * goods_info : [{"amount":1470,"id":1,"img":"http://bq.sxmoc.com/Uploads/attachment/20180110/bc281fa179d440f1387a4227557015dd.jpg","num":1,"price":"1470.00","title":"大脑雷达智力测试仪","unit":"台"}]
     * info : 获取成功
     * logistics_name :
     * logistics_url :
     * order_no : 2018011014490955
     * order_status : 2
     * pay_type : 余额支付
     * phone : 18250151297
     * status : 1
     */

    private String address;
    private double amount;
    private String consignee;
    private String create_time;
    private String info;
    private String logistics_name;
    private String logistics_url;
    private String order_no;
    private int order_status;
    private String pay_type;
    private String phone;
    private int status;
    private List<GoodsInfoBean> goods_info;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<GoodsInfoBean> getGoods_info() {
        return goods_info;
    }

    public void setGoods_info(List<GoodsInfoBean> goods_info) {
        this.goods_info = goods_info;
    }

    public static class GoodsInfoBean {
        /**
         * amount : 1470
         * id : 1
         * img : http://bq.sxmoc.com/Uploads/attachment/20180110/bc281fa179d440f1387a4227557015dd.jpg
         * num : 1
         * price : 1470.00
         * title : 大脑雷达智力测试仪
         * unit : 台
         */

        private double amount;
        private int id;
        private String img;
        private int num;
        private String price;
        private String title;
        private String unit;
        private int goods_type;

        public int getGoods_type() {
            return goods_type;
        }

        public void setGoods_type(int goods_type) {
            this.goods_type = goods_type;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
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

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}
