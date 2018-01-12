package com.sxmoc.bq.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.sxmoc.bq.R;
import com.sxmoc.bq.base.ZjbBaseFragment;
import com.sxmoc.bq.util.DpUtils;
import com.sxmoc.bq.util.ScreenUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class FaXianFragment extends ZjbBaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    private View mInflate;
    private View viewBar;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<Integer> adapter;

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
        layoutParams.height = (int)DpUtils.convertDpToPixel(70,getActivity())+ScreenUtils.getStatusBarHeight(getActivity());
        viewBar.setLayoutParams(layoutParams);
//        initRecycler();
    }

//    /**
//     * 初始化recyclerview
//     */
//    private void initRecycler() {
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.line_width), 0, 0);
//        itemDecoration.setDrawLastItem(false);
//        recyclerView.addItemDecoration(itemDecoration);
//        recyclerView.setRefreshingColorResources(R.color.basic_color);
//        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Integer>(FaXianFragment.this) {
//            @Override
//            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
//                int layout = R.layout.null;
//                return new MyBaseViewHolder(parent, layout);
//            }
//        });
//        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
//            @Override
//            public void onMoreShow() {
//
//            }
//
//            @Override
//            public void onMoreClick() {
//
//            }
//        });
//        adapter.setNoMore(R.layout.view_nomore, new RecyclerArrayAdapter.OnNoMoreListener() {
//            @Override
//            public void onNoMoreShow() {
//
//            }
//
//            @Override
//            public void onNoMoreClick() {
//            }
//        });
//        adapter.setError(R.layout.view_error, new RecyclerArrayAdapter.OnErrorListener() {
//            @Override
//            public void onErrorShow() {
//                adapter.resumeMore();
//            }
//
//            @Override
//            public void onErrorClick() {
//                adapter.resumeMore();
//            }
//        });
//        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//            }
//        });
//        recyclerView.setRefreshListener(this);
//    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onRefresh() {

    }
}
