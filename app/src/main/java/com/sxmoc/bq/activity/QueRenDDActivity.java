package com.sxmoc.bq.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.sxmoc.bq.holder.QueRenDDViewHolder;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.OrderCreateorder;
import com.sxmoc.bq.model.OrderSubmitorder;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.Arith;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;

import java.util.HashMap;

public class QueRenDDActivity extends ZjbBaseActivity implements View.OnClickListener {

    private int id;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<OrderCreateorder> adapter;
    public TextView textSum;
    private OrderCreateorder orderCreateorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_ren_dd);
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
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
        textSum = (TextView) findViewById(R.id.textSum);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("确认订单");
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
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<OrderCreateorder>(QueRenDDActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_queren_dd;
                return new QueRenDDViewHolder(parent, layout);
            }
        });
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.buttonTiJiao).setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.ORDER_CREATEORDER;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("id", id + "");
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        ApiClient.post(this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("确认订单页", s);
                try {
                    orderCreateorder = GsonUtils.parseJSON(s, OrderCreateorder.class);
                    if (orderCreateorder.getStatus() == 1) {
                        orderCreateorder.setNum(1);
                        textSum.setText("¥" + orderCreateorder.getGoods_price());
                        adapter.clear();
                        adapter.add(orderCreateorder);
                        adapter.notifyDataSetChanged();
                    } else if (orderCreateorder.getStatus() == 3) {
                        MyDialog.showReLoginDialog(QueRenDDActivity.this);
                    } else {
                        showError(orderCreateorder.getInfo());
                    }
                } catch (Exception e) {
                    showError("数据出错");
                }
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
                View viewLoader = LayoutInflater.from(QueRenDDActivity.this).inflate(R.layout.view_loaderror, null);
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
            case R.id.buttonTiJiao:
                if (orderCreateorder.getIs_deddr() == 0) {
                    Toast.makeText(QueRenDDActivity.this, "没有收货地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (orderCreateorder.getNum()==0){
                    Toast.makeText(QueRenDDActivity.this, "商品数量必须大于1", Toast.LENGTH_SHORT).show();
                    return;
                }
                tiJiaoDingDan();
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
    private OkObject getTiJiaoDDOkObject() {
        String url = Constant.HOST + Constant.Url.ORDER_SUBMITORDER;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime",tokenTime);
        }
        params.put("id",id+"");
        params.put("num",orderCreateorder.getNum()+"");
        params.put("type_id","1");
        params.put("phone",orderCreateorder.getPhone());
        params.put("address",orderCreateorder.getAddress());
        params.put("consignee",orderCreateorder.getConsignee());
        return new OkObject(params, url);
    }

    /**
     * 提交订单
     */
    private void tiJiaoDingDan() {
        showLoadingDialog();
        ApiClient.post(QueRenDDActivity.this, getTiJiaoDDOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("QueRenDDActivity--onSuccess",s+ "");
                try {
                    OrderSubmitorder orderSubmitorder = GsonUtils.parseJSON(s, OrderSubmitorder.class);
                    if (orderSubmitorder.getStatus()==1){
                        Intent intent = new Intent();
                        intent.putExtra(Constant.IntentKey.ORDER,orderSubmitorder.getOrder_no());
                        Double price = Arith.mul((double) orderCreateorder.getNum(), Double.parseDouble(orderCreateorder.getGoods_price()));
                        intent.putExtra(Constant.IntentKey.VALUE,price);
                        intent.setClass(QueRenDDActivity.this, LiJiZFActivity.class);
                        startActivity(intent);
                    }else if (orderSubmitorder.getStatus()==3){
                        MyDialog.showReLoginDialog(QueRenDDActivity.this);
                    }else {
                        Toast.makeText(QueRenDDActivity.this, orderSubmitorder.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(QueRenDDActivity.this,"数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(QueRenDDActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
