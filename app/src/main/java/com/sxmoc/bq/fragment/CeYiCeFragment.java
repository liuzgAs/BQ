package com.sxmoc.bq.fragment;


import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.sxmoc.bq.R;
import com.sxmoc.bq.activity.BaoBaoLBActivity;
import com.sxmoc.bq.activity.WebActivity;
import com.sxmoc.bq.base.ZjbBaseFragment;
import com.sxmoc.bq.constant.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class CeYiCeFragment extends ZjbBaseFragment implements View.OnClickListener {


    private View mInflate;
    private ImageView imageNeiQuan;
    private ImageView imageWaiQuan;
    private boolean isZhuYi = true;


    public CeYiCeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_ce_yi_ce, container, false);
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
        imageNeiQuan = mInflate.findViewById(R.id.imageNeiQuan);
        imageWaiQuan = mInflate.findViewById(R.id.imageWaiQuan);
    }

    ObjectAnimator animator = new ObjectAnimator();
    ObjectAnimator animator1 = new ObjectAnimator();

    @SuppressLint("WrongConstant")
    @Override
    protected void initViews() {
        PropertyValuesHolder holder02 = PropertyValuesHolder.ofFloat("rotation", 360, 0);

        animator1 = ObjectAnimator.ofPropertyValuesHolder(imageNeiQuan, holder02);
        animator1.setInterpolator(new LinearInterpolator());
        animator1.setDuration(6000);
        animator1.setRepeatCount(ValueAnimator.INFINITE);
        animator1.setRepeatMode(ValueAnimator.INFINITE);
        animator1.start();
        PropertyValuesHolder holder01 = PropertyValuesHolder.ofFloat("rotation", 0, 360);

        animator = ObjectAnimator.ofPropertyValuesHolder(imageWaiQuan, holder01);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(8000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.INFINITE);
    }


    @Override
    protected void setListeners() {
        mInflate.findViewById(R.id.btnKaiShiJC).setOnClickListener(this);
        mInflate.findViewById(R.id.imageNotify).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.imageNotify:
                intent.setClass(getActivity(), WebActivity.class);
                intent.putExtra(Constant.IntentKey.TITLE, "注意事项");
                intent.putExtra(Constant.IntentKey.URL, Constant.Url.PRECAUTIONS);
                startActivity(intent);
                break;
            case R.id.btnKaiShiJC:
                if (isZhuYi){
                    intent.setClass(getActivity(), WebActivity.class);
                    intent.putExtra(Constant.IntentKey.TITLE, "注意事项");
                    intent.putExtra(Constant.IntentKey.URL, Constant.Url.PRECAUTIONS);
                    startActivity(intent);
                    isZhuYi=false;
                }else {
                    startTest();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (animator != null) {
            animator.start();
        }
        if (animator1 != null) {
            animator1.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (animator != null) {
            animator.end();
        }
        if (animator1 != null) {
            animator1.end();
        }
    }

    private void startTest() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), BaoBaoLBActivity.class);
        startActivity(intent);
    }

}
