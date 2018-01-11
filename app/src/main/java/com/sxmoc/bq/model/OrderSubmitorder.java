package com.sxmoc.bq.model;

/**
 * Created by zhangjiebo on 2018/1/11/011.
 *
 * @author ZhangJieBo
 */

public class OrderSubmitorder {
    /**
     * status : 1
     * info : 返回成功！
     * order_no : 201801081854541
     */

    private int status;
    private String info;
    private String order_no;

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

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }
}
