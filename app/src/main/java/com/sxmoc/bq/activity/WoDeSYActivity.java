package com.sxmoc.bq.activity;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxmoc.bq.R;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.holder.XiaoFeiMXViewHolder;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.UserBuyerindex;
import com.sxmoc.bq.model.UserProfitdetailed;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.DateTransforam;
import com.sxmoc.bq.util.DpUtils;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class WoDeSYActivity extends ZjbBaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private TextView textViewRight;
    private TextView textShouYi;
    private int page = 1;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<UserProfitdetailed.DataBean> adapter;
    private BroadcastReceiver reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BroadcastCode.TIXIAN:
                    onRefresh();
                    break;
                default:
                    break;
            }
        }
    };
    private TextView textStart;
    private TextView textEnd;
    private String mobile;
    private TextView textLeiJi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wo_de_sy);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        mobile = intent.getStringExtra(Constant.IntentKey.PHONE);
    }

    @Override
    protected void findID() {
        textViewRight = (TextView) findViewById(R.id.textViewRight);
        textShouYi = (TextView) findViewById(R.id.textShouYi);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
        textStart = (TextView) findViewById(R.id.textStart);
        textEnd = (TextView) findViewById(R.id.textEnd);
        textLeiJi = (TextView) findViewById(R.id.textLeiJi);
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("我的收益");
        textViewRight.setText("收益明细");
        textViewRight.setVisibility(View.GONE);
        initRecycle();
    }

    private void initRecycle() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(WoDeSYActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) DpUtils.convertDpToPixel(1f, WoDeSYActivity.this), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setRefreshingColorResources(R.color.basic_color);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<UserProfitdetailed.DataBean>(WoDeSYActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_chong_zhi_mx;
                return new XiaoFeiMXViewHolder(parent, layout);
            }

        });
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                ApiClient.post(WoDeSYActivity.this, getOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            page++;
                            UserProfitdetailed userProfitdetailed = GsonUtils.parseJSON(s, UserProfitdetailed.class);
                            int status = userProfitdetailed.getStatus();
                            if (status == 1) {
                                List<UserProfitdetailed.DataBean> dataBeanList = userProfitdetailed.getData();
                                adapter.addAll(dataBeanList);
                            } else if (status == 3) {
                                MyDialog.showReLoginDialog(WoDeSYActivity.this);
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
        recyclerView.setRefreshListener(this);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }

    @Override
    protected void setListeners() {
        textViewRight.setOnClickListener(this);
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.btnLiJiTX).setOnClickListener(this);
        findViewById(R.id.viewStart).setOnClickListener(this);
        findViewById(R.id.viewEnd).setOnClickListener(this);
    }

    private String date_begin ="";
    private String date_end="";

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.USER_PROFITDETAILED;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("p", String.valueOf(page));
        params.put("type_id", "1");
        params.put("date_begin", date_begin);
        params.put("date_end", date_end);
        return new OkObject(params, url);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkYuEObject() {
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
        onRefresh();
    }

    @Override
    public void onRefresh() {

        showLoadingDialog();
        ApiClient.post(WoDeSYActivity.this, getOkYuEObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("LiJiZFActivity--onSuccess", s + "");
                try {
                    UserBuyerindex userBuyerindex = GsonUtils.parseJSON(s, UserBuyerindex.class);
                    if (userBuyerindex.getStatus() == 1) {
                        SpannableString span = new SpannableString("¥" + userBuyerindex.getMoney());
                        span.setSpan(new RelativeSizeSpan(0.4f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        textShouYi.setText(span);
                        SpannableString span1 = new SpannableString("¥" + userBuyerindex.getProfit_money());
                        span.setSpan(new RelativeSizeSpan(0.4f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        textLeiJi.setText(span1);
                    } else if (userBuyerindex.getStatus() == 3) {
                        MyDialog.showReLoginDialog(WoDeSYActivity.this);
                    } else {
                        Toast.makeText(WoDeSYActivity.this, userBuyerindex.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(WoDeSYActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(WoDeSYActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });

        page = 1;
        ApiClient.post(WoDeSYActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("消费明细", s);
                try {
                    page++;
                    UserProfitdetailed userProfitdetailed = GsonUtils.parseJSON(s, UserProfitdetailed.class);
                    if (userProfitdetailed.getStatus() == 1) {
                        List<UserProfitdetailed.DataBean> dataBeanList = userProfitdetailed.getData();
                        adapter.clear();
                        adapter.addAll(dataBeanList);
                    } else if (userProfitdetailed.getStatus() == 3) {
                        MyDialog.showReLoginDialog(WoDeSYActivity.this);
                    } else {
                        showError(userProfitdetailed.getInfo());
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
                View viewLoader = LayoutInflater.from(WoDeSYActivity.this).inflate(R.layout.view_loaderror, null);
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
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.viewStart:
                Calendar c1 = Calendar.getInstance();
                DatePickerDialog datePickerDialog1 = new DatePickerDialog(WoDeSYActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        try {
                            date_begin = DateTransforam.dateToStamp(year + "-" + (month + 1) + "-" + dayOfMonth);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        textStart.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                        onRefresh();
                    }
                }, c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH));
                if (!TextUtils.isEmpty(date_end)) {
                    datePickerDialog1.getDatePicker().setMaxDate(Long.parseLong(date_end)*1000);
                } else {
                    datePickerDialog1.getDatePicker().setMaxDate(System.currentTimeMillis());
                }
                datePickerDialog1.show();
                break;
            case R.id.viewEnd:
                Calendar c = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(WoDeSYActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        try {
                            date_end = DateTransforam.dateToStamp(year + "-" + (month + 1) + "-" + dayOfMonth);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        textEnd.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                        onRefresh();
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                if (!TextUtils.isEmpty(date_begin)) {
                    datePickerDialog.getDatePicker().setMinDate(Long.parseLong(date_begin)*1000);
                }
                datePickerDialog.show();
                break;
            case R.id.btnLiJiTX:
                intent.putExtra(Constant.IntentKey.PHONE,mobile);
                intent.setClass(this, TiXianActivity.class);
                startActivity(intent);
                break;
            case R.id.imageBack:
                finish();
                break;
            case R.id.textViewRight:
                intent.setClass(this, ShouYiMxActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BroadcastCode.TIXIAN);
        registerReceiver(reciver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reciver);
    }
}
