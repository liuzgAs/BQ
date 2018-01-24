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
import android.widget.Toast;

import com.sxmoc.bq.R;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseFragment;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.model.GoodsIndex;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.BannerSettingUtil;
import com.sxmoc.bq.util.DpUtils;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;
import com.sxmoc.bq.util.ScreenUtils;

import java.util.HashMap;
import java.util.List;

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
        viewBar.setPadding(0, ScreenUtils.getStatusBarHeight(mContext), 0, 0);
        new BannerSettingUtil(id_viewpager, (int) DpUtils.convertDpToPixel(13, mContext), false).set();
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

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.GOODS_INDEX;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime",tokenTime);
        }
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(mContext, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("ShangChengFragment--onSuccess",s+ "");
                try {
                    GoodsIndex goodsIndex = GsonUtils.parseJSON(s, GoodsIndex.class);
                    if (goodsIndex.getStatus()==1){
                        List<GoodsIndex.DataBean> dataBeanList = goodsIndex.getData();
                        id_viewpager.setAdapter(new MyPageAdapter(getChildFragmentManager(),dataBeanList));
                        id_viewpager.setCurrentItem(50);
                    }else if (goodsIndex.getStatus()==3){
                        MyDialog.showReLoginDialog(mContext);
                    }else {
                        Toast.makeText(mContext, goodsIndex.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(mContext,"数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(mContext, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setZhiShiQi(int index){
        for (int i = 0; i < zhiShiQiView.length; i++) {
            zhiShiQiView[i].setBackgroundColor(ContextCompat.getColor(mContext,R.color.zhiShiQi));
        }
        zhiShiQiView[index].setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
    }

    public class MyPageAdapter extends FragmentPagerAdapter {
        private List<GoodsIndex.DataBean> dataBeanList;
        public MyPageAdapter(FragmentManager fm,List<GoodsIndex.DataBean> dataBeanList) {
            super(fm);
            this.dataBeanList =dataBeanList;
        }

        @Override
        public Fragment getItem(int position) {
            int i = position % dataBeanList.size();
            return new ShangPinFragment(dataBeanList.get(i));
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE / 2;
        }
    }
}
