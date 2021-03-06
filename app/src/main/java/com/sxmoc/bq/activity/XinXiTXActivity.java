package com.sxmoc.bq.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sxmoc.bq.R;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.model.BuyerAddinfo;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.TesterTesteredit;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.DateTransforam;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;

public class XinXiTXActivity extends ZjbBaseActivity implements View.OnClickListener {

    private EditText editName;
    private TextView textSex;
    private int sex = -1;
    private String birthday;
    private TextView textBirthday;
    private EditText editSchool;
    private TextView textGrade;
    private EditText editEmiel;
    private CheckBox checkBox;
    private int id;
    String[] strings1 = {"幼儿园大班", "一年级", "二年级", "三年级", "四年级", "五年级", "六年级", "七年级", "八年级", "九年级"};
    final String[] strings = {"男", "女"};
    private Button btnKaiShiCeShi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xin_xi_tx);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        id = intent.getIntExtra(Constant.IntentKey.ID, 0);
    }

    @Override
    protected void findID() {
        editName = (EditText) findViewById(R.id.editName);
        textSex = (TextView) findViewById(R.id.textSex);
        textBirthday = (TextView) findViewById(R.id.textBirthday);
        editSchool = (EditText) findViewById(R.id.editSchool);
        textGrade = (TextView) findViewById(R.id.textGrade);
        editEmiel = (EditText) findViewById(R.id.editEmiel);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        btnKaiShiCeShi = (Button) findViewById(R.id.btnKaiShiCeShi);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("信息填写");
        if (id == 0) {
            btnKaiShiCeShi.setText("添加");
        } else {
            btnKaiShiCeShi.setText("确认修改");
        }
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.viewSex).setOnClickListener(this);
        findViewById(R.id.viewBirthday).setOnClickListener(this);
        findViewById(R.id.imageBack).setOnClickListener(this);
        btnKaiShiCeShi.setOnClickListener(this);
        findViewById(R.id.viewGrade).setOnClickListener(this);
        findViewById(R.id.textXieYi).setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getHuoQuOkObject() {
        String url = Constant.HOST + Constant.Url.TESTER_TESTEREDIT;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("bid", String.valueOf(id));
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        if (id != 0) {
            showLoadingDialog();
            ApiClient.post(XinXiTXActivity.this, getHuoQuOkObject(), new ApiClient.CallBack() {
                @Override
                public void onSuccess(String s) {
                    cancelLoadingDialog();
                    LogUtil.LogShitou("XinXiTXActivity--onSuccess", s + "");
                    try {
                        TesterTesteredit testerTesteredit = GsonUtils.parseJSON(s, TesterTesteredit.class);
                        if (testerTesteredit.getStatus() == 1) {
                            editName.setText(testerTesteredit.getData().getName());
                            editName.setSelection(testerTesteredit.getData().getName().length());
                            editSchool.setText(testerTesteredit.getData().getSchool_name());
                            editEmiel.setText(testerTesteredit.getData().getMailbox());
                            textGrade.setText(testerTesteredit.getData().getGrade() + "");
                            textBirthday.setText(testerTesteredit.getData().getBirthday());
                            textSex.setText(strings[testerTesteredit.getData().getSex()]);
                            sex = testerTesteredit.getData().getSex();
                        } else if (testerTesteredit.getStatus() == 3) {
                            MyDialog.showReLoginDialog(XinXiTXActivity.this);
                        } else {
                            Toast.makeText(XinXiTXActivity.this, testerTesteredit.getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(XinXiTXActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError() {
                    cancelLoadingDialog();
                    Toast.makeText(XinXiTXActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textXieYi:
                Intent intent = new Intent();
                intent.setClass(this, WebActivity.class);
                intent.putExtra(Constant.IntentKey.TITLE, "产品服务协议");
                intent.putExtra(Constant.IntentKey.URL, Constant.Url.PRODUCT);
                startActivity(intent);
                break;
            case R.id.viewSex:
                new AlertDialog.Builder(this)
                        .setItems(strings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                LogUtil.LogShitou("XinXiTXActivity--onClick", "" + i);
                                textSex.setText(strings[i]);
                                sex = i;
                            }
                        })
                        .show();
                break;
            case R.id.btnKaiShiCeShi:
                if (TextUtils.isEmpty(editName.getText().toString().trim())) {
                    Toast.makeText(XinXiTXActivity.this, "请输入宝宝姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sex == -1) {
                    Toast.makeText(XinXiTXActivity.this, "请选择宝宝性别", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(textBirthday.getText().toString().trim())) {
                    Toast.makeText(XinXiTXActivity.this, "请选择宝宝出生年月", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (TextUtils.isEmpty(editSchool.getText().toString().trim())) {
//                    Toast.makeText(XinXiTXActivity.this, "请输入宝宝校区", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(editEmiel.getText().toString().trim())) {
//                    Toast.makeText(XinXiTXActivity.this, "请填写邮箱地址", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if (TextUtils.isEmpty(textGrade.getText().toString().trim())) {
                    Toast.makeText(XinXiTXActivity.this, "请选择宝宝年级", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (!checkBox.isChecked()) {
//                    Toast.makeText(XinXiTXActivity.this, "请阅读并同意《产品服务协议》", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                tiJiaoXinXi();
                break;
            case R.id.viewBirthday:
                Calendar c = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        try {
                            birthday = DateTransforam.dateToStamp(year + "-" + (month + 1) + "-" + dayOfMonth);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        textBirthday.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
                break;
            case R.id.viewGrade:
                new AlertDialog.Builder(this)
                        .setItems(strings1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                LogUtil.LogShitou("XinXiTXActivity--onClick", "" + i);
                                textGrade.setText(strings1[i]);
                            }
                        })
                        .show();
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
        String url = Constant.HOST + Constant.Url.BUYER_ADDINFO;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("name", editName.getText().toString().trim());
        params.put("school_name", editSchool.getText().toString().trim());
        params.put("mailbox", editEmiel.getText().toString().trim());
        params.put("grade", textGrade.getText().toString().trim());
        params.put("birthday", birthday);
        params.put("sex", String.valueOf(sex));
        if (id != 0) {
            params.put("bid", String.valueOf(id));
        }
        return new OkObject(params, url);
    }

    /**
     * 提交宝宝信息
     */
    private void tiJiaoXinXi() {
        showLoadingDialog();
        ApiClient.post(XinXiTXActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("XinXiTXActivity--onSuccess", s + "");
                try {
                    BuyerAddinfo buyerAddinfo = GsonUtils.parseJSON(s, BuyerAddinfo.class);
                    if (buyerAddinfo.getStatus() == 1) {
//                        if (id==0){
//                            Intent intent1 = new Intent();
//                            intent1.setAction(Constant.BroadcastCode.KAISHICESHI);
//                            sendBroadcast(intent1);
//                            Intent intent = new Intent();
//                            intent.putExtra(Constant.IntentKey.ID, buyerAddinfo.getBid());
//                            intent.setClass(XinXiTXActivity.this, NaoBoActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }else {
                        Intent intent1 = new Intent();
                        intent1.setAction(Constant.BroadcastCode.KAISHICESHI);
                        sendBroadcast(intent1);
                        finish();
//                        }
                    } else if (buyerAddinfo.getStatus() == 3) {
                        MyDialog.showReLoginDialog(XinXiTXActivity.this);
                    } else {
                        Toast.makeText(XinXiTXActivity.this, buyerAddinfo.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(XinXiTXActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(XinXiTXActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
