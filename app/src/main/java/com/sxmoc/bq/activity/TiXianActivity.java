package com.sxmoc.bq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
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
import com.sxmoc.bq.model.BankCardlist;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.SimpleInfo;
import com.sxmoc.bq.model.WithdrawAddbefore;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;
import com.sxmoc.bq.util.MoneyInputFilter;

import java.util.HashMap;
import java.util.List;

public class TiXianActivity extends ZjbBaseActivity implements View.OnClickListener {

    private EditText editJinE;
    private TextView textYuE;
    private TextView textDes;

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
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("提现");
        MoneyInputFilter.init(editJinE);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.btnLiJiTX).setOnClickListener(this);
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
    private OkObject getTXOkObject(int bankID) {
        String url = Constant.HOST + Constant.Url.WITHDRAW_ADDDONE;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("money", editJinE.getText().toString().trim());
        params.put("bank", String.valueOf(bankID));
        return new OkObject(params, url);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLiJiTX:
                if (Double.parseDouble(editJinE.getText().toString().trim())==0){
                    Toast.makeText(TiXianActivity.this, "提现金额必须大于0", Toast.LENGTH_SHORT).show();
                    return;
                }
                chooseBank();
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
    private OkObject getBankOkObject() {
        String url = Constant.HOST + Constant.Url.BANK_CARDLIST;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime",tokenTime);
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
                                tiXian(dataBeanList.get(i).getId());
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
            this.dataBeanList=dataBeanList;
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
            holder.textBank.setText(dataBeanList.get(position).getBank()+"("+dataBeanList.get(position).getBankCard()+")");
            return convertView;
        }
    }

    private void tiXian(int bankID) {
        showLoadingDialog();
        ApiClient.post(TiXianActivity.this, getTXOkObject(bankID), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("TiXianActivity--onSuccess", s + "");
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    if (simpleInfo.getStatus() == 1) {
                        initData();
                        Intent intent = new Intent(Constant.BroadcastCode.TIXIAN);
                        sendBroadcast(intent);
                        MyDialog.showTipDialog(TiXianActivity.this, simpleInfo.getInfo());
                    } else if (simpleInfo.getStatus() == 3) {
                        MyDialog.showReLoginDialog(TiXianActivity.this);
                    } else {
                        Toast.makeText(TiXianActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
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
