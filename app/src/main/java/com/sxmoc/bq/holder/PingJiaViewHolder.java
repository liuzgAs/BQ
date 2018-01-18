package com.sxmoc.bq.holder;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxmoc.bq.R;
import com.sxmoc.bq.activity.PingJiaActivity;
import com.sxmoc.bq.constant.Constant;
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
    private final View viewPingJia;
    private final View viewImgs;

    public PingJiaViewHolder(ViewGroup parent, @LayoutRes int res, int type) {
        super(parent, res);
        imageImg = $(R.id.imageImg);
        textName = $(R.id.textName);
        textDes = $(R.id.textDes);
        ratingbar_pingfeng = $(R.id.ratingbar_pingfeng);
        for (int i = 0; i < imgIdArr.length; i++) {
            imageViews[i] = $(imgIdArr[i]);
        }
        imageGood = $(R.id.imageGood);
        textGoodName = $(R.id.textGoodName);
        textPrice = $(R.id.textPrice);
        textDate = $(R.id.textDate);
        viewGood = $(R.id.viewGood);
        viewPingJia = $(R.id.viewPingJia);
        viewImgs = $(R.id.viewImgs);
        switch (type) {
            case 1:
                viewPingJia.setVisibility(View.GONE);
                ratingbar_pingfeng.setVisibility(View.VISIBLE);
                textDes.setVisibility(View.VISIBLE);
                viewImgs.setVisibility(View.VISIBLE);
                break;
            case 2:
                viewPingJia.setVisibility(View.VISIBLE);
                ratingbar_pingfeng.setVisibility(View.GONE);
                textDes.setVisibility(View.GONE);
                viewImgs.setVisibility(View.GONE);
                break;
            default:

                break;
        }
        viewPingJia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getContext(), PingJiaActivity.class);
                intent.putExtra(Constant.IntentKey.ID,data.getId());
                getContext().startActivity(intent);
            }
        });
    }
    OrderGeteeva.DataBean data;
    @Override
    public void setData(OrderGeteeva.DataBean data) {
        super.setData(data);
        this.data=data;
        GlideApp.with(getContext())
                .asBitmap()
                .load(data.getHeadimg())
                .placeholder(R.mipmap.ic_empty)
                .into(imageImg);
        textName.setText(data.getName());
        textDes.setText(data.getContent());
        ratingbar_pingfeng.setRating(data.getStar());
        if (TextUtils.isEmpty(data.getImg())){
            viewImgs.setVisibility(View.GONE);
        }else {
            viewImgs.setVisibility(View.VISIBLE);
            for (int i = 0; i < imgIdArr.length; i++) {
                imageViews[i].setVisibility(View.GONE);
            }
            imageViews[0].setVisibility(View.VISIBLE);
            GlideApp.with(getContext())
                    .asBitmap()
                    .load(data.getImg())
                    .placeholder(R.mipmap.ic_empty)
                    .into(imageViews[0]);
        }
        List<OrderGeteeva.DataBean.GoodsBean> goodsBeanList = data.getGoods();
        if (goodsBeanList.size() > 0) {
            viewGood.setVisibility(View.VISIBLE);
            GlideApp.with(getContext())
                    .asBitmap()
                    .load(goodsBeanList.get(0).getImg())
                    .placeholder(R.mipmap.ic_empty)
                    .into(imageGood);
            textGoodName.setText(goodsBeanList.get(0).getTitle());
            textPrice.setText("¥" + goodsBeanList.get(0).getPrice());
        } else {
            viewGood.setVisibility(View.GONE);
        }
        textDate.setText(data.getCreate_time());
    }

}
