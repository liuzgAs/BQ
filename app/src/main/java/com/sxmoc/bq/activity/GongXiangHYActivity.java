package com.sxmoc.bq.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxmoc.bq.R;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.holder.GongXiangHYViewHolder;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.UserGetmyshare1;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GlideApp;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;

import java.util.HashMap;
import java.util.List;

public class GongXiangHYActivity extends ZjbBaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<UserGetmyshare1.DataBean> adapter;
    private TextView textGongXiangNum;
    private TextView textHeHuoRen;
    private int id;
    private ImageView imageImg;
    private TextView textShenFen;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gong_xiang_hy);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        id = intent.getIntExtra(Constant.IntentKey.ID, 0);
        title = intent.getStringExtra(Constant.IntentKey.TITLE);
    }

    @Override
    protected void findID() {
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
        textGongXiangNum = (TextView) findViewById(R.id.textGongXiangNum);
        textHeHuoRen = (TextView) findViewById(R.id.textHeHuoRen);
        imageImg = (ImageView) findViewById(R.id.imageImg);
        textShenFen = (TextView) findViewById(R.id.textShenFen);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText(title);
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
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<UserGetmyshare1.DataBean>(GongXiangHYActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_gongxiang;
                return new GongXiangHYViewHolder(parent, layout);
            }
        });
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                ApiClient.post(GongXiangHYActivity.this, getOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtil.LogShitou("DingDanGLActivity--加载更多", s+"");
                        try {
                            page++;
                            UserGetmyshare1 userGetmyshare1 = GsonUtils.parseJSON(s, UserGetmyshare1.class);
                            int status = userGetmyshare1.getStatus();
                            if (status == 1) {
                                List<UserGetmyshare1.DataBean> dataBeanList = userGetmyshare1.getData();
                                adapter.addAll(dataBeanList);
                            } else if (status == 3) {
                                MyDialog.showReLoginDialog(GongXiangHYActivity.this);
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
            }
        });
        recyclerView.setRefreshListener(this);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        onRefresh();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageBack:
                finish();
                break;
            default:
                break;
        }
    }

    int page = 1;

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url;
        if (TextUtils.equals("我的合伙人",title)){
            url = Constant.HOST + Constant.Url.USER_GETMYPARTNER1;
        }else {
             url = Constant.HOST + Constant.Url.USER_GETMYSHARE1;
        }

        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime",tokenTime);
        }
        params.put("id",String.valueOf(id));
        params.put("p",String.valueOf(page));
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
                    UserGetmyshare1 userGetmyshare1 = GsonUtils.parseJSON(s, UserGetmyshare1.class);
                    if (userGetmyshare1.getStatus() == 1) {
                        GlideApp.with(GongXiangHYActivity.this)
                                .asBitmap()
                                .circleCrop()
                                .load(userGetmyshare1.getZ_img())
                                .placeholder(R.mipmap.ic_empty)
                                .into(imageImg);
                        textGongXiangNum.setText(String.valueOf(userGetmyshare1.getName()));
                        textHeHuoRen.setText(String.valueOf(userGetmyshare1.getShare_num()));
                        textShenFen.setText(userGetmyshare1.getGrade_name());
                        List<UserGetmyshare1.DataBean> dataBeanList = userGetmyshare1.getData();
                        adapter.clear();
                        adapter.addAll(dataBeanList);
                    } else if (userGetmyshare1.getStatus() == 3) {
                        MyDialog.showReLoginDialog(GongXiangHYActivity.this);
                    } else {
                        showError(userGetmyshare1.getInfo());
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
                View viewLoader = LayoutInflater.from(GongXiangHYActivity.this).inflate(R.layout.view_loaderror, null);
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
