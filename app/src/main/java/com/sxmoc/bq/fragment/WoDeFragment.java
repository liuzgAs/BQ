package com.sxmoc.bq.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxmoc.bq.R;
import com.sxmoc.bq.activity.CeShiLSActivity;
import com.sxmoc.bq.activity.GeRenXXActivity;
import com.sxmoc.bq.activity.GongGaoActivity;
import com.sxmoc.bq.activity.GuanLiYHKActivity;
import com.sxmoc.bq.activity.HeHuoRenActivity;
import com.sxmoc.bq.activity.SheZhiActivity;
import com.sxmoc.bq.activity.ShouYiMxActivity;
import com.sxmoc.bq.activity.WoDeDDActivity;
import com.sxmoc.bq.activity.WoDeGXActivity;
import com.sxmoc.bq.activity.WoDeSYActivity;
import com.sxmoc.bq.activity.ZhuanRangBaoGaoActivity;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseFragment;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.UserBuyerindex;
import com.sxmoc.bq.model.UserShare;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GlideApp;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;
import com.sxmoc.bq.util.ScreenUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class WoDeFragment extends ZjbBaseFragment implements View.OnClickListener {


    private View mInflate;
    private View viewBar;
    private ImageView imageHead;
    private TextView textName;
    private TextView textBlance;
    private TextView textBaoGaoNum;
    private TextView textGradeName;
    private boolean isShare = false;
    private BroadcastReceiver reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BroadcastCode.USERINFO:
                    initData();
                    break;
                case Constant.BroadcastCode.WX_SHARE:
                    if (isShare) {
                        MyDialog.showTipDialog(getActivity(), "分享成功");
                        isShare = false;
                    }
                    break;
                case Constant.BroadcastCode.WX_SHARE_FAIL:
                    if (isShare) {
                        MyDialog.showTipDialog(getActivity(), "取消分享");
                        isShare = false;
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private UserBuyerindex userBuyerindex;

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
        viewBar.setPadding(0, ScreenUtils.getStatusBarHeight(getActivity()), 0, 0);
        GlideApp.with(getActivity())
                .asBitmap()
                .load(userInfo.getHeadImg())
                .placeholder(R.mipmap.ic_empty)
                .into(imageHead);
        textName.setText(userInfo.getUserName());
    }

    @Override
    protected void setListeners() {
        mInflate.findViewById(R.id.viewJianCeJL).setOnClickListener(this);
        mInflate.findViewById(R.id.imageSheZhi).setOnClickListener(this);
        mInflate.findViewById(R.id.viewWoDeDD).setOnClickListener(this);
        mInflate.findViewById(R.id.viewGeRenXX).setOnClickListener(this);
        mInflate.findViewById(R.id.viewHeHuoRen).setOnClickListener(this);
        mInflate.findViewById(R.id.viewWoDeSY).setOnClickListener(this);
        mInflate.findViewById(R.id.imageXiaoXi).setOnClickListener(this);
        mInflate.findViewById(R.id.viewWoDeGX).setOnClickListener(this);
        mInflate.findViewById(R.id.viewYinHangKa).setOnClickListener(this);
        mInflate.findViewById(R.id.viewZhuanRangBaoGao).setOnClickListener(this);
        mInflate.findViewById(R.id.viewFenXiang).setOnClickListener(this);
        textBlance.setOnClickListener(this);
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
            params.put("tokenTime", tokenTime);
        }
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.viewFenXiang:
                fenXiang();
                break;
            case R.id.viewZhuanRangBaoGao:
                intent.setClass(getActivity(), ZhuanRangBaoGaoActivity.class);
                intent.putExtra(Constant.IntentKey.PHONE,userBuyerindex.getMobile());
                startActivity(intent);
                break;
            case R.id.viewYinHangKa:
                intent.setClass(getActivity(), GuanLiYHKActivity.class);
                startActivity(intent);
                break;
            case R.id.viewWoDeGX:
                intent.setClass(getActivity(), WoDeGXActivity.class);
                startActivity(intent);
                break;
            case R.id.imageXiaoXi:
                intent.setClass(getActivity(), GongGaoActivity.class);
                startActivity(intent);
                break;
            case R.id.textBlance:
                intent.setClass(getActivity(), ShouYiMxActivity.class);
                startActivity(intent);
                break;
            case R.id.viewWoDeSY:
                intent.putExtra(Constant.IntentKey.VALUE, userBuyerindex.getMoney());
                intent.setClass(getActivity(), WoDeSYActivity.class);
                startActivity(intent);
                break;
            case R.id.viewHeHuoRen:
                intent.setClass(getActivity(), HeHuoRenActivity.class);
                startActivity(intent);
                break;
            case R.id.viewGeRenXX:
                intent.setClass(getActivity(), GeRenXXActivity.class);
                startActivity(intent);
                break;
            case R.id.viewWoDeDD:
                intent.setClass(getActivity(), WoDeDDActivity.class);
                startActivity(intent);
                break;
            case R.id.viewJianCeJL:
                intent.setClass(getActivity(), CeShiLSActivity.class);
                startActivity(intent);
                break;
            case R.id.imageSheZhi:
                intent.setClass(getActivity(), SheZhiActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getFenXiangOkObject() {
        String url = Constant.HOST + Constant.Url.USER_SHARE;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime",tokenTime);
        }
        return new OkObject(params, url);
    }

    private IWXAPI api = WXAPIFactory.createWXAPI(getActivity(), Constant.WXAPPID, true);

    /**
     * 分享推荐
     */
    private void fenXiang() {
        showLoadingDialog();
        ApiClient.post(getActivity(), getFenXiangOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("WoDeFragment--onSuccess",s+ "");
                try {
                    UserShare userShare = GsonUtils.parseJSON(s, UserShare.class);
                    if (userShare.getStatus()==1){
                        int can_share = userShare.getCan_share();
                        if (can_share==1){
                            isShare = true;
                            MyDialog.share01(getActivity(), api, userShare.getShare_url(),getActivity().getResources().getString(R.string.app_name));

                        }else {
                            MyDialog.showTipDialog(getActivity(),userShare.getInfo());
                        }
                    }else if (userShare.getStatus()==3){
                        MyDialog.showReLoginDialog(getActivity());
                    }else {
                        Toast.makeText(getActivity(), userShare.getInfo(), Toast.LENGTH_SHORT).show();
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


    @Override
    public void onResume() {
        super.onResume();
        showLoadingDialog();
        ApiClient.post(getActivity(), getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("WoDeFragment--onSuccess", s + "");
                try {
                    userBuyerindex = GsonUtils.parseJSON(s, UserBuyerindex.class);
                    if (userBuyerindex.getStatus() == 1) {
                        GlideApp.with(getActivity())
                                .asBitmap()
                                .load(userBuyerindex.getHeadimg())
                                .placeholder(R.mipmap.ic_empty)
                                .into(imageHead);
                        textName.setText(userBuyerindex.getNickname());
                        textBaoGaoNum.setText(userBuyerindex.getReport_num());
                        textBlance.setText(userBuyerindex.getMoney() + "");
                        textGradeName.setText(userBuyerindex.getGrade_name());
                    } else if (userBuyerindex.getStatus() == 3) {
                        MyDialog.showReLoginDialog(getActivity());
                    } else {
                        Toast.makeText(getActivity(), userBuyerindex.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BroadcastCode.USERINFO);
        filter.addAction(Constant.BroadcastCode.WX_SHARE);
        filter.addAction(Constant.BroadcastCode.WX_SHARE_FAIL);
        getActivity().registerReceiver(reciver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(reciver);
    }
}