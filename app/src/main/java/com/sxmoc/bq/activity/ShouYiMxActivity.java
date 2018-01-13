package com.sxmoc.bq.activity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxmoc.bq.R;
import com.sxmoc.bq.base.ZjbBaseActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.holder.XiaoFeiMXViewHolder;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.DateTransforam;
import com.sxmoc.bq.util.DpUtils;
import com.sxmoc.bq.util.LogUtil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;

public class ShouYiMxActivity extends ZjbBaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<Integer> adapter;
    private int page = 1;
    private TextView textStart;
    private TextView textEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shou_yi_mx);
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
        textStart = (TextView) findViewById(R.id.textStart);
        textEnd = (TextView) findViewById(R.id.textEnd);
    }

    @Override
    protected void initViews() {
        ((TextView)findViewById(R.id.textViewTitle)).setText("收益明细");
        initRecycle();
    }

    private void initRecycle() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(ShouYiMxActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) DpUtils.convertDpToPixel(1f, ShouYiMxActivity.this), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setRefreshingColorResources(R.color.basic_color);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Integer>(ShouYiMxActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_chong_zhi_mx;
                return new XiaoFeiMXViewHolder(parent, layout);
            }

        });
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                ApiClient.post(ShouYiMxActivity.this, getOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        try {
//                            page++;
//                            CorderGetconsumedetail corderGetconsumedetail = GsonUtils.parseJSON(s, CorderGetconsumedetail.class);
//                            int status = corderGetconsumedetail.getStatus();
//                            if (status == 1) {
//                                List<CorderGetconsumedetail.DataBean> dataBeanList = corderGetconsumedetail.getData();
//                                adapter.addAll(dataBeanList);
//                            } else if (status == 3) {
//                                MyDialog.showReLoginDialog(ShouYiMxActivity.this);
//                            } else {
//                                adapter.pauseMore();
//                            }
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
        findViewById(R.id.viewStart).setOnClickListener(this);
        findViewById(R.id.viewEnd).setOnClickListener(this);
        findViewById(R.id.imageBack).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        onRefresh();
    }

    private String date_begin;
    private String date_end;

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
        params.put("date_begin", date_begin);
        params.put("date_end", date_end);
        params.put("p", String.valueOf(page));
        return new OkObject(params, url);
    }

    @Override
    public void onRefresh() {
        page = 1;
        ApiClient.post(ShouYiMxActivity.this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("消费明细", s);
                try {
//                    page++;
//                    CorderGetconsumedetail corderGetconsumedetail = GsonUtils.parseJSON(s, CorderGetconsumedetail.class);
//                    if (corderGetconsumedetail.getStatus() == 1) {
//                        List<CorderGetconsumedetail.DataBean> dataBeanList = corderGetconsumedetail.getData();
//                        adapter.clear();
//                        adapter.addAll(dataBeanList);
//                    } else if (corderGetconsumedetail.getStatus() == 3) {
//                        MyDialog.showReLoginDialog(ShouYiMxActivity.this);
//                    } else {
//                        showError(corderGetconsumedetail.getInfo());
//                    }
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
                View viewLoader = LayoutInflater.from(ShouYiMxActivity.this).inflate(R.layout.view_loaderror, null);
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
        switch (view.getId()) {
            case R.id.imageBack:
                finish();
                break;
            case R.id.viewStart:
                Calendar c1 = Calendar.getInstance();
                DatePickerDialog datePickerDialog1 = new DatePickerDialog(ShouYiMxActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    datePickerDialog1.getDatePicker().setMaxDate(Long.parseLong(date_end));
                } else {
                    datePickerDialog1.getDatePicker().setMaxDate(System.currentTimeMillis());
                }
                datePickerDialog1.show();
                break;
            case R.id.viewEnd:
                Calendar c = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(ShouYiMxActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    datePickerDialog.getDatePicker().setMinDate(Long.parseLong(date_begin));
                }
                datePickerDialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
