package com.sxmoc.bq.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/1/17/017.
 *
 * @author ZhangJieBo
 */

public class ProductQueryhistory {

    /**
     * status : 1
     * info : 成功
     * data : [{"id":37,"uid":7,"status":1,"bid":1,"create_time":"2018.01.16","name":"宝宝"},{"id":38,"uid":7,"status":1,"bid":1,"create_time":"2018.01.16","name":"宝宝"},{"id":39,"uid":7,"status":1,"bid":1,"create_time":"2018.01.16","name":"宝宝"},{"id":43,"uid":7,"status":1,"bid":1,"create_time":"2018.01.16","name":"宝宝"},{"id":44,"uid":7,"status":1,"bid":173,"create_time":"2018.01.16","name":"宝宝"},{"id":45,"uid":7,"status":1,"bid":174,"create_time":"2018.01.16","name":"宝宝"},{"id":46,"uid":7,"status":1,"bid":175,"create_time":"2018.01.16","name":"阿宝"},{"id":47,"uid":7,"status":1,"bid":176,"create_time":"2018.01.16","name":"阿宝"},{"id":48,"uid":7,"status":1,"bid":179,"create_time":"2018.01.16","name":"班"},{"id":50,"uid":7,"status":1,"bid":181,"create_time":"2018.01.16","name":"阿爸"},{"id":51,"uid":7,"status":1,"bid":182,"create_time":"2018.01.16","name":"阿宝"},{"id":52,"uid":7,"status":1,"bid":183,"create_time":"2018.01.16","name":"看见了"},{"id":54,"uid":7,"status":1,"bid":186,"create_time":"2018.01.16","name":"看见了"},{"id":55,"uid":7,"status":1,"bid":187,"create_time":"2018.01.16","name":"接口"},{"id":56,"uid":7,"status":1,"bid":189,"create_time":"2018.01.16","name":"Mon"},{"id":57,"uid":7,"status":1,"bid":191,"create_time":"2018.01.16","name":"搬家"},{"id":60,"uid":7,"status":1,"bid":197,"create_time":"2018.01.17","name":"阿爸"}]
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
         * id : 37
         * uid : 7
         * status : 1
         * bid : 1
         * create_time : 2018.01.16
         * name : 宝宝
         */

        private int id;
        private int uid;
        private int status;
        private int bid;
        private String create_time;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getBid() {
            return bid;
        }

        public void setBid(int bid) {
            this.bid = bid;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
