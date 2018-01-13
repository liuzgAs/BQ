package com.sxmoc.bq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.sxmoc.bq.R;
import com.sxmoc.bq.base.ToLoginActivity;
import com.sxmoc.bq.base.ZjbBaseActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.customview.TwoBtnDialog;
import com.sxmoc.bq.util.ACache;
import com.sxmoc.bq.util.DataCleanManager;
import com.sxmoc.bq.util.DpUtils;
import com.sxmoc.bq.util.GlideApp;
import com.sxmoc.bq.util.VersionUtils;


/**
 * 设置
 *
 * @author Administrator
 */
public class SheZhiActivity extends ZjbBaseActivity implements View.OnClickListener {

    private TextView textHuanCun;
    private TextView textBanben;
    private ImageView imageLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_she_zhi);
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
        textHuanCun = (TextView) findViewById(R.id.textHuanCun);
        textBanben = (TextView) findViewById(R.id.textBanben);
        imageLogo = (ImageView) findViewById(R.id.imageLogo);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("设置");
        textHuanCun.setText(getSize());
        textBanben.setText("v" + VersionUtils.getCurrVersionName(this));

        GlideApp.with(SheZhiActivity.this)
                .asBitmap()
                .centerCrop()
                .transform(new RoundedCorners((int) DpUtils.convertDpToPixel(16,SheZhiActivity.this)))
                .load(R.mipmap.logo)
                .into(imageLogo);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.viewYiJianFanKui).setOnClickListener(this);
        findViewById(R.id.viewChangJianWenTi).setOnClickListener(this);
        findViewById(R.id.viewXiuGaiMM).setOnClickListener(this);
        findViewById(R.id.viewHuanCun).setOnClickListener(this);
        findViewById(R.id.viewAbout).setOnClickListener(this);
        findViewById(R.id.btnExit).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btnExit:
                final TwoBtnDialog twoBtnDialog = new TwoBtnDialog(this, "您确定要退出登录吗？", "是", "否");
                twoBtnDialog.setClicklistener(new TwoBtnDialog.ClickListenerInterface() {
                            @Override
                            public void doConfirm() {
                                twoBtnDialog.dismiss();
                                ACache aCache = ACache.get(SheZhiActivity.this, Constant.Acache.APP);
                                aCache.clear();
                                Constant.changeControl++;
                                finish();
                            }

                            @Override
                            public void doCancel() {
                                twoBtnDialog.dismiss();
                            }
                        });
                twoBtnDialog.show();
                break;
            case R.id.viewAbout:
                intent.setClass(SheZhiActivity.this,WebActivity.class);
                intent.putExtra(Constant.IntentKey.TITLE,"关于我们");
                intent.putExtra(Constant.IntentKey.URL,Constant.Url.ABOUT);
                startActivity(intent);
                break;
            case R.id.viewHuanCun:
                DataCleanManager.clearAllCache(this);
                textHuanCun.setText(getSize());
                Toast.makeText(this, "缓存清除完毕", Toast.LENGTH_SHORT).show();
                break;
            case R.id.viewXiuGaiMM:
                if (isLogin) {
                    intent.setClass(this, XiuGaiMMActivity.class);
                    startActivity(intent);
                } else {
                    ToLoginActivity.toLoginActivity(this);
                }
                break;
            case R.id.viewChangJianWenTi:
                intent.setClass(this, ChangJianWenTiActivity.class);
                startActivity(intent);
                break;
            case R.id.viewYiJianFanKui:
                intent.setClass(this, YiJianFKActivity.class);
                startActivity(intent);
                break;
            case R.id.imageBack:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * -------------获取缓存大小-----------------
     */
    private String getSize() {
        String totalCacheSize = null;
        try {
            totalCacheSize = DataCleanManager.getTotalCacheSize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalCacheSize;
    }
}
