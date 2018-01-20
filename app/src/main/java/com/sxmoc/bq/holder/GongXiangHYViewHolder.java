package com.sxmoc.bq.holder;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxmoc.bq.R;
import com.sxmoc.bq.model.UserGetmyshare1;
import com.sxmoc.bq.util.GlideApp;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class GongXiangHYViewHolder extends BaseViewHolder<UserGetmyshare1.DataBean> {

    private final ImageView imageImg;
    private final TextView textName;
    private final TextView textCount_des;
    private final TextView textGrade;

    public GongXiangHYViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageImg = $(R.id.imageImg);
        textName = $(R.id.textName);
        textCount_des = $(R.id.textCount_des);
        textGrade = $(R.id.textGrade);
        ImageView imageRight =  $(R.id.imageRight);
        imageRight.setVisibility(View.GONE);
    }

    @Override
    public void setData(UserGetmyshare1.DataBean data) {
        super.setData(data);
        GlideApp.with(getContext())
                .asBitmap()
                .circleCrop()
                .load(data.getImg())
                .placeholder(R.mipmap.ic_empty)
                .into(imageImg);
        textName.setText(data.getName());
        textCount_des.setText(data.getCount_des());
        textGrade.setText(data.getGrade_name());
        switch (data.getGrade_type()) {
            case 1:
                textGrade.setBackgroundResource(R.drawable.shape_not_right_top_20dp_01);
                break;
            case 2:
                textGrade.setBackgroundResource(R.drawable.shape_not_right_top_20dp_02);
                break;
            case 3:
                textGrade.setBackgroundResource(R.drawable.shape_not_right_top_20dp_03);
                break;
            case 4:
                textGrade.setBackgroundResource(R.drawable.shape_not_right_top_20dp_04);
                break;
            case 5:
                textGrade.setBackgroundResource(R.drawable.shape_not_right_top_20dp_05);
                break;
            default:
                break;
        }
    }

}
