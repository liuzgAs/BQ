package com.sxmoc.bq.holder;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxmoc.bq.R;
import com.sxmoc.bq.activity.XinXiTXActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.model.TesterGettester;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class BaoBaoViewHolder extends BaseViewHolder<TesterGettester.DataBean> {

    private final TextView textName;
    private final TextView textTime;
    private final TextView textBianJi;
    private final ImageView imageSex;
    TesterGettester.DataBean data;

    public BaoBaoViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textName = $(R.id.textName);
        textTime = $(R.id.textTime);
        textBianJi = $(R.id.textBianJi);
        imageSex = $(R.id.imageSex);
        textBianJi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getContext(), XinXiTXActivity.class);
                intent.putExtra(Constant.IntentKey.ID,data.getBid());
                getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void setData(TesterGettester.DataBean data) {
        super.setData(data);
        this.data=data;
        textName.setText(data.getName());
        textTime.setText(data.getBirthday());
        if (data.getSex()==0){
            imageSex.setImageResource(R.mipmap.nan);
        }else {
            imageSex.setImageResource(R.mipmap.nv);
        }
    }
    
}
