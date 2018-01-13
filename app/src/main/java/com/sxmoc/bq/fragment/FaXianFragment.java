package com.sxmoc.bq.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxmoc.bq.R;
import com.sxmoc.bq.activity.WebActivity;
import com.sxmoc.bq.adapter.BannerAdapter;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseFragment;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.holder.FaXianViewHolder;
import com.sxmoc.bq.holder.FaXianeBannerHolderView;
import com.sxmoc.bq.model.IndexFindindex;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.BannerSettingUtil;
import com.sxmoc.bq.util.DpUtils;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;
import com.sxmoc.bq.util.ScreenUtils;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FaXianFragment extends ZjbBaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    private View mInflate;
    private View viewBar;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<IndexFindindex.DataBean> adapter;
    private List<IndexFindindex.RecomBean> recomBeanList;
    private List<IndexFindindex.BannerBean> bannerBeanList;

    public FaXianFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_fa_xian, container, false);
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
        recyclerView = mInflate.findViewById(R.id.recyclerView);
    }

    @Override
    protected void initViews() {
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) DpUtils.convertDpToPixel(70, getActivity()) + ScreenUtils.getStatusBarHeight(getActivity());
        viewBar.setLayoutParams(layoutParams);
        initRecycler();
    }

    /**
     * 初始化recyclerview
     */
    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.line_width), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setRefreshingColorResources(R.color.basic_color);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<IndexFindindex.DataBean>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_fa_xian;
                return new FaXianViewHolder(parent, layout);
            }
        });
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            private TextView textPaiDangTitle;
            private ViewPager id_viewpager;
            private ConvenientBanner banner;

            @Override
            public View onCreateView(ViewGroup parent) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.header_fa_xian, null);
                banner = view.findViewById(R.id.banner);
                banner.setScrollDuration(1000);
                banner.startTurning(3000);
                textPaiDangTitle = view.findViewById(R.id.textPaiDangTitle);
                id_viewpager = view.findViewById(R.id.id_viewpager);
                new BannerSettingUtil(id_viewpager, (int) DpUtils.convertDpToPixel(8, getActivity()), false).set();
                id_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (recomBeanList != null) {
                            textPaiDangTitle.setText(recomBeanList.get(position % recomBeanList.size()).getTitle());
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                return view;
            }

            @Override
            public void onBindView(View headerView) {
                if (bannerBeanList != null) {
                    banner.setPages(new CBViewHolderCreator() {
                        @Override
                        public Object createHolder() {
                            return new FaXianeBannerHolderView();
                        }
                    }, bannerBeanList);
                }
                if (recomBeanList != null) {
                    if (recomBeanList.size() > 0) {
                        id_viewpager.setAdapter(new BannerAdapter(getActivity(), recomBeanList));
                        id_viewpager.setCurrentItem(50);
                    } else {
                    }
                }
            }
        });
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                ApiClient.post(getActivity(), getOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtil.LogShitou("DingDanGLActivity--加载更多", s + "");
                        try {
                            page++;
                            IndexFindindex indexFindindex = GsonUtils.parseJSON(s, IndexFindindex.class);
                            int status = indexFindindex.getStatus();
                            if (status == 1) {
                                List<IndexFindindex.DataBean> dataBeanList = indexFindindex.getData();
                                adapter.addAll(dataBeanList);
                            } else if (status == 3) {
                                MyDialog.showReLoginDialog(getActivity());
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
                Intent intent = new Intent();
                intent.setClass(getActivity(), WebActivity.class);
                intent.putExtra(Constant.IntentKey.TITLE, adapter.getItem(position).getTitle());
                intent.putExtra(Constant.IntentKey.URL, adapter.getItem(position).getUrl());
                startActivity(intent);
            }
        });
        recyclerView.setRefreshListener(this);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initData() {
        onRefresh();
    }

    int page = 1;

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.INDEX_FINDINDEX;
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
        ApiClient.post(getActivity(), getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("发现", s);
                try {
                    page++;
                    IndexFindindex indexFindindex = GsonUtils.parseJSON(s, IndexFindindex.class);
                    if (indexFindindex.getStatus() == 1) {
                        bannerBeanList = indexFindindex.getBanner();
                        recomBeanList = indexFindindex.getRecom();
                        List<IndexFindindex.DataBean> dataBeanList = indexFindindex.getData();
                        adapter.clear();
                        adapter.addAll(dataBeanList);
                    } else if (indexFindindex.getStatus() == 3) {
                        MyDialog.showReLoginDialog(getActivity());
                    } else {
                        showError(indexFindindex.getInfo());
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
                View viewLoader = LayoutInflater.from(getActivity()).inflate(R.layout.view_loaderror, null);
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
