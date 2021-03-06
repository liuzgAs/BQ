package com.sxmoc.bq.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxmoc.bq.R;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.holder.GongXiangViewHolder;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.UserGetmyshare;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;

import java.util.HashMap;
import java.util.List;

public class WoDeGXActivity extends ZjbBaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<UserGetmyshare.DataBean> adapter;
    private TextView textGongXiangNum;
    private TextView textHeHuoRen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wo_de_gx);
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
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
        textGongXiangNum = (TextView) findViewById(R.id.textGongXiangNum);
        textHeHuoRen = (TextView) findViewById(R.id.textHeHuoRen);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("我的共享");
        initRecycler();
    }

    /**
     * 初始化recyclerview
     */
    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.line_width), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setRefreshingColorResources(R.color.basic_color);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<UserGetmyshare.DataBean>(WoDeGXActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_gongxiang;
                return new GongXiangViewHolder(parent, layout);
            }
        });
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                ApiClient.post(WoDeGXActivity.this, getOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtil.LogShitou("DingDanGLActivity--加载更多", s + "");
                        try {
                            page++;
                            UserGetmyshare userGetmyshare = GsonUtils.parseJSON(s, UserGetmyshare.class);
                            int status = userGetmyshare.getStatus();
                            if (status == 1) {
                                List<UserGetmyshare.DataBean> dataBeanList = userGetmyshare.getData();
                                adapter.addAll(dataBeanList);
                            } else if (status == 3) {
                                MyDialog.showReLoginDialog(WoDeGXActivity.this);
                            } else {
                                adapter.pauseMore();
                            }
                        } catch (Exception e) {
                            adapter.pauseMore();
                        }
                    }

                    @Override
                    public void onError() {
                        adapter.pauseMore();
                    }
                });
            }

            @Override
            public void onMoreClick() {

            }
        });
        adapter.setNoMore(R.layout.view_nomore, new RecyclerArrayAdapter.OnNoMoreListener() {
            @Override
            public void onNoMoreShow() {

            }

            @Override
            public void onNoMoreClick() {
            }
        });
        adapter.setError(R.layout.view_error, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
                adapter.resumeMore();
            }

            @Override
            public void onErrorClick() {
                adapter.resumeMore();
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (isGongXiang){
                    Intent intent = new Intent();
                    intent.setClass(WoDeGXActivity.this, GongXiangHYActivity.class);
                    intent.putExtra(Constant.IntentKey.TITLE,"我的共享");
                    intent.putExtra(Constant.IntentKey.ID, adapter.getItem(position).getId());
                    startActivity(intent);
                }else {
                    Intent intent = new Intent();
                    intent.setClass(WoDeGXActivity.this, GongXiangHYActivity.class);
                    intent.putExtra(Constant.IntentKey.TITLE,"我的合伙人");
                    intent.putExtra(Constant.IntentKey.ID, adapter.getItem(position).getId());
                    startActivity(intent);
                }
            }
        });
        recyclerView.setRefreshListener(this);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.viewFenXiang).setOnClickListener(this);
        findViewById(R.id.viewHeHuo).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        onRefresh();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewFenXiang:
                isGongXiang=true;
                onRefresh();
                break;
            case R.id.viewHeHuo:
                isGongXiang=false;
                onRefresh();
                break;
            case R.id.imageBack:
                finish();
                break;
            default:
                break;
        }
    }

    int page = 1;
    boolean isGongXiang = true;

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url;
        if (isGongXiang){
            url = Constant.HOST + Constant.Url.USER_GETMYSHARE;
        }else {
            url = Constant.HOST + Constant.Url.USER_GETMYPARTNER;
        }
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("p", String.valueOf(page));
        return new OkObject(params, url);
    }

    @Override
    public void onRefresh() {
        page = 1;
        ApiClient.post(this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("我的共享", s);
                try {
                    page++;
                    UserGetmyshare userGetmyshare = GsonUtils.parseJSON(s, UserGetmyshare.class);
                    if (userGetmyshare.getStatus() == 1) {
                        textGongXiangNum.setText(String.valueOf(userGetmyshare.getShare_num()));
                        textHeHuoRen.setText(String.valueOf(userGetmyshare.getPartner_num()));
                        List<UserGetmyshare.DataBean> dataBeanList = userGetmyshare.getData();
                        adapter.clear();
                        adapter.addAll(dataBeanList);
                    } else if (userGetmyshare.getStatus() == 3) {
                        MyDialog.showReLoginDialog(WoDeGXActivity.this);
                    } else {
                        showError(userGetmyshare.getInfo());
                    }
                } catch (Exception e) {
                    showError("数据出错");
                }
            }

            @Override
            public void onError() {
                showError("网络出错");
            }

            /**
             * 错误显示
             * @param msg
             */
            private void showError(String msg) {
                View viewLoader = LayoutInflater.from(WoDeGXActivity.this).inflate(R.layout.view_loaderror, null);
                TextView textMsg = viewLoader.findViewById(R.id.textMsg);
                textMsg.setText(msg);
                viewLoader.findViewById(R.id.buttonReLoad).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerView.showProgress();
                        initData();
                    }
                });
                recyclerView.setErrorView(viewLoader);
                recyclerView.showError();
            }
        });
    }
}
