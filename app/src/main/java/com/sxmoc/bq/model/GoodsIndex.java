package com.sxmoc.bq.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/1/10/010.
 *
 * @author ZhangJieBo
 */

public class GoodsIndex {
    /**
     * data : [{"title":"Q友套餐","price":"1470.00","cover":"http://bq.sxmoc.com/Uploads/attachment/20180106/e6ebeb5e124cb9e4866e7eb196aef129.png","id":3},{"title":"BQ测评报告","price":"49.00","cover":"http://bq.sxmoc.com/Uploads/attachment/20180106/e6ebeb5e124cb9e4866e7eb196aef129.png","id":2},{"title":"大脑雷达智力测试仪","price":"1470.00","cover":"http://bq.sxmoc.com/Uploads/attachment/20180106/e6ebeb5e124cb9e4866e7eb196aef129.png","id":1}]
     * status : 1
     * info : 返回成功！
     */

    private int status;
    private String info;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : Q友套餐
         * price : 1470.00
         * cover : http://bq.sxmoc.com/Uploads/attachment/20180106/e6ebeb5e124cb9e4866e7eb196aef129.png
         * id : 3
         */

        private String title;
        private String price;
        private String cover;
        private String unit;
        private int id;

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
