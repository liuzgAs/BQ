package com.sxmoc.bq.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.sxmoc.bq.R;
import com.sxmoc.bq.model.GoodsInfo;
import com.sxmoc.bq.util.GlideApp;

public class BannerHolderView implements Holder<GoodsInfo.BannerBean> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, GoodsInfo.BannerBean data) {
        GlideApp.with(context)
                .load(data.getImg())
                .placeholder(R.mipmap.ic_empty)
                .into(imageView);
    }
}