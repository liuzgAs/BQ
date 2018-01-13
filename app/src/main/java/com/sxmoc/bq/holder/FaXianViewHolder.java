package com.sxmoc.bq.holder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxmoc.bq.R;
import com.sxmoc.bq.model.IndexFindindex;
import com.sxmoc.bq.util.GlideApp;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class FaXianViewHolder extends BaseViewHolder<IndexFindindex.DataBean> {

    private final ImageView imageImg;
    private final TextView textTitle;
    private final TextView textDes;

    public FaXianViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        imageImg = $(R.id.imageImg);
        textTitle = $(R.id.textTitle);
        textDes = $(R.id.textDes);
    }

    @Override
    public void setData(IndexFindindex.DataBean data) {
        super.setData(data);
        GlideApp.with(getContext())
                .asBitmap()
                .load(data.getImg())
                .placeholder(R.mipmap.ic_empty)
                .into(imageImg);
        textTitle.setText(data.getTitle());
        textDes.setText(data.getDes());
    }
    
}
