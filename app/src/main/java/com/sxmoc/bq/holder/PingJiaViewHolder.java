package com.sxmoc.bq.holder;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxmoc.bq.R;
import com.sxmoc.bq.model.OrderGeteeva;
import com.sxmoc.bq.util.GlideApp;

import java.util.List;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class PingJiaViewHolder extends BaseViewHolder<OrderGeteeva.DataBean> {
    int[] imgIdArr = new int[]{
            R.id.imagePic1,
            R.id.imagePic2,
            R.id.imagePic3,
            R.id.imagePic4,
            R.id.imagePic5,
    };
    ImageView[] imageViews = new ImageView[5];
    private final ImageView imageImg;
    private final TextView textName;
    private final TextView textDes;
    private final ImageView imageGood;
    private final TextView textGoodName;
    private final TextView textPrice;
    private final TextView textDate;
    private final SimpleRatingBar ratingbar_pingfeng;
    private final View viewGood;

    public PingJiaViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageImg = $(R.id.imageImg);
        textName = $(R.id.textName);
        textDes = $(R.id.textDes);
        ratingbar_pingfeng = $(R.id.ratingbar_pingfeng);
        for (int i = 0; i < imgIdArr.length; i++) {
            imageViews[i]= $(imgIdArr[i]);
        }
        imageGood = $(R.id.imageGood);
        textGoodName = $(R.id.textGoodName);
        textPrice = $(R.id.textPrice);
        textDate = $(R.id.textDate);
        viewGood = $(R.id.viewGood);
    }

    @Override
    public void setData(OrderGeteeva.DataBean data) {
        super.setData(data);
        GlideApp.with(getContext())
                .asBitmap()
                .load(data.getHeadimg())
                .placeholder(R.mipmap.ic_empty)
                .into(imageImg);
        textName.setText(data.getName());
        textDes.setText(data.getContent());
        ratingbar_pingfeng.setRating(data.getStar());
        for (int i = 0; i < imgIdArr.length; i++) {
            imageViews[i].setVisibility(View.GONE);
        }
        imageViews[0].setVisibility(View.VISIBLE);
        GlideApp.with(getContext())
                .asBitmap()
                .load(data.getImg())
                .placeholder(R.mipmap.ic_empty)
                .into(imageViews[0]);
        List<OrderGeteeva.DataBean.GoodsBean> goodsBeanList = data.getGoods();
        if (goodsBeanList.size()>0){
            viewGood.setVisibility(View.VISIBLE);
            GlideApp.with(getContext())
                    .asBitmap()
                    .load(goodsBeanList.get(0).getImg())
                    .placeholder(R.mipmap.ic_empty)
                    .into(imageGood);
            textGoodName.setText(goodsBeanList.get(0).getTitle());
            textPrice.setText("¥"+goodsBeanList.get(0).getPrice());
        }else {
            viewGood.setVisibility(View.GONE);
        }
        textDate.setText(data.getCreate_time());
    }
    
}
