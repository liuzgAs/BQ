package com.sxmoc.bq.holder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxmoc.bq.R;
import com.sxmoc.bq.model.WithdrawGetwithdraw;


/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class TiXianJLViewHolder extends BaseViewHolder<WithdrawGetwithdraw.DataBean> {

    private final TextView textDes;
    private final TextView textCreatetime;
    private final TextView textPrice;

    public TiXianJLViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textDes = $(R.id.textDes);
        textCreatetime = $(R.id.textCreatetime);
        textPrice = $(R.id.textPrice);
    }

    @Override
    public void setData(WithdrawGetwithdraw.DataBean data) {
        super.setData(data);
        textDes.setText("提现" + data.getMoney());
        textCreatetime.setText(data.getCreate_time());
        if (data.getStatus() == 1) {
            textPrice.setText("提现中");
        } else if (data.getStatus() == 2) {
            textPrice.setText("已拒绝");
        } else {
            textPrice.setText("已到账");
        }
    }

}
