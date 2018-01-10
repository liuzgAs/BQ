package com.sxmoc.bq.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxmoc.bq.R;
import com.sxmoc.bq.activity.ChanPinXQActivity;
import com.sxmoc.bq.base.ZjbBaseFragment;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.model.GoodsIndex;
import com.sxmoc.bq.util.GlideApp;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShangPinFragment extends ZjbBaseFragment implements View.OnClickListener {


    private View mInflate;
    private TextView textPrice;
    private GoodsIndex.DataBean dataBean;
    private ImageView imageImg;
    private TextView textTitle;

    public ShangPinFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ShangPinFragment(GoodsIndex.DataBean dataBean) {
        this.dataBean = dataBean;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_shang_pin, container, false);
            init();
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) mInflate.getParent();
        if (parent != null) {
            parent.removeView(mInflate);
        }
        return mInflate;
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void findID() {
        textPrice = mInflate.findViewById(R.id.textPrice);
        imageImg = mInflate.findViewById(R.id.imageImg);
        textTitle = mInflate.findViewById(R.id.textTitle);
    }

    @Override
    protected void initViews() {
        String price = dataBean.getPrice();
        SpannableString span = new SpannableString("¥"+price);
        span.setSpan(new RelativeSizeSpan(0.6f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textPrice.setText(span);
        GlideApp.with(getActivity())
                .asBitmap()
                .load(dataBean.getCover())
                .placeholder(R.mipmap.ic_empty)
                .into(imageImg);
        textTitle.setText(dataBean.getTitle());
    }

    @Override
    protected void setListeners() {
        mInflate.findViewById(R.id.viewShangPin).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewShangPin:
                Intent intent = new Intent();
                intent.putExtra(Constant.IntentKey.ID,dataBean.getId());
                intent.setClass(getActivity(), ChanPinXQActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
