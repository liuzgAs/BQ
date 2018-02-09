package com.sxmoc.bq.holder;

import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxmoc.bq.R;
import com.sxmoc.bq.activity.CeShiLSActivity;
import com.sxmoc.bq.activity.ChanPinXQActivity;
import com.sxmoc.bq.activity.PdfActivity;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.customview.SingleBtnDialog;
import com.sxmoc.bq.customview.TwoBtnDialog;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.ProductQueryhistory;
import com.sxmoc.bq.model.TesterGetreport;
import com.sxmoc.bq.model.UserBuyerindex;
import com.sxmoc.bq.sql.MySql;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class CeShiLSViewHolder extends BaseViewHolder<ProductQueryhistory.DataBean> {

    private final TextView btnCaoZuo;
    private final TextView textName;
    private final TextView textDate;
    private ProductQueryhistory.DataBean data;
    private final TextView textXiaZai;
    private final TextView btnFuZhi;
    private final View viewChaKan;
    private final View viewFuZHi;
    private final ImageView image0000;
    private final ImageView image0001;

    public CeShiLSViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        btnCaoZuo = $(R.id.btnCaoZuo);
        textName = $(R.id.textName);
        textDate = $(R.id.textDate);
        viewChaKan = $(R.id.viewChaKan);
        viewFuZHi = $(R.id.viewFuZHi);
        image0000 = $(R.id.image0000);
        image0001 = $(R.id.image0001);
        viewChaKan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chaKanBaoGao();
            }
        });
        btnFuZhi = $(R.id.btnFuZhi);
        textXiaZai = $(R.id.textXiaZai);
        viewFuZHi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.getStatus() == 1) {
                    upLoad(true);
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
        data.setDownLoad(false);
        if (data.getStatus() == 1) {
            viewChaKan.setVisibility(View.VISIBLE);
            btnFuZhi.setText("复制报告");
            image0000.setImageResource(R.mipmap.fuzhi);
        } else {
            viewChaKan.setVisibility(View.GONE);
            btnFuZhi.setText("生成报告");
            image0000.setImageResource(R.mipmap.shengcheng);
        }
        textName.setText(data.getName());
        textDate.setText(data.getCreate_time());
        MySql mySql = new MySql(getContext());
        SQLiteDatabase sdb = mySql.getWritableDatabase();
        Cursor cursor = sdb.query("baogao", new String[]{"id", "filepath"}, "id = " + data.getId(), null, null, null, null);
        boolean b = cursor.moveToFirst();
        if (b) {
            String filePath = cursor.getString(1);
            LogUtil.LogShitou("CeShiLSActivity--initSP", "" + filePath);
            data.setDownLoad(true);
            data.setFilePath(filePath);
        }
//        if (data.isDownLoad()) {
//            textXiaZai.setVisibility(View.VISIBLE);
//        } else {
//            textXiaZai.setVisibility(View.GONE);
//        }
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
            params.put("tokenTime", ((CeShiLSActivity) getContext()).tokenTime);
        }
        params.put("bid", String.valueOf(data.getBid()));
        params.put("sid", String.valueOf(data.getSid()));
        return new OkObject(params, url);
    }

    /**
     * 查看报告
     */
    private void chaKanBaoGao() {
        if (data.isDownLoad()) {
            File file = new File(data.getFilePath());
            if (file.exists()) {
                Intent intent = new Intent();
                intent.setClass(getContext(), PdfActivity.class);
                intent.putExtra(Constant.IntentKey.TITLE, data.getName() + "的检测报告");
                intent.putExtra(Constant.IntentKey.VALUE, data.getFilePath());
                getContext().startActivity(intent);
            } else {
                final SingleBtnDialog singleBtnDialog = new SingleBtnDialog(getContext(), "文件不存在或被移动", "重新下载");
                singleBtnDialog.show();
                singleBtnDialog.setClicklistener(new SingleBtnDialog.ClickListenerInterface() {
                    @Override
                    public void doWhat() {
                        singleBtnDialog.dismiss();
                        upLoad(false);
                    }
                });
            }
        } else {
            upLoad(false);
        }
    }

    /**
     * 下载
     */
    private void upLoad(final boolean fuzhi) {
        ((CeShiLSActivity) getContext()).showLoadingDialog();
        ApiClient.post(getContext(), getShengChengOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                ((CeShiLSActivity) getContext()).cancelLoadingDialog();
                LogUtil.LogShitou("CeShiLSViewHolder--onSuccess", s + "");
                try {
                    final TesterGetreport testerGetreport = GsonUtils.parseJSON(s, TesterGetreport.class);
                    if (testerGetreport.getStatus() == 1) {
                        if (fuzhi) {
                            ClipboardManager cmb = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                            cmb.setText(testerGetreport.getData_url());
                            MyDialog.showTipDialog(getContext(), "已复制到剪切版");
                        } else {
                            ((CeShiLSActivity) getContext()).showLoadingDialog();
                            ApiClient.downLoadFile(getContext(), testerGetreport.getData_url(), "大脑雷达", data.getName() + "的详情报告" + System.currentTimeMillis() + ".pdf", new ApiClient.CallBack() {
                                @Override
                                public void onSuccess(String s) {
                                    Toast.makeText(getContext(), "文件保存在 " + s + " 目录下", Toast.LENGTH_SHORT).show();
                                    ((CeShiLSActivity) getContext()).cancelLoadingDialog();
                                    MySql mySql = new MySql(getContext());
                                    SQLiteDatabase sdb = mySql.getWritableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.put("id", data.getId());
                                    values.put("filepath", s);
                                    sdb.insert("baogao", null, values);
                                    sdb.close();
                                    ((CeShiLSActivity) getContext()).onRefresh();
                                    Intent intent = new Intent();
                                    intent.setClass(getContext(), PdfActivity.class);
                                    intent.putExtra(Constant.IntentKey.TITLE, data.getName() + "的检测报告");
                                    intent.putExtra(Constant.IntentKey.VALUE, s);
                                    getContext().startActivity(intent);
                                }

                                @Override
                                public void onError() {
                                    ((CeShiLSActivity) getContext()).cancelLoadingDialog();
                                }
                            });
                        }
                    } else if (testerGetreport.getStatus() == 3) {
                        MyDialog.showReLoginDialog(getContext());
                    } else {
                        Toast.makeText(getContext(), testerGetreport.getInfo(), Toast.LENGTH_SHORT).show();
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

}
