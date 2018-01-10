package com.sxmoc.bq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sxmoc.bq.R;
import com.sxmoc.bq.base.ZjbBaseActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.SimpleInfo;
import com.sxmoc.bq.model.UserInfo;
import com.sxmoc.bq.util.ACache;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;
import com.sxmoc.bq.util.MD5Util;
import com.sxmoc.bq.util.StringUtil;

import java.util.HashMap;


public class DengLuActivity extends ZjbBaseActivity implements View.OnClickListener {

    private EditText[] editView = new EditText[2];
    private View[] lineView = new View[2];
    private String did;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deng_lu);
        init();
    }

    @Override
    protected void initSP() {
        ACache aCache = ACache.get(this, Constant.Acache.LOCATION);
        did = aCache.getAsString(Constant.Acache.DID);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        String phone = intent.getStringExtra(Constant.IntentKey.PHONE);
        editView[0].setText(phone);
        editView[1].requestFocus();
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void findID() {
        editView[0] = (EditText) findViewById(R.id.edit01);
        editView[1] = (EditText) findViewById(R.id.edit02);
        lineView[0] = findViewById(R.id.line01);
        lineView[1] = findViewById(R.id.line02);
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.buttonLogin).setOnClickListener(this);
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.buttonZhuCe).setOnClickListener(this);
        findViewById(R.id.textWangJiMM).setOnClickListener(this);
        for (int i = 0; i < editView.length; i++) {
            final int finalI = i;
            editView[i].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        for (View aLineView : lineView) {
                            aLineView.setBackgroundColor(ContextCompat.getColor(DengLuActivity.this, R.color.text_gray));
                        }
                        lineView[finalI].setBackgroundColor(ContextCompat.getColor(DengLuActivity.this, R.color.basic_color));
                    }
                }
            });
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.textWangJiMM:
                intent.setClass(this, WangJiMMActivity.class);
                startActivity(intent);
                break;
            case R.id.imageBack:
                finish();
                break;
            case R.id.buttonLogin:

                if (!StringUtil.isMobileNO(editView[0].getText().toString().trim())) {
                    Toast.makeText(DengLuActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editView[1].getText().toString().trim())) {
                    Toast.makeText(DengLuActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginPsw();
                break;
            case R.id.buttonZhuCe:
                intent.setClass(this, ZhuCeActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.LOGIN_FORGETSMS;
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", editView[1].getText().toString().trim());
        return new OkObject(params, url);
    }

    /**
     * des： 获取短信
     * author： ZhangJieBo
     * date： 2017/7/6 0006 下午 2:45
     */
    private void getSms() {
        showLoadingDialog();
        ApiClient.post(this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("ZhuCeActivity--获取短信", "" + s);
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    Toast.makeText(DengLuActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    if (simpleInfo.getStatus() == 1) {

                    }
                } catch (Exception e) {
                    Toast.makeText(DengLuActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(DengLuActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObjectPsw(String tokenTime) {
        String url = Constant.HOST + Constant.Url.LOGIN_INDEX;
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", editView[0].getText().toString().trim());
        params.put("tokenTime", tokenTime);
        params.put("userPwd", MD5Util.getMD5(MD5Util.getMD5(editView[1].getText().toString().trim()) + "ad"));
        params.put("did", did);
        return new OkObject(params, url);
    }

    /**
     * 密码登录
     */
    private void loginPsw() {
        showLoadingDialog();
        final String tokenTime = System.currentTimeMillis() + "";
        ApiClient.post(DengLuActivity.this, getOkObjectPsw(tokenTime), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("DengLuActivity--登录返回", "" + s);
                cancelLoadingDialog();
                try {
                    loginSuccess(s, tokenTime);
                } catch (Exception e) {
                    Toast.makeText(DengLuActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(DengLuActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * des： 登录成功处理
     * author： ZhangJieBo
     * date： 2017/9/8 0008 下午 5:27
     */
    private void loginSuccess(String s, String tokenTime) {
        final UserInfo userInfo = GsonUtils.parseJSON(s, UserInfo.class);
        if (userInfo.getStatus() == 1) {
            ACache aCache = ACache.get(DengLuActivity.this, Constant.Acache.APP);
            aCache.put(Constant.Acache.USER_INFO, userInfo);
            aCache.put(Constant.Acache.TOKENTIME, tokenTime);
            Constant.changeControl++;
            Intent intent = new Intent();
            intent.setClass(DengLuActivity.this, MainActivity.class);
            startActivity(intent);
        }
        Toast.makeText(DengLuActivity.this, userInfo.getInfo(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
