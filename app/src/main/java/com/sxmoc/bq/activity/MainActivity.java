package com.sxmoc.bq.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.sxmoc.bq.R;
import com.sxmoc.bq.base.ZjbBaseNotLeftActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.fragment.CeYiCeFragment;
import com.sxmoc.bq.fragment.FaXianFragment;
import com.sxmoc.bq.fragment.ShangChengFragment;
import com.sxmoc.bq.fragment.WoDeFragment;
import com.sxmoc.bq.util.LogUtil;
import com.sxmoc.bq.util.UpgradeUtils;


public class MainActivity extends ZjbBaseNotLeftActivity {
    private String[] tabsItem = new String[4];
    private Class[] fragment = new Class[]{
            ShangChengFragment.class,
            CeYiCeFragment.class,
            FaXianFragment.class,
            WoDeFragment.class,
    };
    private int[] imgRes = new int[]{
            R.mipmap.item_01,
            R.mipmap.item_02,
            R.mipmap.item_03,
            R.mipmap.item_04,
    };
    public FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //检查更新
        UpgradeUtils.checkUpgrade(MainActivity.this, Constant.HOST + Constant.Url.INDEX_VERSION);
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
        mTabHost = findViewById(R.id.tabHost);
    }

    @Override
    protected void initViews() {
        tabsItem[0] = "0";
        tabsItem[1] = "1";
        tabsItem[2] = "2";
        tabsItem[3] = "3";
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtab);
        for (int i = 0; i < tabsItem.length; i++) {
            View inflate = getLayoutInflater().inflate(R.layout.tabs_item, null);
            View tabsCircle = inflate.findViewById(R.id.tabsCircle);
            ImageView tabsImg = inflate.findViewById(R.id.tabs_img);
//            if (i==1||i==2){
//                tabsImg.setPadding(0,(int) DpUtils.convertDpToPixel(1f,this),0,(int) DpUtils.convertDpToPixel(1f,this));
//            }
            tabsImg.setImageResource(imgRes[i]);
            mTabHost.addTab(mTabHost.newTabSpec(tabsItem[i]).setIndicator(inflate), fragment[i], null);
        }
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                LogUtil.LogShitou("MainActivity--onTabChanged", "" + s);
                switch (s) {
                    case "0":
                        mTabHost.setBackgroundResource(R.mipmap.shangcheng_bg);
                        break;
                    case "1":
                        mTabHost.setBackgroundResource(R.drawable.top_bottom_jian_bian);
                        break;
                    case "2":
                        mTabHost.setBackgroundResource(R.drawable.top_bottom_jian_bian);
                        break;
                    case "3":
                        mTabHost.setBackgroundResource(R.drawable.top_bottom_jian_bian);
                        break;
                    default:
                        mTabHost.setBackgroundResource(R.drawable.top_bottom_jian_bian);
                        break;
                }
            }
        });
    }

    @Override
    protected void setListeners() {
    }

    @Override
    protected void initData() {

    }

}
