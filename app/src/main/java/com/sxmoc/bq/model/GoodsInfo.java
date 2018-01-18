package com.sxmoc.bq.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/1/10/010.
 *
 * @author ZhangJieBo
 */

public class GoodsInfo {
    /**
     * desList : [{"name":"产品说明","value":"大脑雷达智力测试仪产品说明"},{"name":"检测公司","value":"BBQ检测公司"},{"name":"产品小贴士","value":"产品小贴士"}]
     * banner : [{"img":"http://www.qizhibq.com/Uploads/attachment/20180110/bc281fa179d440f1387a4227557015dd.jpg"},{"img":"http://www.qizhibq.com/Uploads/attachment/20180115/b4cd7cac6d548e632bc3beb39ec2588a.jpg"}]
     * data : {"id":1,"title":"大脑雷达智力测试仪","price":"1470.00","unit":"台","priceDes":"活动价"}
     * is_vip : 0
     * flag : ["很先进3","分析很细致3","很牛牛牛牛3","回头客1"]
     * evaluate : [{"name":"benzxx","content":"好的 不错哦","headimg":"http://www.qizhibq.com/Uploads/avstar.png","create_time":"2018.01.17"},{"name":"benzxx","content":"好的 不错哦 okok a","headimg":"http://www.qizhibq.com/Uploads/avstar.png","create_time":"2018.01.17"},{"name":"benzxx","content":"好的 不错哦 okok a","headimg":"http://www.qizhibq.com/Uploads/avstar.png","create_time":"2018.01.17"},{"name":"benzxx","content":"好的 不错哦 okok a","headimg":"http://www.qizhibq.com/Uploads/avstar.png","create_time":"2018.01.17"},{"name":"benzxx","content":"好的 不错哦 okok a","headimg":"http://www.qizhibq.com/Uploads/avstar.png","create_time":"2018.01.17"}]
     * status : 1
     * info : 返回成功！
     */

    private DataBean data;
    private int is_vip;
    private int status;
    private int count;
    private String info;
    private String tips;
    private List<DesListBean> desList;
    private List<BannerBean> banner;
    private List<String> flag;
    private List<EvaluateBean> evaluate;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
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

    public List<DesListBean> getDesList() {
        return desList;
    }

    public void setDesList(List<DesListBean> desList) {
        this.desList = desList;
    }

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<String> getFlag() {
        return flag;
    }

    public void setFlag(List<String> flag) {
        this.flag = flag;
    }

    public List<EvaluateBean> getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(List<EvaluateBean> evaluate) {
        this.evaluate = evaluate;
    }

    public static class DataBean {
        /**
         * id : 1
         * title : 大脑雷达智力测试仪
         * price : 1470.00
         * unit : 台
         * priceDes : 活动价
         */

        private int id;
        private String title;
        private String price;
        private String unit;
        private String priceDes;

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

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getPriceDes() {
            return priceDes;
        }

        public void setPriceDes(String priceDes) {
            this.priceDes = priceDes;
        }
    }

    public static class DesListBean {
        /**
         * name : 产品说明
         * value : 大脑雷达智力测试仪产品说明
         */

        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class BannerBean {
        /**
         * img : http://www.qizhibq.com/Uploads/attachment/20180110/bc281fa179d440f1387a4227557015dd.jpg
         */

        private String img;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }

}
