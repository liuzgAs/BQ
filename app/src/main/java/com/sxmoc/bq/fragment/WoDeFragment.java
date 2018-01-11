package com.sxmoc.bq.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxmoc.bq.R;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseFragment;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.UserBuyerindex;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GlideApp;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;
import com.sxmoc.bq.util.ScreenUtils;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class WoDeFragment extends ZjbBaseFragment {


    private View mInflate;
    private View viewBar;
    private ImageView imageHead;
    private TextView textName;
    private TextView textBlance;
    private TextView textBaoGaoNum;
    private TextView textGradeName;

    public WoDeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_wo_de, container, false);
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
        imageHead = mInflate.findViewById(R.id.imageHead);
        textName = mInflate.findViewById(R.id.textName);
        textBlance = mInflate.findViewById(R.id.textBlance);
        textBaoGaoNum = mInflate.findViewById(R.id.textBaoGaoNum);
        textGradeName = mInflate.findViewById(R.id.textGradeName);
    }

    @Override
    protected void initViews() {
        viewBar.setPadding(0, ScreenUtils.getStatusBarHeight(getActivity()),0,0);
        GlideApp.with(getActivity())
                .asBitmap()
                .load(userInfo.getHeadImg())
                .placeholder(R.mipmap.ic_empty)
                .into(imageHead);
        textName.setText(userInfo.getUserName());
    }

    @Override
    protected void setListeners() {

    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.USER_BUYERINDEX;
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
        ApiClient.post(getActivity(), getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("WoDeFragment--onSuccess",s+ "");
                try {
                    UserBuyerindex userBuyerindex = GsonUtils.parseJSON(s, UserBuyerindex.class);
                    if (userBuyerindex.getStatus()==1){
                        GlideApp.with(getActivity())
                                .asBitmap()
                                .load(userBuyerindex.getHeadimg())
                                .placeholder(R.mipmap.ic_empty)
                                .into(imageHead);
                        textName.setText(userBuyerindex.getNickname());
                        textBaoGaoNum.setText(userBuyerindex.getReport_num());
                        textBlance.setText(userBuyerindex.getMoney()+"");
                        textGradeName.setText(userBuyerindex.getGrade_name());
                    }else if (userBuyerindex.getStatus()==3){
                        MyDialog.showReLoginDialog(getActivity());
                    }else {
                        Toast.makeText(getActivity(), userBuyerindex.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(),"数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}