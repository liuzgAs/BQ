package com.sxmoc.bq.holder;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxmoc.bq.R;
import com.sxmoc.bq.model.EvaluateBean;
import com.sxmoc.bq.util.GlideApp;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class ChanPinPJViewHolder extends BaseViewHolder<EvaluateBean> {
    int[] imgIdArr = new int[]{
            R.id.imagePic1,
            R.id.imagePic2,
            R.id.imagePic3,
            R.id.imagePic4,
            R.id.imagePic5,
    };
    ImageView[] imageViews = new ImageView[5];
    private final View viewImgs;
    private final ImageView imageImg;
    private final TextView textName;
    private final TextView textDes;
    private final SimpleRatingBar ratingbar_pingfeng;

    public ChanPinPJViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageImg = $(R.id.imageImg);
        textName = $(R.id.textName);
        textDes = $(R.id.textDes);
        viewImgs = $(R.id.viewImgs);
        ratingbar_pingfeng = $(R.id.ratingbar_pingfeng);
        for (int i = 0; i < imgIdArr.length; i++) {
            imageViews[i] = $(imgIdArr[i]);
        }
        viewImgs.setVisibility(View.GONE);
        ratingbar_pingfeng.setVisibility(View.GONE);
    }

    @Override
    public void setData(EvaluateBean data) {
        super.setData(data);
        GlideApp.with(getContext())
                .asBitmap()
                .circleCrop()
                .load(data.getHeadimg())
                .placeholder(R.mipmap.ic_empty)
                .into(imageImg);
        textName.setText(data.getName());
        textDes.setText(data.getContent());
    }
    
}
