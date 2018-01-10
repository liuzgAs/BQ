package com.sxmoc.bq.holder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxmoc.bq.R;
import com.sxmoc.bq.model.GoodsInfo;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class ChanPinDesViewHolder extends BaseViewHolder<GoodsInfo.DesListBean> {

    private final TextView textTitle;
    private final TextView textDes;

    public ChanPinDesViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textTitle = $(R.id.textTitle);
        textDes = $(R.id.textDes);
    }

    @Override
    public void setData(GoodsInfo.DesListBean data) {
        super.setData(data);
        textTitle.setText(data.getName());
        textDes.setText(data.getValue());
    }
    
}
