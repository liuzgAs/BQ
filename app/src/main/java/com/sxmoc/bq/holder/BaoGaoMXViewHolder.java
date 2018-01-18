package com.sxmoc.bq.holder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxmoc.bq.R;
import com.sxmoc.bq.model.UserReportdetailed;


/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class BaoGaoMXViewHolder extends BaseViewHolder<UserReportdetailed.DataBean> {

    private final TextView textDes;
    private final TextView textCreatetime;
    private final TextView textPrice;

    public BaoGaoMXViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textDes = $(R.id.textDes);
        textCreatetime = $(R.id.textCreatetime);
        textPrice = $(R.id.textPrice);
    }

    @Override
    public void setData(UserReportdetailed.DataBean data) {
        super.setData(data);
        textDes.setText(data.getDes());
        textCreatetime.setText(data.getCreate_time());
        textPrice.setText(String.valueOf(data.getNum()));
    }

}
