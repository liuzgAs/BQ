package com.sxmoc.bq.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxmoc.bq.R;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseNotLeftActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.holder.XuanZeXYKViewHolder;
import com.sxmoc.bq.model.BankCardlist;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.SimpleInfo;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;
import com.sxmoc.bq.util.ScreenUtils;

import java.util.HashMap;
import java.util.List;


public class GuanLiYHKActivity extends ZjbBaseNotLeftActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private View viewBar;
    private TextView textShanChu;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<BankCardlist.DataBean> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guan_li_yhk);
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
        viewBar = findViewById(R.id.viewBar);
        textShanChu = (TextView) findViewById(R.id.textShanChu);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("管理银行卡");
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        textShanChu.setVisibility(View.VISIBLE);
//        textShanChu.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                textShanChu.setVisibility(View.GONE);
//            }
//        }, 2000);
        initRecycler();
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        onRefresh();
    }

    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(GuanLiYHKActivity.this));
        final DividerDecoration itemDecoration = new DividerDecoration(getResources().getColor(R.color.line_gray), (int) getResources().getDimension(R.dimen.line_width), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setRefreshingColor(getResources().getColor(R.color.basic_color));
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<BankCardlist.DataBean>(GuanLiYHKActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_xuan_ze_xyk;
                return new XuanZeXYKViewHolder(parent, layout);

            }
        });
        adapter.addFooter(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View view = LayoutInflater.from(GuanLiYHKActivity.this).inflate(R.layout.footer_xuan_ze_xyk, null);
                TextView textXinZengXYK = (TextView) view.findViewById(R.id.textXinZengXYK);
                textXinZengXYK.setText("新增银行卡");
                textXinZengXYK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(GuanLiYHKActivity.this, XinZengYHKActivity.class);
                        intent.putExtra(Constant.IntentKey.TITLE, "新增银行卡");
                        startActivityForResult(intent, Constant.RequestResultCode.XIN_YONG_KA);
                    }
                });
                return view;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        recyclerView.setRefreshListener(this);
        adapter.setOnItemLongClickListener(new RecyclerArrayAdapter.OnItemLongClickListener() {
            /**
             * des： 网络请求参数
             * author： ZhangJieBo
             * date： 2017/8/28 0028 上午 9:55
             */
            private OkObject getOkObject1(String id) {
                String url = Constant.HOST + Constant.Url.BANK_CARDDEL;
                HashMap<String, String> params = new HashMap<>();
                params.put("uid", userInfo.getUid());
                params.put("tokenTime", tokenTime);
                params.put("id", id);
                return new OkObject(params, url);
            }

            @Override
            public boolean onItemLongClick(final int position) {
                AlertDialog alertDialog = new AlertDialog.Builder(GuanLiYHKActivity.this).setTitle("提示")
                        .setMessage("确定要删除吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showLoadingDialog();
                                ApiClient.post(GuanLiYHKActivity.this, getOkObject1(String.valueOf(adapter.getItem(position).getId())), new ApiClient.CallBack() {
                                    @Override
                                    public void onSuccess(String s) {
                                        cancelLoadingDialog();
                                        LogUtil.LogShitou("GuanLiYHKFragment--删除银行卡", "" + s);
                                        try {
                                            SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                                            if (simpleInfo.getStatus() == 1) {
                                                onRefresh();
                                            } else if (simpleInfo.getStatus() == 3) {
                                                MyDialog.showReLoginDialog(GuanLiYHKActivity.this);
                                            } else {
                                            }
                                            Toast.makeText(GuanLiYHKActivity.this, simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            Toast.makeText(GuanLiYHKActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onError() {
                                        cancelLoadingDialog();
                                        Toast.makeText(GuanLiYHKActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).create();
                alertDialog.show();
                return false;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.RequestResultCode.XIN_YONG_KA && resultCode == Constant.RequestResultCode.XIN_YONG_KA) {
            onRefresh();
        }
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.BANK_CARDLIST;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", userInfo.getUid());
        params.put("tokenTime", tokenTime);
        return new OkObject(params, url);
    }

    @Override
    public void onRefresh() {
        ApiClient.post(GuanLiYHKActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("选择信用卡", s);
                try {
                    BankCardlist bankCardlist = GsonUtils.parseJSON(s, BankCardlist.class);
                    if (bankCardlist.getStatus() == 1) {
                        adapter.clear();
                        List<BankCardlist.DataBean> bankCardlistData = bankCardlist.getData();
                        adapter.addAll(bankCardlistData);
                    } else if (bankCardlist.getStatus() == 3) {
                        MyDialog.showReLoginDialog(GuanLiYHKActivity.this);
                    } else {
                        showError(bankCardlist.getInfo());
                    }
                } catch (Exception e) {
                    showError("数据出错");
                }
            }

            @Override
            public void onError() {
                showError("网络出错");
            }

            public void showError(String msg) {
                View view_loaderror = LayoutInflater.from(GuanLiYHKActivity.this).inflate(R.layout.view_loaderror, null);
                TextView textMsg = (TextView) view_loaderror.findViewById(R.id.textMsg);
                textMsg.setText(msg);
                view_loaderror.findViewById(R.id.buttonReLoad).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerView.showProgress();
                        initData();
                    }
                });
                recyclerView.setErrorView(view_loaderror);
                recyclerView.showError();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBack:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        
    }
}
