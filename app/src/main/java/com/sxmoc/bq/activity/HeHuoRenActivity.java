package com.sxmoc.bq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sxmoc.bq.R;
import com.sxmoc.bq.base.ZjbBaseActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.fragment.ShenQIngSYHHRFragment;
import com.sxmoc.bq.fragment.ShengJiSYHHRFragment;
import com.sxmoc.bq.model.UserApplybefore;

import java.util.ArrayList;
import java.util.List;

public class HeHuoRenActivity extends ZjbBaseActivity implements View.OnClickListener {
    private TabLayout tablayout;
    public ViewPager viewPager;
    List<String> list = new ArrayList<>();
    private UserApplybefore userApplybefore;
    private TextView textViewRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_he_huo_ren);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        userApplybefore = (UserApplybefore) intent.getSerializableExtra(Constant.IntentKey.BEAN);
    }

    @Override
    protected void findID() {
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        textViewRight = (TextView) findViewById(R.id.textViewRight);
    }

    @Override
    protected void initViews() {
        textViewRight.setText("合伙人规则");
        ((TextView) findViewById(R.id.textViewTitle)).setText("事业合伙人");
        list.add("申请事业合伙人");
        list.add("升级事业合伙人");
        viewPager.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
        tablayout.setupWithViewPager(viewPager);
        tablayout.removeAllTabs();
        for (int i = 0; i < list.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_tablayout, null);
            TextView textTitle = view.findViewById(R.id.textTitle);
            textTitle.setText(list.get(i));
            if (i == 0) {
                tablayout.addTab(tablayout.newTab().setCustomView(view), true);
            } else {
                tablayout.addTab(tablayout.newTab().setCustomView(view), false);
            }
        }
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        textViewRight.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageBack:
                finish();
                break;
            case R.id.textViewRight:
                Intent intent = new Intent();
                intent.setClass(this, WebActivity.class);
                intent.putExtra(Constant.IntentKey.TITLE, "合伙人规则");
                intent.putExtra(Constant.IntentKey.URL, Constant.Url.PARTNERDES);
                startActivity(intent);
                break;
            default:

                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    class MyPageAdapter extends FragmentPagerAdapter {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ShenQIngSYHHRFragment(userApplybefore);
                case 1:
                    return new ShengJiSYHHRFragment();

                default:
                    return new ShenQIngSYHHRFragment(userApplybefore);
            }

        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}
