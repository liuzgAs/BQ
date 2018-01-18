package com.sxmoc.bq.model;

import java.util.List;

/**
 * Created by zhangjiebo on 2018/1/18/018.
 *
 * @author ZhangJieBo
 */

public class PingJiaTiJiao {
    private String smsKey;
    private int loginType;
    private String platform;
    private String uid;
    private String tokenTime;
    private int oid;
    private int goods_id;
    private String content;
    private int star;
    private int imgId;
    private List<Integer> flag;

    public PingJiaTiJiao(String smsKey, int loginType, String platform, String uid, String tokenTime, int oid, int goods_id, String content, int star, int imgId, List<Integer> flag) {
        this.smsKey = smsKey;
        this.loginType = loginType;
        this.platform = platform;
        this.uid = uid;
        this.tokenTime = tokenTime;
        this.oid = oid;
        this.goods_id = goods_id;
        this.content = content;
        this.star = star;
        this.imgId = imgId;
        this.flag = flag;
    }
}
