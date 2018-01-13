package com.sxmoc.bq.holder;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxmoc.bq.R;
import com.sxmoc.bq.model.Order;

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
            }
        });
    }

    @Override
    public void setData(Order.DataBean data) {
        super.setData(data);
        textOrder.setText("订单号："+data.getOrder_no());
        List<Order.DataBean.ProductsBean> productsBeanList = data.getProducts();
        adapter.clear();
        adapter.addAll(productsBeanList);
        switch (data.getOrder_status()) {
            case 1:
                textCancle.setVisibility(View.VISIBLE);
                btnPingJia.setVisibility(View.VISIBLE);
                textCancle.setText("取消订单");
                btnPingJia.setText("立即付款");
                break;
            case 2:
                textCancle.setVisibility(View.GONE);
                btnPingJia.setVisibility(View.VISIBLE);
                btnPingJia.setText("查看物流");
                break;
            case 3:
                textCancle.setVisibility(View.GONE);
                btnPingJia.setVisibility(View.VISIBLE);
                btnPingJia.setText("确认收货");
                break;
            case 4:
                textCancle.setVisibility(View.GONE);
                btnPingJia.setVisibility(View.VISIBLE);
                btnPingJia.setText("立即评价");
                break;
            default:

                break;
        }
    }

}
