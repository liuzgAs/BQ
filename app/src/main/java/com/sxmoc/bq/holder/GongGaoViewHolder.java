package com.sxmoc.bq.holder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxmoc.bq.R;
import com.sxmoc.bq.model.UserMsg;
import com.sxmoc.bq.util.DateTransforam;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class GongGaoViewHolder extends BaseViewHolder<UserMsg.DataBean> {

    private final TextView textAddTime;
    private final TextView textTitle;
    private final TextView textIntro;
    private UserMsg.DataBean data;

    public GongGaoViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textAddTime = $(R.id.textAddTime);
        textTitle = $(R.id.textTitle);
        textIntro = $(R.id.textIntro);
    }

    @Override
    public void setData(UserMsg.DataBean data) {
        super.setData(data);
        this.data=data;
        textAddTime.setText(DateTransforam.stampToDate(data.getCreate_time()));
        textTitle.setText(data.getTitle());
        textIntro.setText(data.getDes());
    }


}
