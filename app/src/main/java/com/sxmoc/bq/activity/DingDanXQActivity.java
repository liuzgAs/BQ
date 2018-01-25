package com.sxmoc.bq.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxmoc.bq.R;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.customview.TwoBtnDialog;
import com.sxmoc.bq.holder.DDXQViewHolder;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.OrderGetorderdetail;
import com.sxmoc.bq.model.SimpleInfo;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;

import java.util.HashMap;
import java.util.List;

public class DingDanXQActivity extends ZjbBaseActivity implements View.OnClickListener {

    private EasyRecyclerView recyclerView;
    private int id;
    private String order_no;
    private RecyclerArrayAdapter<OrderGetorderdetail.GoodsInfoBean> adapter;
    private OrderGetorderdetail orderGetorderdetail;
    private Button btn01;
    private Button btn02;
    private View viewDiBu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ding_dan_xq);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        id = intent.getIntExtra(Constant.IntentKey.ID, 0);
        order_no = intent.getStringExtra(Constant.IntentKey.VALUE);
    }

    @Override
    protected void findID() {
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
        btn01 = (Button) findViewById(R.id.btn01);
        btn02 = (Button) findViewById(R.id.btn02);
        viewDiBu = findViewById(R.id.viewDiBu);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("订单详情");
        viewDiBu.setVisibility(View.INVISIBLE);
        initRecycler();
    }

    /**
     * 初始化recyclerview
     */
    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.line_width), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setRefreshingColorResources(R.color.basic_color);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<OrderGetorderdetail.GoodsInfoBean>(DingDanXQActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_dd_item;
                return new DDXQViewHolder(parent, layout);
            }
        });
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {

            private View viewAddress;
            private TextView textAddress;
            private TextView textPhone;
            private TextView textShouHuoRen;

            @Override
            public View onCreateView(ViewGroup parent) {
                View view = LayoutInflater.from(DingDanXQActivity.this).inflate(R.layout.view_address, null);
                viewAddress = view.findViewById(R.id.viewAddress);
                textShouHuoRen = view.findViewById(R.id.textShouHuoRen);
                textPhone = view.findViewById(R.id.textPhone);
                textAddress = view.findViewById(R.id.textAddress);
                return view;
            }

            @Override
            public void onBindView(View headerView) {
                if (orderGetorderdetail != null) {
                    if (TextUtils.isEmpty(orderGetorderdetail.getAddress())) {
                        viewAddress.setVisibility(View.GONE);
                    } else {
                        viewAddress.setVisibility(View.VISIBLE);
                        textShouHuoRen.setText(orderGetorderdetail.getConsignee());
                        textPhone.setText(orderGetorderdetail.getPhone());
                        textAddress.setText(orderGetorderdetail.getAddress());
                    }
                }
            }
        });
        adapter.addFooter(new RecyclerArrayAdapter.ItemView() {

            private TextView textZhiFuType;
            private TextView textShiJian;
            private TextView textBianHao;
            private TextView textPrice;

            @Override
            public View onCreateView(ViewGroup parent) {
                View view = LayoutInflater.from(DingDanXQActivity.this).inflate(R.layout.foot_dingdan_xq, null);
                textPrice = view.findViewById(R.id.textPrice);
                textBianHao = view.findViewById(R.id.textBianHao);
                textShiJian = view.findViewById(R.id.textShiJian);
                textZhiFuType = view.findViewById(R.id.textZhiFuType);
                return view;
            }

            @Override
            public void onBindView(View headerView) {
                if (orderGetorderdetail != null) {
                    textPrice.setText("¥" + orderGetorderdetail.getAmount());
                    textBianHao.setText(orderGetorderdetail.getOrder_no());
                    textShiJian.setText(orderGetorderdetail.getCreate_time());
                    textZhiFuType.setText(orderGetorderdetail.getPay_type());
                }
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }
        });
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.ORDER_GETORDERDETAIL;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("oid", String.valueOf(id));
        params.put("order_no", order_no);
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        ApiClient.post(this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("订单详情", s);
//                try {
                    orderGetorderdetail = GsonUtils.parseJSON(s, OrderGetorderdetail.class);
                    if (orderGetorderdetail.getStatus() == 1) {
                        switch (orderGetorderdetail.getOrder_status()) {
                            case 1:
                                btn01.setVisibility(View.VISIBLE);
                                btn02.setVisibility(View.VISIBLE);
                                btn01.setText("取消订单");
                                btn01.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        final TwoBtnDialog twoBtnDialog = new TwoBtnDialog(DingDanXQActivity.this,"确定取消该订单吗？", "是", "否");
                                        twoBtnDialog.show();
                                        twoBtnDialog.setClicklistener(new TwoBtnDialog.ClickListenerInterface() {
                                            @Override
                                            public void doConfirm() {
                                                Intent intent = new Intent();
                                                intent.setAction(Constant.BroadcastCode.SHUA_XIN_DD);
                                                sendBroadcast(intent);
                                                quXiaoDD();
                                                twoBtnDialog.dismiss();
                                            }

                                            @Override
                                            public void doCancel() {
                                                twoBtnDialog.dismiss();
                                            }
                                        });
                                    }
                                });
                                btn02.setText("立即付款");
                                btn02.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        liJiFK();
                                    }
                                });
                                break;
                            case 2:
                                if (orderGetorderdetail.getGoods_info().get(0).getGoods_type()==2){
                                    btn01.setVisibility(View.GONE);
                                    btn02.setVisibility(View.GONE);
                                }else {
                                    btn01.setVisibility(View.GONE);
                                    btn02.setVisibility(View.VISIBLE);
                                    btn02.setText("查看物流");
                                    btn02.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent();
                                            intent.setClass(DingDanXQActivity.this, WebActivity.class);
                                            intent.putExtra(Constant.IntentKey.TITLE, "物流信息");
                                            intent.putExtra(Constant.IntentKey.URL,orderGetorderdetail.getLogistics_url());
                                            startActivity(intent);
                                        }
                                    });
                                }

                                break;
                            case 3:
                                btn01.setVisibility(View.GONE);
                                btn02.setVisibility(View.VISIBLE);
                                btn02.setText("确认收货");
                                btn02.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        queRenSH();
                                    }
                                });
                                break;
                            case 4:
                                btn01.setVisibility(View.GONE);
                                btn02.setVisibility(View.VISIBLE);
                                btn02.setText("立即评价");
                                btn02.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent();
                                        intent.setClass(DingDanXQActivity.this, PingJiaActivity.class);
                                        intent.putExtra(Constant.IntentKey.ID,id);
                                        startActivity(intent);
                                    }
                                });
                                break;
                            case 5:
                                btn01.setVisibility(View.GONE);
                                btn02.setVisibility(View.VISIBLE);
                                btn02.setText("已完成");
                                btn02.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                      finish();
                                    }
                                });
                                break;
                            default:

                                break;
                        }


                        viewDiBu.setVisibility(View.VISIBLE);
                        adapter.clear();
                        List<OrderGetorderdetail.GoodsInfoBean> goodsInfoBeanList = orderGetorderdetail.getGoods_info();
                        adapter.addAll(goodsInfoBeanList);

                    } else if (orderGetorderdetail.getStatus() == 3) {
                        MyDialog.showReLoginDialog(DingDanXQActivity.this);
                    } else {
                        showError(orderGetorderdetail.getInfo());
                    }
