package com.sxmoc.bq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sxmoc.bq.R;
import com.sxmoc.bq.base.ZjbBaseNotLeftActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.util.LogUtil;

import es.voghdev.pdfviewpager.library.PDFViewPager;

public class PdfActivity extends ZjbBaseNotLeftActivity implements View.OnClickListener {


    private RelativeLayout viewZhu;
    private String pdf;
    private String title;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        pdf = intent.getStringExtra(Constant.IntentKey.VALUE);
        title = intent.getStringExtra(Constant.IntentKey.TITLE);
        type = intent.getIntExtra(Constant.IntentKey.TYPE, 0);
        LogUtil.LogShitou("PdfActivity--initIntent", "type" + type);
    }

    @Override
    protected void findID() {
        viewZhu = (RelativeLayout) findViewById(R.id.viewZhu);
    }

    @Override
    protected void initViews() {
        PDFViewPager pdfViewPager = new PDFViewPager(this, pdf);
        viewZhu.addView(pdfViewPager, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ((TextView) findViewById(R.id.textViewTitle)).setText(title);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageBack:
                if (type == 1) {
                    Intent intent = new Intent();
                    intent.setClass(this, CeShiLSActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (type == 1) {
            Intent intent = new Intent();
            intent.setClass(this, CeShiLSActivity.class);
            startActivity(intent);
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}
