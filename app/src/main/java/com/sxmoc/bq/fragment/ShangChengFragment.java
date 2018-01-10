package com.sxmoc.bq.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sxmoc.bq.R;
import com.sxmoc.bq.base.ZjbBaseFragment;
import com.sxmoc.bq.util.BannerSettingUtil;
import com.sxmoc.bq.util.DpUtils;
import com.sxmoc.bq.util.ScreenUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShangChengFragment extends ZjbBaseFragment {


    private View mInflate;
    private View viewBar;
    private ViewPager id_viewpager;
    private View[] zhiShiQiView = new View[3];
    private int[] zhiShiQiID = new int[]{
            R.id.zhiShiQi01,
            R.id.zhiShiQi02,
            R.id.zhiShiQi03,
    };

    public ShangChengFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_shang_cheng, container, false);
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
        viewBar = mInflate.findViewById(R.id.viewBar);
        id_viewpager = mInflate.findViewById(R.id.id_viewpager);
        for (int i = 0; i < zhiShiQiView.length; i++) {
            zhiShiQiView[i] = mInflate.findViewById(zhiShiQiID[i]);
        }
    }

    @Override
    protected void initViews() {
        viewBar.setPadding(0, ScreenUtils.getStatusBarHeight(getActivity()), 0, 0);
        new BannerSettingUtil(id_viewpager, (int) DpUtils.convertDpToPixel(13, getActivity()), false).set();
        id_viewpager.setAdapter(new MyPageAdapter(getChildFragmentManager()));
        id_viewpager.setCurrentItem(50);
        setZhiShiQi(0);
    }

    @Override
    protected void setListeners() {
        id_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setZhiShiQi(position%3);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    private void setZhiShiQi(int index){
        for (int i = 0; i < zhiShiQiView.length; i++) {
            zhiShiQiView[i].setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.zhiShiQi));
        }
        zhiShiQiView[index].setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.white));
    }

    public class MyPageAdapter extends FragmentPagerAdapter {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return new ShangPinFragment();
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE / 2;
        }
    }
}
