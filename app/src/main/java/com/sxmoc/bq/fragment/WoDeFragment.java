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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxmoc.bq.R;
import com.sxmoc.bq.activity.BaoGaoMxActivity;
import com.sxmoc.bq.activity.CeShiLSActivity;
import com.sxmoc.bq.activity.ChanPinXQActivity;
import com.sxmoc.bq.activity.ChangJianWenTiActivity;
import com.sxmoc.bq.activity.GeRenXXActivity;
import com.sxmoc.bq.activity.GongGaoActivity;
import com.sxmoc.bq.activity.HeHuoRenActivity;
import com.sxmoc.bq.activity.PingJiaGLActivity;
import com.sxmoc.bq.activity.SheZhiActivity;
import com.sxmoc.bq.activity.ShouYiMxActivity;
import com.sxmoc.bq.activity.TiXianActivity;
import com.sxmoc.bq.activity.WoDeDDActivity;
import com.sxmoc.bq.activity.WoDeGXActivity;
import com.sxmoc.bq.activity.WoDeSYActivity;
import com.sxmoc.bq.activity.ZhuanRangBaoGaoActivity;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseFragment;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.customview.TwoBtnDialog;
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
                        MyDialog.showTipDialog(mContext, "分享成功");
                        isShare = false;
                    }
                    break;
                case Constant.BroadcastCode.WX_SHARE_FAIL:
                    if (isShare) {
                        MyDialog.showTipDialog(mContext, "取消分享");
                        isShare = false;
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private UserBuyerindex userBuyerindex;
    private Button btnGouMai;

    public WoDeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_wo_de, container, false);
            api = WXAPIFactory.createWXAPI(mContext, Constant.WXAPPID, true);
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
        btnGouMai = mInflate.findViewById(R.id.btnGouMai);
    }

    @Override
    protected void initViews() {
        viewBar.setPadding(0, ScreenUtils.getStatusBarHeight(mContext), 0, 0);
        GlideApp.with(mContext)
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
        btnGouMai.setOnClickListener(this);
        mInflate.findViewById(R.id.viewPingJiaGL).setOnClickListener(this);
        mInflate.findViewById(R.id.viewBaoGao).setOnClickListener(this);
        mInflate.findViewById(R.id.viewTiXian).setOnClickListener(this);
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
            case R.id.viewTiXian:
                intent.putExtra(Constant.IntentKey.PHONE,userBuyerindex.getMobile());
                intent.setClass(mContext, TiXianActivity.class);
                startActivity(intent);
                break;
            case R.id.viewBaoGao:
                intent.setClass(mContext, BaoGaoMxActivity.class);
                startActivity(intent);
                break;
            case R.id.viewPingJiaGL:
                intent.setClass(mContext, PingJiaGLActivity.class);
                startActivity(intent);
                break;
            case R.id.btnGouMai:
                if (userBuyerindex.getIs_share()==1){
                    intent.putExtra(Constant.IntentKey.ID, userBuyerindex.getGoods_id());
                    intent.setClass(getContext(), ChanPinXQActivity.class);
                    startActivity(intent);
                }else {
                    intent.putExtra(Constant.IntentKey.ID, userBuyerindex.getReport_id());
                    intent.setClass(mContext, ChanPinXQActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.viewFenXiang:
                fenXiang();
                break;
            case R.id.viewZhuanRangBaoGao:
                intent.setClass(mContext, ZhuanRangBaoGaoActivity.class);
                intent.putExtra(Constant.IntentKey.PHONE, userBuyerindex.getMobile());
                startActivity(intent);
                break;
            case R.id.viewYinHangKa:
                intent.setClass(mContext, ChangJianWenTiActivity.class);
                startActivity(intent);
                break;
            case R.id.viewWoDeGX:
                intent.setClass(mContext, WoDeGXActivity.class);
                startActivity(intent);
                break;
            case R.id.imageXiaoXi:
                intent.setClass(mContext, GongGaoActivity.class);
                startActivity(intent);
                break;
            case R.id.textBlance:
                intent.setClass(mContext, ShouYiMxActivity.class);
                startActivity(intent);
                break;
            case R.id.viewWoDeSY:
                intent.putExtra(Constant.IntentKey.PHONE, userBuyerindex.getMobile());
                intent.putExtra(Constant.IntentKey.VALUE, userBuyerindex.getMoney());
                intent.setClass(mContext, WoDeSYActivity.class);
                startActivity(intent);
                break;
            case R.id.viewHeHuoRen:
                intent.setClass(mContext, HeHuoRenActivity.class);
                startActivity(intent);
                break;
            case R.id.viewGeRenXX:
                intent.setClass(mContext, GeRenXXActivity.class);
                startActivity(intent);
                break;
            case R.id.viewWoDeDD:
                intent.setClass(mContext, WoDeDDActivity.class);
                startActivity(intent);
                break;
            case R.id.viewJianCeJL:
                intent.setClass(mContext, CeShiLSActivity.class);
                startActivity(intent);
                break;
            case R.id.imageSheZhi:
                intent.setClass(mContext, SheZhiActivity.class);
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
            params.put("tokenTime", tokenTime);
        }
        return new OkObject(params, url);
    }

    private IWXAPI api;

    /**
     * 分享推荐
     */
    private void fenXiang() {
        showLoadingDialog();
        ApiClient.post(mContext, getFenXiangOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("WoDeFragment--onSuccess", s + "");
                try {
                    final UserShare userShare = GsonUtils.parseJSON(s, UserShare.class);
                    if (userShare.getStatus() == 1) {
                        int can_share = userShare.getCan_share();
                        if (can_share == 1) {
                            isShare = true;
                            MyDialog.share01(mContext, api, userShare.getShare_url(), userShare.getTitle(),userShare.getContent());
                        } else {
                            final TwoBtnDialog twoBtnDialog = new TwoBtnDialog(mContext, userShare.getInfo(), "升级", "返回");
                            twoBtnDialog.setClicklistener(new TwoBtnDialog.ClickListenerInterface() {
                                @Override
                                public void doConfirm() {
                                    twoBtnDialog.dismiss();
                                    Intent intent = new Intent();
                                    intent.putExtra(Constant.IntentKey.ID, userShare.getGoods_id());
                                    intent.setClass(getContext(), ChanPinXQActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void doCancel() {
                                    twoBtnDialog.dismiss();
                                }
                            });
                            twoBtnDialog.show();
                        }
                    } else if (userShare.getStatus() == 3) {
                        MyDialog.showReLoginDialog(mContext);
                    } else {
                        Toast.makeText(mContext, userShare.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(mContext, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        showLoadingDialog();
        ApiClient.post(mContext, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("WoDeFragment--onSuccess", s + "");
                try {
                    userBuyerindex = GsonUtils.parseJSON(s, UserBuyerindex.class);
                    if (userBuyerindex.getStatus() == 1) {
                        GlideApp.with(mContext)
                                .asBitmap()
                                .load(userBuyerindex.getHeadimg())
                                .placeholder(R.mipmap.ic_empty)
                                .into(imageHead);
                        textName.setText(userBuyerindex.getNickname());
                        textBaoGaoNum.setText(userBuyerindex.getReport_num());
                        textBlance.setText(userBuyerindex.getMoney() + "");
                        textGradeName.setText(userBuyerindex.getGrade_name());
                        if (userBuyerindex.getIs_share()==1){
                            btnGouMai.setText("进入升级");
                        }else {
                            btnGouMai.setText("购买报告");
                        }
                    } else if (userBuyerindex.getStatus() == 3) {
                        MyDialog.showReLoginDialog(mContext);
                    } else {
                        Toast.makeText(mContext, userBuyerindex.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(mContext, "请求失败", Toast.LENGTH_SHORT).show();
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
        mContext.registerReceiver(reciver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext.unregisterReceiver(reciver);
    }
}