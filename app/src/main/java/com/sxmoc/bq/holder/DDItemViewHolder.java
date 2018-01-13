package com.sxmoc.bq.holder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxmoc.bq.R;
import com.sxmoc.bq.model.Order;
import com.sxmoc.bq.util.GlideApp;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class DDItemViewHolder extends BaseViewHolder<Order.DataBean.ProductsBean> {

    private final ImageView imageImg;
    private final TextView textTitle;
    private final TextView textPrice;
    private final TextView textNum;

    public DDItemViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageImg = $(R.id.imageImg);
        textTitle = $(R.id.textTitle);
        textPrice = $(R.id.textPrice);
        textNum = $(R.id.textNum);
    }

    @Override
    public void setData(Order.DataBean.ProductsBean data) {
        super.setData(data);
        GlideApp.with(getContext())
                .asBitmap()
                .load(data.getImg())
                .placeholder(R.mipmap.ic_empty)
                .into(imageImg);
        textTitle.setText(data.getTitle());
        textPrice.setText(data.getPrice());
        textNum.setText("×"+data.getNum());
    }
    
}
