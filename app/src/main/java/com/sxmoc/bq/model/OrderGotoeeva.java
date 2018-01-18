package com.sxmoc.bq.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/1/18/018.
 *
 * @author ZhangJieBo
 */

public class OrderGotoeeva {
    /**
     * flag : [{"fid":1,"title":"很先进"},{"fid":2,"title":"分析很细致"},{"fid":3,"title":"很牛牛牛牛"},{"fid":4,"title":"回头客"}]
     * goods : [{"amount":49,"goods_type":2,"id":2,"img":"http://www.qizhibq.com/Uploads/attachment/20180110/73158bff8c0e7e06ba8e9ee78d9313a5.jpg","num":1,"price":"49.00","title":"BQ测评报告","unit":"份"}]
     * info : 获取成功
     * status : 1
     */

    private String info;
    private int status;
    private List<FlagBean> flag;
    private List<GoodsBean> goods;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<FlagBean> getFlag() {
        return flag;
    }

    public void setFlag(List<FlagBean> flag) {
        this.flag = flag;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class FlagBean {
        /**
         * fid : 1
         * title : 很先进
         */

        private int fid;
        private String title;

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class GoodsBean {
        /**
         * amount : 49
         * goods_type : 2
         * id : 2
         * img : http://www.qizhibq.com/Uploads/attachment/20180110/73158bff8c0e7e06ba8e9ee78d9313a5.jpg
         * num : 1
         * price : 49.00
         * title : BQ测评报告
         * unit : 份
         */

        private int amount;
        private int goods_type;
        private int id;
        private String img;
        private int num;
        private String price;
        private String title;
        private String unit;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
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
