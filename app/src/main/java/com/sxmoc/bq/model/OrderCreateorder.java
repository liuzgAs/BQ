package com.sxmoc.bq.model;

/**
 * Created by zhangjiebo on 2018/1/10/010.
 *
 * @author ZhangJieBo
 */

public class OrderCreateorder {
    /**
     * status : 1
     * info : 获取成功
     * is_deddr : 1
     * consignee : asd
     * phone : 15860026755
     * address : 福建省-厦门市-思明区观音山商务区6号楼
     * goods_id : 1
     * goods_img : http://bq.sxmoc.com/Uploads/attachment/20180106/e6ebeb5e124cb9e4866e7eb196aef129.png
     * goods_title : 大脑雷达智力测试仪
     * goods_price : 1470.00
     * goods_stock : 1000
     * type_id : 1
     */

    private int status;
    private String info;
    private int is_deddr;
    private String consignee;
    private String phone;
    private String address;
    private int goods_id;
    private String goods_img;
    private String goods_title;
    private String goods_price;
    private int goods_stock;
    private int type_id;
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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

    public int getIs_deddr() {
        return is_deddr;
    }

    public void setIs_deddr(int is_deddr) {
        this.is_deddr = is_deddr;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getGoods_title() {
        return goods_title;
    }

    public void setGoods_title(String goods_title) {
        this.goods_title = goods_title;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public int getGoods_stock() {
        return goods_stock;
    }

    public void setGoods_stock(int goods_stock) {
        this.goods_stock = goods_stock;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }
}
