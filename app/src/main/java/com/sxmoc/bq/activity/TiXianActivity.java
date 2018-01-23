package com.sxmoc.bq.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxmoc.bq.R;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.customview.SingleBtnDialog;
import com.sxmoc.bq.model.BankCardlist;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.SimpleInfo;
import com.sxmoc.bq.model.WithdrawAddbefore;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;
import com.sxmoc.bq.util.MoneyInputFilter;
import com.sxmoc.bq.util.StringUtil;

import java.util.HashMap;
import java.util.List;

public class TiXianActivity extends ZjbBaseActivity implements View.OnClickListener {

    private EditText editJinE;
    private TextView textYuE;
    private TextView textDes;
    private TextView buttonSms;
    private EditText editPhone;
    private EditText editCode;
    private Runnable mR;
    private int[] mI;
    private String mPhone_sms;
    private TextView textViewRight;
    int bankID;
    private TextView textBank1;
    private View viewPhone;
    private View viewCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ti_xian);
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
        editJinE = (EditText) findViewById(R.id.editJinE);
        textYuE = (TextView) findViewById(R.id.textYuE);
        textDes = (TextView) findViewById(R.id.textDes);
        buttonSms = (TextView) findViewById(R.id.buttonSms);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editCode = (EditText) findViewById(R.id.editCode);
        textViewRight = (TextView) findViewById(R.id.textViewRight);
        textBank1 = (TextView) findViewById(R.id.textBank1);
        viewPhone = findViewById(R.id.viewPhone);
        viewCode = findViewById(R.id.viewCode);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("提现");
        MoneyInputFilter.init(editJinE);
        textViewRight.setText("提现记录");
        viewPhone.setVisibility(View.GONE);
        viewCode.setVisibility(View.GONE);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.btnLiJiTX).setOnClickListener(this);
        findViewById(R.id.viewBank).setOnClickListener(this);
        buttonSms.setOnClickListener(this);
        textViewRight.setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.WITHDRAW_ADDBEFORE;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(TiXianActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("TiXianActivity--onSuccess", s + "");
                try {
                    WithdrawAddbefore withdrawAddbefore = GsonUtils.parseJSON(s, WithdrawAddbefore.class);
                    if (withdrawAddbefore.getStatus() == 1) {
//                        editJinE.setText(withdrawAddbefore.getMoney() + "");
//                        editJinE.setSelection((withdrawAddbefore.getMoney() + "").length());
                        textYuE.setText(withdrawAddbefore.getMoneyDes());
                        textDes.setText(withdrawAddbefore.getDes());
                    } else if (withdrawAddbefore.getStatus() == 3) {
                        MyDialog.showReLoginDialog(TiXianActivity.this);
                    } else {
                        Toast.makeText(TiXianActivity.this, withdrawAddbefore.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(TiXianActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(TiXianActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getTXOkObject() {
        String url = Constant.HOST + Constant.Url.WITHDRAW_ADDDONE;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("money", editJinE.getText().toString().trim());
        params.put("bank", String.valueOf(bankID));
        params.put("userName", editPhone.getText().toString().trim());
        params.put("code", editCode.getText().toString().trim());
        return new OkObject(params, url);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewBank:
                chooseBank();
                break;
            case R.id.textViewRight:
                Intent intent = new Intent();
                intent.setClass(TiXianActivity.this, TiXianJLActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonSms:
                sendSMS();
                break;
            case R.id.btnLiJiTX:
                if (TextUtils.isEmpty(editJinE.getText().toString().trim())) {
                    Toast.makeText(TiXianActivity.this, "请填写提现金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Double.parseDouble(editJinE.getText().toString().trim()) == 0) {
                    Toast.makeText(TiXianActivity.this, "提现金额必须大于0", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (bankID == 0) {
                    Toast.makeText(TiXianActivity.this, "请选择提现银行卡", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editPhone.getText().toString().trim())) {
                    Toast.makeText(TiXianActivity.this, "请输入银行预留手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editCode.getText().toString().trim())) {
                    Toast.makeText(TiXianActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                tiXian();
                break;
            case R.id.imageBack:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * des： 短信发送按钮状态
     * author： ZhangJieBo
     * date： 2017/8/22 0022 上午 10:26
     */
    private void sendSMS() {
        buttonSms.removeCallbacks(mR);
        boolean mobileNO = StringUtil.isMobileNO(editPhone.getText().toString().trim());
        if (mobileNO) {
            mPhone_sms = editPhone.getText().toString().trim();
            buttonSms.setEnabled(false);
            mI = new int[]{60};

            mR = new Runnable() {
                @Override
                public void run() {
                    buttonSms.setText((mI[0]--) + "秒后重发");
                    if (mI[0] == 0) {
                        buttonSms.setEnabled(true);
                        buttonSms.setText("重新发送");
                        return;
                    } else {

                    }
                    buttonSms.postDelayed(mR, 1000);
                }
            };
            buttonSms.postDelayed(mR, 0);
            getSms();
        } else {
            Toast.makeText(TiXianActivity.this, "输入正确的手机号", Toast.LENGTH_SHORT).show();
            editPhone.setText("");
        }
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject1() {
        String url = Constant.HOST + Constant.Url.LOGIN_BINDSMS;
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", mPhone_sms);
        return new OkObject(params, url);
    }


    /**
     * des： 获取短信
     * author： ZhangJieBo
     * date： 2017/9/11 0011 下午 4:32
     */
    private void getSms() {
        showLoadingDialog();
        ApiClient.post(TiXianActivity.this, getOkObject1(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("RenZhengFragment--获取短信", "" + s);
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    Toast.makeText(TiXianActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    if (simpleInfo.getStatus() == 1) {

                    }
                } catch (Exception e) {
                    Toast.makeText(TiXianActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(TiXianActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getBankOkObject() {
        String url = Constant.HOST + Constant.Url.BANK_CARDLIST;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        return new OkObject(params, url);
    }

    /**
     * 选择提现银行卡
     */
    private void chooseBank() {
        showLoadingDialog();
        ApiClient.post(TiXianActivity.this, getBankOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("TiXianActivity--onSuccess", s + "");
                try {
                    BankCardlist bankCardlist = GsonUtils.parseJSON(s, BankCardlist.class);
                    if (bankCardlist.getStatus() == 1) {
                        final List<BankCardlist.DataBean> dataBeanList = bankCardlist.getData();
                        View dialog_tu_pian = LayoutInflater.from(TiXianActivity.this).inflate(R.layout.dialog_bank, null);
                        final AlertDialog alertDialog = new AlertDialog.Builder(TiXianActivity.this, R.style.dialog)
                                .setView(dialog_tu_pian)
                                .create();
                        ListView listView = dialog_tu_pian.findViewById(R.id.listView);
                        listView.setAdapter(new MyAdapter(dataBeanList));
                        dialog_tu_pian.findViewById(R.id.textAdd).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setClass(TiXianActivity.this, XinZengYHKActivity.class);
                                intent.putExtra(Constant.IntentKey.TITLE, "新增银行卡");
                                startActivityForResult(intent, Constant.RequestResultCode.XIN_YONG_KA);
                                alertDialog.dismiss();
                            }
                        });
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                bankID = dataBeanList.get(i).getId();
                                textBank1.setText(dataBeanList.get(i).getBank() + "(" + dataBeanList.get(i).getBankCard() + ")");
                                viewPhone.setVisibility(View.VISIBLE);
                                viewCode.setVisibility(View.VISIBLE);
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                        Window dialogWindow = alertDialog.getWindow();
                        dialogWindow.setGravity(Gravity.BOTTOM);
                        dialogWindow.setWindowAnimations(R.style.dialogFenXiang);
                        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                        DisplayMetrics d = getResources().getDisplayMetrics(); // 获取屏幕宽、高用
                        lp.width = (int) (d.widthPixels * 1); // 高度设置为屏幕的0.6
                        dialogWindow.setAttributes(lp);
                    } else if (bankCardlist.getStatus() == 3) {
                        MyDialog.showReLoginDialog(TiXianActivity.this);
                    } else {
                        Toast.makeText(TiXianActivity.this, bankCardlist.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(TiXianActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(TiXianActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class MyAdapter extends BaseAdapter {
        List<BankCardlist.DataBean> dataBeanList;

        public MyAdapter(List<BankCardlist.DataBean> dataBeanList) {
            this.dataBeanList = dataBeanList;
        }

        class ViewHolder {
            public TextView textBank;
        }

        @Override
        public int getCount() {
            return dataBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(TiXianActivity.this).inflate(R.layout.item_bank, null);
                holder.textBank = convertView.findViewById(R.id.textBank);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textBank.setText(dataBeanList.get(position).getBank() + "(" + dataBeanList.get(position).getBankCard() + ")");
            return convertView;
        }
    }

    private void tiXian() {
        showLoadingDialog();
        ApiClient.post(TiXianActivity.this, getTXOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("TiXianActivity--onSuccess", s + "");
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    if (simpleInfo.getStatus() == 1) {
                        initData();
                        final Intent intent = new Intent(Constant.BroadcastCode.TIXIAN);
                        sendBroadcast(intent);
                        final SingleBtnDialog singleBtnDialog = new SingleBtnDialog(TiXianActivity.this, simpleInfo.getInfo(), "确认");
                        singleBtnDialog.show();
                        singleBtnDialog.setClicklistener(new SingleBtnDialog.ClickListenerInterface() {
                            @Override
                            public void doWhat() {
                                singleBtnDialog.dismiss();
                                Intent intent1 = new Intent();
                                intent1.setClass(TiXianActivity.this, TiXianJLActivity.class);
                                startActivity(intent1);
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
                    } else if (simpleInfo.getStatus() == 3) {
                        MyDialog.showReLoginDialog(TiXianActivity.this);
                    } else {
                        MyDialog.showTipDialog(TiXianActivity.this, simpleInfo.getInfo());
                    }
                } catch (Exception e) {
                    Toast.makeText(TiXianActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(TiXianActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
