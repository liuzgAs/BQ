package com.sxmoc.bq.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/1/17/017.
 *
 * @author ZhangJieBo
 */

public class OrderGeteeva {

    /**
     * status : 1
     * info : 获取成功
     * data : [{"id":181,"oid":181,"name":"","content":"","img":"","star":"","create_time":"2018-01-12","goods":[{"id":1,"title":"大脑雷达智力测试仪","price":"0.01","num":10,"unit":"台","amount":0.1,"goods_type":1,"img":"http://www.qizhibq.com/Uploads/attachment/20180110/bc281fa179d440f1387a4227557015dd.jpg"}]},{"id":180,"oid":180,"name":"","content":"","img":"","star":"","create_time":"2018-01-12","goods":[{"id":1,"title":"大脑雷达智力测试仪","price":"0.01","num":10,"unit":"台","amount":0.1,"goods_type":1,"img":"http://www.qizhibq.com/Uploads/attachment/20180110/bc281fa179d440f1387a4227557015dd.jpg"}]}]
     * flag : [{"fid":1,"title":"很先进"},{"fid":2,"title":"分析很细致"},{"fid":3,"title":"很牛牛牛牛"},{"fid":4,"title":"回头客"}]
     */

    private int status;
    private String info;
    private List<DataBean> data;
    private List<FlagBean> flag;

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

    public List<FlagBean> getFlag() {
        return flag;
    }

    public void setFlag(List<FlagBean> flag) {
        this.flag = flag;
    }

    public static class DataBean {
        /**
         * id : 181
         * oid : 181
         * name :
         * content :
         * img :
         * star :
         * create_time : 2018-01-12
         * goods : [{"id":1,"title":"大脑雷达智力测试仪","price":"0.01","num":10,"unit":"台","amount":0.1,"goods_type":1,"img":"http://www.qizhibq.com/Uploads/attachment/20180110/bc281fa179d440f1387a4227557015dd.jpg"}]
         */

        private int id;
        private int oid;
        private String name;
        private String content;
        private String img;
        private String headimg;
        private float star;
        private String create_time;
        private List<GoodsBean> goods;

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOid() {
            return oid;
        }

        public void setOid(int oid) {
            this.oid = oid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public float getStar() {
            return star;
        }

        public void setStar(float star) {
            this.star = star;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * id : 1
             * title : 大脑雷达智力测试仪
             * price : 0.01
             * num : 10
             * unit : 台
             * amount : 0.1
             * goods_type : 1
             * img : http://www.qizhibq.com/Uploads/attachment/20180110/bc281fa179d440f1387a4227557015dd.jpg
             */

            private int id;
            private String title;
            private String price;
            private int num;
            private String unit;
            private double amount;
            private int goods_type;
            private String img;

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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public int getGoods_type() {
                return goods_type;
            }

            public void setGoods_type(int goods_type) {
                this.goods_type = goods_type;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
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
}
