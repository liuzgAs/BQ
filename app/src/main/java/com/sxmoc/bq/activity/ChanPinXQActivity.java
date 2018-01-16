package com.sxmoc.bq.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.sxmoc.bq.R;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.holder.BannerHolderView;
import com.sxmoc.bq.holder.ChanPinDesViewHolder;
import com.sxmoc.bq.model.GoodsInfo;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.DpUtils;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;
import com.sxmoc.bq.util.RecycleViewDistancaUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChanPinXQActivity extends ZjbBaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private View viewBar;
    private ImageView imageBack;
    private TextView textViewTitle;
    private int id;
    private List<GoodsInfo.BannerBean> banner1 = new ArrayList<>();
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<GoodsInfo.DesListBean> adapter;
    private GoodsInfo.DataBean data;
    private Button textReserve;
    private BroadcastReceiver reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BroadcastCode.ZHI_FU_CG:
                    finish();
                    break;
                default:

                    break;
            }
        }
    };
    private GoodsInfo goodsInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chan_pin_xq);
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        id = intent.getIntExtra(Constant.IntentKey.ID, 0);
    }

    @Override
    protected void findID() {
        viewBar = findViewById(R.id.viewBar);
        imageBack = (ImageView) findViewById(R.id.imageBack);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textReserve = (Button) findViewById(R.id.textReserve);
    }

    @Override
    protected void initViews() {
        viewBar.getBackground().mutate().setAlpha(0);
        imageBack.getBackground().mutate().setAlpha(255);
        textViewTitle.setAlpha(0);
        textReserve.setVisibility(View.INVISIBLE);
        initRecycler();
    }

    /**
     * 初始化recyclerview
     */
    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setRefreshingColorResources(R.color.basic_color);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<GoodsInfo.DesListBean>(ChanPinXQActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_chan_pin_sm;
                return new ChanPinDesViewHolder(parent, layout);
            }
        });
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            private TextView textUnit;
            private ConvenientBanner banner;
            private TextView textZhiShiQi;
            private TextView textPrice;
            private TextView textTitle;
            private TextView textDes;
            @Override
            public View onCreateView(ViewGroup parent) {
                View view = LayoutInflater.from(ChanPinXQActivity.this).inflate(R.layout.header_chanpin, null);
                banner =  view.findViewById(R.id.banner);
                banner.setScrollDuration(1000);
                banner.startTurning(3000);
                textZhiShiQi =  view.findViewById(R.id.textZhiShiQi);
                textPrice =  view.findViewById(R.id.textPrice);
                textTitle =  view.findViewById(R.id.textTitle);
                textDes =  view.findViewById(R.id.textDes);
                textUnit = view.findViewById(R.id.textUnit);
                banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        LogUtil.LogShitou("ChanPinXQActivity--onPageSelected", ""+position);
                        textZhiShiQi.setText((position + 1) + "/" + banner1.size());
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                return view;
            }

            @Override
            public void onBindView(View headerView) {
                textZhiShiQi.setText("1/" + banner1.size());
                banner.setPages(new CBViewHolderCreator() {
                    @Override
                    public Object createHolder() {
                        return new BannerHolderView();
                    }
                }, banner1);
                if (data!=null){
                    textUnit.setText("/"+data.getUnit());
                    SpannableString span = new SpannableString("¥"+data.getPrice());
                    span.setSpan(new RelativeSizeSpan(0.6f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    textPrice.setText(span);
                    textTitle.setText(data.getTitle());
                    textDes.setText(data.getPriceDes());
                }
            }
        });
        adapter.addFooter(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View view = new View(ChanPinXQActivity.this);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int) DpUtils.convertDpToPixel(10,ChanPinXQActivity.this)));
                return view;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        recyclerView.setRefreshListener(this);
    }

    @Override
    protected void setListeners() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int scrollY = RecycleViewDistancaUtil.getDistance(recyclerView, 0);
                float guangGaoHeight = getResources().getDimension(R.dimen.bannerHeight);
                if (scrollY <= guangGaoHeight - viewBar.getHeight() && scrollY >= 0) {
                    int baiFenBi = (int) ((double) scrollY / (double) (guangGaoHeight - viewBar.getHeight()) * 255);
                    viewBar.getBackground().mutate().setAlpha(baiFenBi);
                    imageBack.getBackground().mutate().setAlpha(255 - baiFenBi);
                    textViewTitle.setAlpha((float) baiFenBi / 255f);
                } else {
                    viewBar.getBackground().mutate().setAlpha(255);
                    textViewTitle.setAlpha(1);
                }
            }
        });
        textReserve.setOnClickListener(this);
        findViewById(R.id.imageBack).setOnClickListener(this);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.GOODS_INFO ;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime",tokenTime);
        }
        params.put("id",id+"");
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        onRefresh();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.imageBack:
                finish();
                break;
            case R.id.textReserve:
                if (goodsInfo.getIs_vip()==1){
                    MyDialog.showTipDialog(ChanPinXQActivity.this,goodsInfo.getTips());
                }else {
                    intent.setClass(this, QueRenDDActivity.class);
                    intent.putExtra(Constant.IntentKey.ID,id);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        ApiClient.post(this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("产品详情", s);
                try {
                    goodsInfo = GsonUtils.parseJSON(s, GoodsInfo.class);
                    if (goodsInfo.getStatus() == 1) {
                        banner1.clear();
                        banner1.addAll(goodsInfo.getBanner());
                        data = goodsInfo.getData();
                        List<GoodsInfo.DesListBean> desList = goodsInfo.getDesList();
                        adapter.clear();
                        adapter.addAll(desList);
                        textReserve.setVisibility(View.VISIBLE);
                    } else if (goodsInfo.getStatus()== 3) {
                        MyDialog.showReLoginDialog(ChanPinXQActivity.this);
                    } else {
                        showError(goodsInfo.getInfo());
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
                View viewLoader = LayoutInflater.from(ChanPinXQActivity.this).inflate(R.layout.view_loaderror, null);
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
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BroadcastCode.ZHI_FU_CG);
        registerReceiver(reciver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reciver);
    }
}
