package com.sxmoc.bq.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.sxmoc.bq.R;
import com.sxmoc.bq.model.IndexFindindex;
import com.sxmoc.bq.util.GlideApp;

/**
 * des： banner image
 * author： ZhangJieBo
 * date： 2017/7/7 0007 上午 9:46
 */
public class FaXianeBannerHolderView implements Holder<IndexFindindex.BannerBean> {

    private ImageView imageImg;
//    private TextView textTitle;

    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_faxain, null);
        imageImg = view.findViewById(R.id.imageImg);
//        textTitle = view.findViewById(R.id.textTitle);
        return view;
    }

    @Override
    public void UpdateUI(final Context context, int position, final IndexFindindex.BannerBean data) {

        GlideApp.with(context)
                .asBitmap()
                .load(data.getImg())
                .placeholder(R.mipmap.ic_empty)
                .into(imageImg);
//        textTitle.setText(data.getCode());
    }
}