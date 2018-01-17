package com.sxmoc.bq.holder;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxmoc.bq.R;
import com.sxmoc.bq.activity.CeShiLSActivity;
import com.sxmoc.bq.activity.ChanPinXQActivity;
import com.sxmoc.bq.activity.WebActivity;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.customview.TwoBtnDialog;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.ProductQueryhistory;
import com.sxmoc.bq.model.TesterGetreport;
import com.sxmoc.bq.model.UserBuyerindex;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class CeShiLSViewHolder extends BaseViewHolder<ProductQueryhistory.DataBean> {

    private final Button btnCaoZuo;
    private final TextView textName;
    private final TextView textDate;
    private ProductQueryhistory.DataBean data;

    public CeShiLSViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        btnCaoZuo = $(R.id.btnCaoZuo);
        textName = $(R.id.textName);
        textDate = $(R.id.textDate);
        btnCaoZuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.getStatus() == 1) {
                    chaKanBaoGao();
                } else {
                    shengChengBaoGao();
                }
            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getBaoGaoOkObject() {
        String url = Constant.HOST + Constant.Url.USER_BUYERINDEX;
        HashMap<String, String> params = new HashMap<>();
        if (((CeShiLSActivity) getContext()).isLogin) {
            params.put("uid", ((CeShiLSActivity) getContext()).userInfo.getUid());
            params.put("tokenTime", ((CeShiLSActivity) getContext()).tokenTime);
        }
        return new OkObject(params, url);
    }


    /**
     * 生成报告
     */
    private void shengChengBaoGao() {
        ((CeShiLSActivity) getContext()).showLoadingDialog();
        ApiClient.post(getContext(), getBaoGaoOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                ((CeShiLSActivity) getContext()).cancelLoadingDialog();
                LogUtil.LogShitou("CeYiCeFragment--onSuccess", s + "");
                try {
                    final UserBuyerindex userBuyerindex = GsonUtils.parseJSON(s, UserBuyerindex.class);
                    if (userBuyerindex.getStatus() == 1) {
                        String report_num = userBuyerindex.getReport_num();
                        if (Integer.parseInt(report_num) > 0) {
                            chaKanBaoGao();
                        } else {
                            final TwoBtnDialog twoBtnDialog = new TwoBtnDialog(getContext(), "您已没有多余的报告可使用", "购买", "取消");
                            twoBtnDialog.setClicklistener(new TwoBtnDialog.ClickListenerInterface() {
                                @Override
                                public void doConfirm() {
                                    twoBtnDialog.dismiss();
                                    Intent intent = new Intent();
                                    intent.putExtra(Constant.IntentKey.ID, userBuyerindex.getReport_id());
                                    intent.setClass(getContext(), ChanPinXQActivity.class);
                                    getContext().startActivity(intent);
                                }

                                @Override
                                public void doCancel() {
                                    twoBtnDialog.dismiss();
                                }
                            });
                            twoBtnDialog.show();
                        }
                    } else if (userBuyerindex.getStatus() == 3) {
                        MyDialog.showReLoginDialog(getContext());
                    } else {
                        Toast.makeText(getContext(), userBuyerindex.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                ((CeShiLSActivity) getContext()).cancelLoadingDialog();
                Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setData(ProductQueryhistory.DataBean data) {
        super.setData(data);
        this.data = data;
        if (data.getStatus() == 1) {
            btnCaoZuo.setText("查看报告");
            btnCaoZuo.setBackgroundResource(R.drawable.shape_basic01_1dp_25dp);
        } else {
            btnCaoZuo.setText("生成报告");
            btnCaoZuo.setBackgroundResource(R.drawable.shape_red01_1dp_25dp);
        }
        textName.setText(data.getName());
        textDate.setText(data.getCreate_time());
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getShengChengOkObject() {
        String url = Constant.HOST + Constant.Url.TESTER_GETREPORT;
        HashMap<String, String> params = new HashMap<>();
        if (((CeShiLSActivity) getContext()).isLogin) {
            params.put("uid", ((CeShiLSActivity) getContext()).userInfo.getUid());
            params.put("tokenTime",((CeShiLSActivity) getContext()).tokenTime);
        }
        params.put("bid",String.valueOf(data.getBid()));
        return new OkObject(params, url);
    }

    /**
     * 查看报告
     */
    private void chaKanBaoGao() {
        ((CeShiLSActivity) getContext()).showLoadingDialog();
        ApiClient.post(getContext(), getShengChengOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                ((CeShiLSActivity) getContext()).cancelLoadingDialog();
                LogUtil.LogShitou("CeShiLSViewHolder--onSuccess",s+ "");
                try {
                    TesterGetreport testerGetreport = GsonUtils.parseJSON(s, TesterGetreport.class);
                    if (testerGetreport.getStatus()==1){
                        Intent intent = new Intent();
                        intent.setClass(getContext(), WebActivity.class);
                        intent.putExtra(Constant.IntentKey.TITLE, data.getName()+"的报告详情");
                        intent.putExtra(Constant.IntentKey.URL, testerGetreport.getData_url());
                        getContext().startActivity(intent);
                    }else if (testerGetreport.getStatus()==3){
                        MyDialog.showReLoginDialog(getContext());
                    }else {
                        Toast.makeText(getContext(), testerGetreport.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(),"数据出错", Toast.LENGTH_SHORT).show();
                }
            }
        
            @Override
            public void onError() {
                ((CeShiLSActivity) getContext()).cancelLoadingDialog();
                Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
