package com.sxmoc.bq.model;

/**
 * Created by zhangjiebo on 2018/1/17/017.
 *
 * @author ZhangJieBo
 */

public class TesterGetreport {
    /**
     * status : 1
     * info : 成功
     * data_url : http://api.qizhibq.com/Uploads/report_data/20180117/1011516155951/b84605be-fb2d-11e7-851f-00163e0c922b.pdf
     */

    private int status;
    private String info;
    private String data_url;

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

    public String getData_url() {
        return data_url;
    }

    public void setData_url(String data_url) {
        this.data_url = data_url;
    }
}
