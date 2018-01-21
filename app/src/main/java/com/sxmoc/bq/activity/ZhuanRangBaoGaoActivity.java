package com.sxmoc.bq.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sxmoc.bq.R;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.customview.SingleBtnDialog;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.SimpleInfo;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;

import java.util.HashMap;

public class ZhuanRangBaoGaoActivity extends ZjbBaseActivity implements View.OnClickListener {

    private TextView textPhone;
    private String phone;
    private EditText editToPhone;
    private EditText editNum;
    private TextView textViewRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuan_rang_bao_gao);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        phone = intent.getStringExtra(Constant.IntentKey.PHONE);
    }

    @Override
    protected void findID() {
        textPhone = (TextView) findViewById(R.id.textPhone);
        editToPhone = (EditText) findViewById(R.id.editToPhone);
        editNum = (EditText) findViewById(R.id.editNum);
        textViewRight = (TextView) findViewById(R.id.textViewRight);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("转让报告");
        textViewRight.setText("转让记录");
        textPhone.setText(phone);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.btnZhuanRang).setOnClickListener(this);
        textViewRight.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewRight:
                Intent intent = new Intent();
                intent.setClass(this,ZhuanRangLLActivity.class);
                startActivity(intent);
                break;
            case R.id.btnZhuanRang:
                if (TextUtils.isEmpty(editToPhone.getText().toString().trim())) {
                    Toast.makeText(ZhuanRangBaoGaoActivity.this, "请输入要转入的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editNum.getText().toString().trim())){
                    Toast.makeText(ZhuanRangBaoGaoActivity.this, "请输入要转让的报告数量", Toast.LENGTH_SHORT).show();
                    return;
                }
                zhuanRang();
                break;
            case R.id.imageBack:
                finish();
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
        String url = Constant.HOST + Constant.Url.USER_TRANSFER;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime",tokenTime);
        }
        params.put("d_phone",editToPhone.getText().toString().trim());
        params.put("num",editNum.getText().toString().trim());
        return new OkObject(params, url);
    }

    /**
     * 转让
     */
    private void zhuanRang() {
        showLoadingDialog();
        ApiClient.post(ZhuanRangBaoGaoActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("ZhuanRangBaoGaoActivity--onSuccess",s+ "");
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    if (simpleInfo.getStatus()==1){
                        MyDialog.showTipDialog(ZhuanRangBaoGaoActivity.this,simpleInfo.getInfo());
                        final SingleBtnDialog singleBtnDialog = new SingleBtnDialog(ZhuanRangBaoGaoActivity.this, simpleInfo.getInfo(), "确认");
                        singleBtnDialog.setClicklistener(new SingleBtnDialog.ClickListenerInterface() {
                            @Override
                            public void doWhat() {
                                singleBtnDialog.dismiss();
                                finish();
                                Intent intent = new Intent();
                                intent.setClass(ZhuanRangBaoGaoActivity.this,ZhuanRangLLActivity.class);
                                startActivity(intent);
                            }
                        });
                        singleBtnDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                                    dialog.dismiss();
                                    finish();
                                }
                                return false;
                            }
                        });
                        singleBtnDialog.show();
                    }else if (simpleInfo.getStatus()==3){
                        MyDialog.showReLoginDialog(ZhuanRangBaoGaoActivity.this);
                    }else {
                        Toast.makeText(ZhuanRangBaoGaoActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(ZhuanRangBaoGaoActivity.this,"数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(ZhuanRangBaoGaoActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
