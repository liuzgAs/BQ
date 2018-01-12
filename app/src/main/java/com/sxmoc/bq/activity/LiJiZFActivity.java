package com.sxmoc.bq.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.sxmoc.bq.R;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.customview.SingleBtnDialog;
import com.sxmoc.bq.model.AliPayBean;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.PayAlipay;
import com.sxmoc.bq.model.SimpleInfo;
import com.sxmoc.bq.model.UserGetbalance;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

public class LiJiZFActivity extends ZjbBaseActivity implements View.OnClickListener {

    private String order;
    private double price;
    private TextView textBlance;
    private int payMode = 0;
    private View[] paySelectView = new View[3];
    private View[] payView = new View[3];
    final IWXAPI api = WXAPIFactory.createWXAPI(this, null);
    private BroadcastReceiver recevier = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BroadcastCode.PAY_RECEIVER:
                    cancelLoadingDialog();
                    int error = intent.getIntExtra("error", -1);
                    if (error == 0) {
                        paySuccess();
                    } else if (error == -1) {
                        MyDialog.showTipDialog(LiJiZFActivity.this, "支付失败");
                    } else if (error == -2) {
                        MyDialog.showTipDialog(LiJiZFActivity.this, "支付失败");
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_li_ji_zf);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        order = intent.getStringExtra(Constant.IntentKey.ORDER);
        price = intent.getDoubleExtra(Constant.IntentKey.VALUE, 0);
    }

    @Override
    protected void findID() {
        textBlance = (TextView) findViewById(R.id.textBlance);
        paySelectView[0] = findViewById(R.id.imageYuE);
        paySelectView[1] = findViewById(R.id.imageZhiFuBao);
        paySelectView[2] = findViewById(R.id.imageWeiXin);
        payView[0] = findViewById(R.id.viewYuE);
        payView[1] = findViewById(R.id.viewZhiFuBao);
        payView[2] = findViewById(R.id.viewWeiXin);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("立即支付");
        ((TextView) findViewById(R.id.textPrice)).setText("¥" + price);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        for (int i = 0; i < payView.length; i++) {
            final int finalI = i;
            payView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < paySelectView.length; j++) {
                        paySelectView[j].setVisibility(View.GONE);
                    }
                    paySelectView[finalI].setVisibility(View.VISIBLE);
                    payMode = finalI;
                }
            });
        }
        findViewById(R.id.btnLiJiZF).setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.USER_GETBALANCE;
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
        ApiClient.post(LiJiZFActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("LiJiZFActivity--onSuccess", s + "");
                try {
                    UserGetbalance userGetbalance = GsonUtils.parseJSON(s, UserGetbalance.class);
                    if (userGetbalance.getStatus() == 1) {
                        textBlance.setText("\u3000|\u3000余额：¥" + userGetbalance.getBalance());
                    } else if (userGetbalance.getStatus() == 3) {
                        MyDialog.showReLoginDialog(LiJiZFActivity.this);
                    } else {
                        Toast.makeText(LiJiZFActivity.this, userGetbalance.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(LiJiZFActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(LiJiZFActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * des： 支付成功
     * author： ZhangJieBo
     * date： 2017/12/25/025 16:11
     */
    private void paySuccess() {
        Intent intent = new Intent();
        intent.setAction(Constant.BroadcastCode.ZHI_FU_CG);
        sendBroadcast(intent);
        final SingleBtnDialog singleBtnDialog = new SingleBtnDialog(this, "支付成功", "确认");
        singleBtnDialog.setClicklistener(new SingleBtnDialog.ClickListenerInterface() {
            @Override
            public void doWhat() {
                singleBtnDialog.dismiss();
                finish();
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLiJiZF:
                switch (payMode) {
                    case 0:
                        zhiFu();
                        break;
                    case 1:
                        zhiFuBao(order);
                        break;
                    case 2:
                        MyDialog.showTipDialog(this,"微信支付暂未开通，敬请期待");
                        break;
                    default:
                        break;
                }
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
    private OkObject getZhiFuOkObject() {
        String url = Constant.HOST + Constant.Url.PAY_BALANCEPAY;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("order_no", order);
        return new OkObject(params, url);
    }

    /**
     * 支付
     */
    private void zhiFu() {
        showLoadingDialog();
        ApiClient.post(LiJiZFActivity.this, getZhiFuOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("LiJiZFActivity--onSuccess", s + "");
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    if (simpleInfo.getStatus() == 1) {
                        paySuccess();
                    } else if (simpleInfo.getStatus() == 3) {
                        MyDialog.showReLoginDialog(LiJiZFActivity.this);
                    } else {
                        Toast.makeText(LiJiZFActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(LiJiZFActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(LiJiZFActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getZFBOkObject(String order_no) {
        String url = Constant.HOST + Constant.Url.PAY_ALIPAY;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("order_no", order_no + "");
        return new OkObject(params, url);
    }

    /**
     * des： 支付宝支付
     * author： ZhangJieBo
     * date： 2017/12/25/025 15:13
     */
    private void zhiFuBao(String order_no) {
        showLoadingDialog();
        ApiClient.post(LiJiZFActivity.this, getZFBOkObject(order_no), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("ChaWeiBaoActivity--支付宝", s + "");
                try {
                    final PayAlipay payAlipay = GsonUtils.parseJSON(s, PayAlipay.class);
                    if (payAlipay.getStatus() == 1) {
                        Runnable payRunnable = new Runnable() {

                            @Override
                            public void run() {
                                try {
                                    PayTask alipay = new PayTask(LiJiZFActivity.this);
                                    Map<String, String> stringMap = alipay.payV2(payAlipay.getOrderinfo(), true);
                                    final AliPayBean aliPayBean = GsonUtils.parseJSON(stringMap.get("result"), AliPayBean.class);
                                    LogUtil.LogShitou("ChaWeiBaoActivity--支付结果", ""+stringMap.get("result"));
                                    LogUtil.LogShitou("ChaWeiBaoActivity--支付结果码", ""+aliPayBean.getAlipay_trade_app_pay_response().getCode());
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            switch (aliPayBean.getAlipay_trade_app_pay_response().getCode()) {
                                                case 10000:
                                                    paySuccess();
                                                    break;
                                                case 8000:
                                                    paySuccess();
                                                    break;
                                                case 4000:
                                                    MyDialog.showTipDialog(LiJiZFActivity.this, "订单支付失败");
                                                    break;
                                                case 5000:
                                                    MyDialog.showTipDialog(LiJiZFActivity.this, "重复请求");
                                                    break;
                                                case 6001:
                                                    MyDialog.showTipDialog(LiJiZFActivity.this, "取消支付");
                                                    break;
                                                case 6002:
                                                    MyDialog.showTipDialog(LiJiZFActivity.this, "网络连接错误");
                                                    break;
                                                case 6004:
                                                    MyDialog.showTipDialog(LiJiZFActivity.this, "支付结果未知");
                                                    break;
                                                default:
                                                    MyDialog.showTipDialog(LiJiZFActivity.this, "支付失败");
                                                    break;
                                            }
                                        }
                                    });
                                } catch (Exception e) {
                                }
                            }
                        };
                        // 必须异步调用
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    } else if (payAlipay.getStatus() == 3) {
                        MyDialog.showReLoginDialog(LiJiZFActivity.this);
                    } else {
                    }
                    Toast.makeText(LiJiZFActivity.this, payAlipay.getInfo(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(LiJiZFActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(LiJiZFActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BroadcastCode.PAY_RECEIVER);
        registerReceiver(recevier, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(recevier);
    }
}
