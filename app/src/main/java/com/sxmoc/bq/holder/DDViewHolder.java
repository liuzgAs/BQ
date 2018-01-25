package com.sxmoc.bq.holder;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
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
import com.sxmoc.bq.activity.DingDanXQActivity;
import com.sxmoc.bq.activity.LiJiZFActivity;
import com.sxmoc.bq.activity.PingJiaActivity;
import com.sxmoc.bq.activity.WebActivity;
import com.sxmoc.bq.activity.WoDeDDActivity;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.customview.TwoBtnDialog;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.Order;
import com.sxmoc.bq.model.SimpleInfo;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.Arith;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class DDViewHolder extends BaseViewHolder<Order.DataBean> {

    private final TextView textOrder;
    private final TextView textCancle;
    private final Button btnPingJia;
    private final EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<Order.DataBean.ProductsBean> adapter;

    public DDViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textOrder = $(R.id.textOrder);
        textCancle = $(R.id.textCancle);
        btnPingJia = $(R.id.btnPingJia);
        recyclerView = $(R.id.recyclerView);
        initRecycler();
    }

    /**
     * 初始化recyclerview
     */
    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getContext().getResources().getDimension(R.dimen.line_width), (int) getContext().getResources().getDimension(R.dimen.leftAndRight), (int) getContext().getResources().getDimension(R.dimen.leftAndRight));
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setRefreshingColorResources(R.color.basic_color);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Order.DataBean.ProductsBean>(getContext()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_dd_item;
                return new DDItemViewHolder(parent, layout);
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                intent.setClass(getContext(), DingDanXQActivity.class);
                intent.putExtra(Constant.IntentKey.ID,data.getId());
                intent.putExtra(Constant.IntentKey.VALUE,data.getOrder_no());
                getContext().startActivity(intent);
            }
        });
    }
    Order.DataBean data;
    @Override
    public void setData(final Order.DataBean data) {
        super.setData(data);
        this.data=data;
        textOrder.setText("订单号："+data.getOrder_no());
        List<Order.DataBean.ProductsBean> productsBeanList = data.getProducts();
        adapter.clear();
        adapter.addAll(productsBeanList);
        switch (data.getOrder_status()) {
            case 1:
                textCancle.setVisibility(View.VISIBLE);
                btnPingJia.setVisibility(View.VISIBLE);
                textCancle.setText("取消订单");
                textCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final TwoBtnDialog twoBtnDialog = new TwoBtnDialog(getContext(),"确定取消该订单吗？", "是", "否");
                        twoBtnDialog.show();
                        twoBtnDialog.setClicklistener(new TwoBtnDialog.ClickListenerInterface() {
                            @Override
                            public void doConfirm() {
                                Intent intent = new Intent();
                                intent.setAction(Constant.BroadcastCode.SHUA_XIN_DD);
                                getContext().sendBroadcast(intent);
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
                btnPingJia.setText("立即付款");
                btnPingJia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        liJiFK();
                    }
                });
                break;
            case 2:
                if (data.getProducts().get(0).getGoods_type()==2){
                    textCancle.setVisibility(View.GONE);
                    btnPingJia.setVisibility(View.GONE);
                }else {
                    textCancle.setVisibility(View.GONE);
                    btnPingJia.setVisibility(View.VISIBLE);
                    btnPingJia.setText("查看物流");
                    btnPingJia.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent();
                            intent.setClass(getContext(), WebActivity.class);
                            intent.putExtra(Constant.IntentKey.TITLE, "物流信息");
                            intent.putExtra(Constant.IntentKey.URL,data.getLogistics_url());
                            getContext().startActivity(intent);
                        }
                    });
                }

                break;
            case 3:
                textCancle.setVisibility(View.GONE);
                btnPingJia.setVisibility(View.VISIBLE);
                btnPingJia.setText("确认收货");
                btnPingJia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        queRenSH();
                    }
                });
                break;
            case 4:
                textCancle.setVisibility(View.GONE);
                btnPingJia.setVisibility(View.VISIBLE);
                btnPingJia.setText("立即评价");
                btnPingJia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setClass(getContext(), PingJiaActivity.class);
                        intent.putExtra(Constant.IntentKey.ID,data.getId());
                        getContext().startActivity(intent);
                    }
                });
                break;
            case 5:
                textCancle.setVisibility(View.GONE);
                btnPingJia.setVisibility(View.VISIBLE);
                btnPingJia.setText("已完成");
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
        if (((WoDeDDActivity)getContext()).isLogin) {
            params.put("uid", ((WoDeDDActivity)getContext()).userInfo.getUid());
            params.put("tokenTime",((WoDeDDActivity)getContext()).tokenTime);
        }
        params.put("oid",String.valueOf(data.getId()));
        return new OkObject(params, url);
    }

    /**
     * 确认收货
     */
    private void queRenSH() {
        ((WoDeDDActivity)getContext()).showLoadingDialog();
        ApiClient.post(getContext(), getQueRenDDOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                ((WoDeDDActivity)getContext()).cancelLoadingDialog();
                LogUtil.LogShitou("DDViewHolder--onSuccess",s+ "");
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    if (simpleInfo.getStatus()==1){
                        MyDialog.showTipDialog(getContext(),simpleInfo.getInfo());
                    }else if (simpleInfo.getStatus()==3){
                        MyDialog.showReLoginDialog(getContext());
                    }else {
                        Toast.makeText(getContext(), simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(),"数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                ((WoDeDDActivity)getContext()).cancelLoadingDialog();
                Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 立即付款
     */
    private void liJiFK() {
        Intent intent = new Intent();
        intent.putExtra(Constant.IntentKey.ORDER, data.getOrder_no());
        Double price = Arith.mul((double) data.getProducts().get(0).getNum(), Double.parseDouble(data.getProducts().get(0).getPrice()));
        intent.putExtra(Constant.IntentKey.VALUE, price);
        intent.setClass(getContext(), LiJiZFActivity.class);
        getContext().startActivity(intent);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getQuXiaoOkObject() {
        String url = Constant.HOST + Constant.Url.ORDER_CANCELORDER;
        HashMap<String, String> params = new HashMap<>();
        if (((WoDeDDActivity)getContext()).isLogin) {
            params.put("uid", ((WoDeDDActivity)getContext()).userInfo.getUid());
            params.put("tokenTime",((WoDeDDActivity)getContext()).tokenTime);
        }
        params.put("id",String.valueOf(data.getId()));
        return new OkObject(params, url);
    }

    /**
     * 取消订单
     */
    private void quXiaoDD() {
        ((WoDeDDActivity)getContext()).showLoadingDialog();
        ApiClient.post(getContext(), getQuXiaoOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                ((WoDeDDActivity)getContext()).cancelLoadingDialog();
                LogUtil.LogShitou("DDViewHolder--onSuccess",s+ "");
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    if (simpleInfo.getStatus()==1){
                        MyDialog.showTipDialog(getContext(),simpleInfo.getInfo());
                    }else if (simpleInfo.getStatus()==3){
                        MyDialog.showReLoginDialog(getContext());
                    }else {
                        Toast.makeText(getContext(), simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(),"数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                ((WoDeDDActivity)getContext()).cancelLoadingDialog();
                Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
