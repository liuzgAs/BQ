package com.sxmoc.bq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxmoc.bq.R;
import com.sxmoc.bq.base.ZjbBaseActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.SimpleInfo;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;
import com.sxmoc.bq.util.MD5Util;
import com.sxmoc.bq.util.StringUtil;

import java.util.HashMap;


/**
 * 注册
 * @author Administrator
 */
public class ZhuCeActivity extends ZjbBaseActivity implements View.OnClickListener {
    private EditText[] editView = new EditText[4];
    private View[] lineView = new View[4];
    private TextView textSms;
    private Runnable mR;
    private int[] mI;
    private String mPhone_sms;
    private boolean isAgreement = true;
    private ImageView imageAgreement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_ce);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void findID() {
        editView[0] = (EditText) findViewById(R.id.edit01);
        editView[1] = (EditText) findViewById(R.id.edit02);
        editView[2] = (EditText) findViewById(R.id.edit03);
        editView[3] = (EditText) findViewById(R.id.edit04);
//        editView[4] = (EditText) findViewById(R.id.edit05);
        lineView[0] = findViewById(R.id.line01);
        lineView[1] = findViewById(R.id.line02);
        lineView[2] = findViewById(R.id.line03);
        lineView[3] = findViewById(R.id.line04);
//        lineView[4] = findViewById(R.id.line05);
        textSms = (TextView) findViewById(R.id.textSms);
        imageAgreement = (ImageView) findViewById(R.id.imageAgreement);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setListeners() {
        findViewById(R.id.buttonZhuCe).setOnClickListener(this);
        findViewById(R.id.imageBack).setOnClickListener(this);
        /*下划线监听*/
        for (int i = 0; i < editView.length; i++) {
            final int finalI = i;
            editView[i].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        for (View aLineView : lineView) {
                            aLineView.setBackgroundColor(ContextCompat.getColor(ZhuCeActivity.this,R.color.text_gray));
                        }
                        lineView[finalI].setBackgroundColor(ContextCompat.getColor(ZhuCeActivity.this,R.color.basic_color));
                    }
                }
            });
        }
        textSms.setOnClickListener(this);
        imageAgreement.setOnClickListener(this);
        findViewById(R.id.textXieYi).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    /**
     * des： 控制协议选中
     * author： ZhangJieBo
     * date： 2017/11/14 0014 上午 10:17
     */
    private void setAgreement() {
        if (isAgreement) {
            imageAgreement.setImageResource(R.mipmap.xuanzhong);
        } else {
            imageAgreement.setImageResource(R.mipmap.weixuanzhong);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textXieYi:
                Intent intent = new Intent();
                intent.setClass(this,WebActivity.class);
                intent.putExtra(Constant.IntentKey.TITLE,"用户协议");
                intent.putExtra(Constant.IntentKey.URL,Constant.Url.USER);
                startActivity(intent);
                break;
            case R.id.imageAgreement:
                isAgreement = !isAgreement;
                setAgreement();
                break;
            case R.id.textSms:
                sendSMS();
                break;
            case R.id.buttonZhuCe:
                if (TextUtils.isEmpty(mPhone_sms)) {
                    Toast.makeText(ZhuCeActivity.this, "请先发送验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editView[1].getText().toString().trim())) {
                    Toast.makeText(ZhuCeActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editView[2].getText().toString().trim()) || TextUtils.isEmpty(editView[3].getText().toString().trim())) {
                    Toast.makeText(ZhuCeActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.equals(editView[2].getText().toString().trim(), editView[3].getText().toString().trim())) {
                    Toast.makeText(ZhuCeActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editView[2].getText().toString().trim().length()<=6) {
                    Toast.makeText(ZhuCeActivity.this, "密码必须大于6位", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isAgreement) {
                    Toast.makeText(ZhuCeActivity.this, R.string.xieyi, Toast.LENGTH_SHORT).show();
                    return;
                }
                zuCe();
                break;
            case R.id.imageBack:
                finish();
                break;
            default:
                break;
        }
    }

    private OkObject getOkObject1() {
        String url = Constant.HOST + Constant.Url.LOGIN_REGISTER;
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", mPhone_sms);
        params.put("userPwd", MD5Util.getMD5(MD5Util.getMD5(editView[2].getText().toString().trim()) + "ad"));
        params.put("code", editView[1].getText().toString().trim());
//        params.put("tx_mobile", editView[4].getText().toString().trim());
        /*用户类型卖家注册时传1买家注册传0*/
        params.put("type", "0");
        return new OkObject(params, url);
    }

    /**
     * des： 注册
     * author： ZhangJieBo
     * date： 2017/8/22 0022 上午 10:48
     */
    private void zuCe() {
        showLoadingDialog();
        ApiClient.post(this, getOkObject1(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("ZhuCeActivity--onSuccess", "");
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    Toast.makeText(ZhuCeActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    if (simpleInfo.getStatus() == 1) {
                        Intent intent = new Intent();
                        intent.setClass(ZhuCeActivity.this, DengLuActivity.class);
                        intent.putExtra(Constant.IntentKey.PHONE, mPhone_sms);
                        startActivity(intent);
                    } else {

                    }
                } catch (Exception e) {
                    Toast.makeText(ZhuCeActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(ZhuCeActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * des： 短信发送按钮状态
     * author： ZhangJieBo
     * date： 2017/8/22 0022 上午 10:26
     */
    private void sendSMS() {
        textSms.removeCallbacks(mR);
        boolean mobileNO = StringUtil.isMobileNO(editView[0].getText().toString().trim());
        if (mobileNO) {
            mPhone_sms = editView[0].getText().toString().trim();
            textSms.setEnabled(false);
            mI = new int[]{60};

            mR = new Runnable() {
                @Override
                public void run() {
                    textSms.setText((mI[0]--) + "秒后重发");
                    if (mI[0] == 0) {
                        textSms.setEnabled(true);
                        textSms.setText("重新发送");
                        return;
                    } else {
                    }
                    textSms.postDelayed(mR, 1000);
                }
            };
            textSms.postDelayed(mR, 0);
            getSms();
        } else {
            Toast.makeText(ZhuCeActivity.this, "输入正确的手机号", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.LOGIN_REGSMS;
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", mPhone_sms);
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
                    Toast.makeText(ZhuCeActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    if (simpleInfo.getStatus() == 1) {

                    }
                } catch (Exception e) {
                    Toast.makeText(ZhuCeActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(ZhuCeActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
