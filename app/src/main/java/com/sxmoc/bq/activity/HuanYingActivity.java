package com.sxmoc.bq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.sxmoc.bq.R;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseNotLeftActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.model.IndexStartad;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.util.ACache;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;
import com.sxmoc.bq.util.VersionUtils;

import java.util.HashMap;

public class HuanYingActivity extends ZjbBaseNotLeftActivity {
    private static final int LOCATION = 1991;
    private String isFirst = "1";
    private long currentTimeMillis;
    private int GPS_REQUEST_CODE = 10;

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.INDEX_STARTAD;
        HashMap<String, String> params = new HashMap<>();
        params.put("isFirst", isFirst);
        params.put("deviceId", PushServiceFactory.getCloudPushService().getDeviceId());
        params.put("deviceToken", "");
        params.put("type", "android");
        params.put("version", VersionUtils.getCurrVersionName(this));
        params.put("intro", "model=" + android.os.Build.MODEL + "sdk=" + android.os.Build.VERSION.SDK);
        params.put("mid", "");
        return new OkObject(params, url);
    }

    private void getData() {
        ApiClient.post(HuanYingActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("HuanYingActivity--onSuccess", "");
                try {
                    final IndexStartad indexStartad = GsonUtils.parseJSON(s, IndexStartad.class);
                    if ((System.currentTimeMillis() - currentTimeMillis) < 1000) {

                        if (indexStartad.getStatus() == 1) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                        go(indexStartad);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        } else {
                            MyDialog.dialogFinish(HuanYingActivity.this, indexStartad.getInfo());
                        }
                    } else {
                        go(indexStartad);
                    }
                } catch (Exception e) {
                    MyDialog.dialogFinish(HuanYingActivity.this, "数据出错");
                }

            }

            private void go(IndexStartad indexStartad) {
                if (TextUtils.equals(isFirst, "1")) {
                    Intent intent = new Intent(HuanYingActivity.this, YinDaoActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    toMainActivity();
                }
                ACache aCache = ACache.get(HuanYingActivity.this, Constant.Acache.LOCATION);
                aCache.put(Constant.Acache.DID, indexStartad.getDid() + "");
            }

            @Override
            public void onError() {
                try {
                    MyDialog.dialogFinish(HuanYingActivity.this, "请求失败");
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huan_ying);
        init();
    }


    @Override
    protected void initSP() {
        ACache aCache = ACache.get(HuanYingActivity.this, Constant.Acache.FRIST);
        String frist = aCache.getAsString(Constant.Acache.FRIST);
        if (frist != null) {
            isFirst = frist;
        }
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void findID() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initData() {
        currentTimeMillis = System.currentTimeMillis();
        getData();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void toMainActivity() {
        Intent intent = new Intent();
        if (isLogin) {
            intent.setClass(HuanYingActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            intent.setClass(HuanYingActivity.this, DengLuActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