//                } catch (Exception e) {
//                    showError("数据出错");
//                }
            }

            @Override
            public void onError() {
                showError("网络出错");
            }

            /**
             * 错误显示
             * @param msg
             */
            private void showError(String msg) {
                View viewLoader = LayoutInflater.from(DingDanXQActivity.this).inflate(R.layout.view_loaderror, null);
                TextView textMsg = viewLoader.findViewById(R.id.textMsg);
                textMsg.setText(msg);
                viewLoader.findViewById(R.id.buttonReLoad).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerView.showProgress();
                        initData();
                    }
                });
                recyclerView.setErrorView(viewLoader);
                recyclerView.showError();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
    private OkObject getQueRenDDOkObject() {
        String url = Constant.HOST + Constant.Url.ORDER_CONFIRMORDER;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime",tokenTime);
        }
        params.put("oid",String.valueOf(id));
        return new OkObject(params, url);
    }

    /**
     * 确认收货
     */
    private void queRenSH() {
        showLoadingDialog();
        ApiClient.post(this, getQueRenDDOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("DDViewHolder--onSuccess",s+ "");
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    if (simpleInfo.getStatus()==1){
                        MyDialog.showTipDialog(DingDanXQActivity.this,simpleInfo.getInfo());
                    }else if (simpleInfo.getStatus()==3){
                        MyDialog.showReLoginDialog(DingDanXQActivity.this);
                    }else {
                        Toast.makeText(DingDanXQActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(DingDanXQActivity.this,"数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(DingDanXQActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 立即付款
     */
    private void liJiFK() {
        Intent intent = new Intent();
        intent.putExtra(Constant.IntentKey.ORDER, orderGetorderdetail.getOrder_no());
        intent.putExtra(Constant.IntentKey.VALUE, orderGetorderdetail.getAmount());
        intent.setClass(DingDanXQActivity.this, LiJiZFActivity.class);
        DingDanXQActivity.this.startActivity(intent);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getQuXiaoOkObject() {
        String url = Constant.HOST + Constant.Url.ORDER_CANCELORDER;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime",tokenTime);
        }
        params.put("id",String.valueOf(id));
        return new OkObject(params, url);
    }

    /**
     * 取消订单
     */
    private void quXiaoDD() {
        showLoadingDialog();
        ApiClient.post(DingDanXQActivity.this, getQuXiaoOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("DDViewHolder--onSuccess",s+ "");
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    if (simpleInfo.getStatus()==1){
                        MyDialog.showTipDialog(DingDanXQActivity.this,simpleInfo.getInfo());
                    }else if (simpleInfo.getStatus()==3){
                        MyDialog.showReLoginDialog(DingDanXQActivity.this);
                    }else {
                        Toast.makeText(DingDanXQActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(DingDanXQActivity.this,"数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
               cancelLoadingDialog();
                Toast.makeText(DingDanXQActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
