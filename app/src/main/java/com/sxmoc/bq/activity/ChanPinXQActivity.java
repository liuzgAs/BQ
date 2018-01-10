package com.sxmoc.bq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.sxmoc.bq.R;
import com.sxmoc.bq.base.ZjbBaseActivity;
import com.sxmoc.bq.customview.ScrollViewWithListener;
import com.sxmoc.bq.holder.BannerHolderView;

import java.util.ArrayList;
import java.util.List;

public class ChanPinXQActivity extends ZjbBaseActivity implements View.OnClickListener {

    private ConvenientBanner banner;
    private TextView textZhiShiQi;
    private ScrollViewWithListener scrollView;
    private View viewBar;
    private TextView textViewTitle;
    private ImageView imageBack;
    private List<Integer> imgList = new ArrayList<>();
    private TextView textPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chan_pin_xq);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void findID() {
        banner = (ConvenientBanner) findViewById(R.id.banner);
        textZhiShiQi = (TextView) findViewById(R.id.textZhiShiQi);
        scrollView = (ScrollViewWithListener) findViewById(R.id.scrollView);
        viewBar = findViewById(R.id.viewBar);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        imageBack = (ImageView) findViewById(R.id.imageBack);
        textPrice = (TextView) findViewById(R.id.textPrice);
    }

    @Override
    protected void initViews() {
        viewBar.getBackground().mutate().setAlpha(0);
        imageBack.getBackground().mutate().setAlpha(255);
        textViewTitle.setAlpha(0);
        imgList.add(R.mipmap.banner);
        imgList.add(R.mipmap.banner);
        imgList.add(R.mipmap.banner);
        imgList.add(R.mipmap.banner);
        imgList.add(R.mipmap.banner);
        textZhiShiQi.setText("1/" + imgList.size());
        banner.setScrollDuration(1000);
        banner.startTurning(3000);
        banner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new BannerHolderView();
            }
        }, imgList);
        SpannableString span = new SpannableString("¥2880");
        span.setSpan(new RelativeSizeSpan(0.6f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textPrice.setText(span);
    }

    @Override
    protected void setListeners() {
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                textZhiShiQi.setText((position + 1) + "/" + imgList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        scrollView.setOnScrollChangedListener(new ScrollViewWithListener.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int scrollY) {
                float guangGaoHeight = getResources().getDimension(R.dimen.bannerHeight);
                if (scrollY <= guangGaoHeight - viewBar.getHeight() && scrollY >= 0) {
                    int baiFenBi = (int) ((double) scrollY / (double) (guangGaoHeight - viewBar.getHeight()) * 255);
                    viewBar.getBackground().mutate().setAlpha(baiFenBi);
                    imageBack.getBackground().mutate().setAlpha(255 - baiFenBi);
                    textViewTitle.setAlpha((float) baiFenBi / 255f);
                } else {
                    viewBar.getBackground().mutate().setAlpha(255);
                    textViewTitle.setAlpha(1);
                }
            }
        });
        findViewById(R.id.textReserve).setOnClickListener(this);
        findViewById(R.id.imageBack).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.imageBack:
                finish();
                break;
            case R.id.textReserve:
//                intent.setClass(this, ConfirmOrderActivity.class);
//                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
