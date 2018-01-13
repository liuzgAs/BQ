package com.sxmoc.bq.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/1/13/013.
 *
 * @author ZhangJieBo
 */

public class UserMsg {
    /**
     * data : [{"title":"用户升级成功","id":2,"des":null,"create_time":1515719920},{"title":"1111","id":1,"des":"1111111111111111","create_time":1515378990}]
     * page : {"page":"1","pageTotal":1,"pageSize":10,"dataTotal":2}
     * msgType : [{"id":1,"name":"系统消息","act":1},{"id":2,"name":"钱包消息","act":0}]
     * status : 1
     * info : 操作成功！
     */

    private PageBean page;
    private int status;
    private String info;
    private List<DataBean> data;
    private List<MsgTypeBean> msgType;

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

    public List<MsgTypeBean> getMsgType() {
        return msgType;
    }

    public void setMsgType(List<MsgTypeBean> msgType) {
        this.msgType = msgType;
    }

    public static class PageBean {
        /**
         * page : 1
         * pageTotal : 1
         * pageSize : 10
         * dataTotal : 2
         */

        private String page;
        private int pageTotal;
        private int pageSize;
        private int dataTotal;

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
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
         * title : 用户升级成功
         * id : 2
         * des : null
         * create_time : 1515719920
         */

        private String title;
        private int id;
        private String des;
        private int create_time;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }
    }

    public static class MsgTypeBean {
        /**
         * id : 1
         * name : 系统消息
         * act : 1
         */

        private int id;
        private String name;
        private int act;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAct() {
            return act;
        }

        public void setAct(int act) {
            this.act = act;
        }
    }
}
