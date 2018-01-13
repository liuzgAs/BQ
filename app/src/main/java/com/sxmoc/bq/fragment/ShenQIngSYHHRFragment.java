package com.sxmoc.bq.fragment;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sxmoc.bq.R;
import com.sxmoc.bq.activity.ChengShiXZActivity;
import com.sxmoc.bq.base.ZjbBaseFragment;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.model.IndexCitylist;
import com.sxmoc.bq.model.UserApplybefore;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShenQIngSYHHRFragment extends ZjbBaseFragment implements View.OnClickListener {


    private View mInflate;
    UserApplybefore userApplybefore;
    private TextView textBaoZhengJin;
    private int grade = -1;
    private TextView textShiYeHHR;
    private TextView textCHanPin;
    private TextView textAddress;
    private String cityBeanName;

    public ShenQIngSYHHRFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ShenQIngSYHHRFragment(UserApplybefore userApplybefore) {
        this.userApplybefore = userApplybefore;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_shen_qing_syhhr, container, false);
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
        textBaoZhengJin = mInflate.findViewById(R.id.textBaoZhengJin);
        textShiYeHHR = mInflate.findViewById(R.id.textShiYeHHR);
        textCHanPin = mInflate.findViewById(R.id.textCHanPin);
        textAddress = mInflate.findViewById(R.id.textAddress);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setListeners() {
        mInflate.findViewById(R.id.viewShiYEHHR).setOnClickListener(this);
        mInflate.findViewById(R.id.viewAddress).setOnClickListener(this);
        mInflate.findViewById(R.id.btnTiJiao).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.RequestResultCode.CITY&&resultCode ==Constant.RequestResultCode.CITY){
            IndexCitylist.CityEntity.ListEntity cityBean = (IndexCitylist.CityEntity.ListEntity) data.getSerializableExtra(Constant.IntentKey.BEAN);
            cityBeanName = cityBean.getName();
            textAddress.setText(cityBeanName);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTiJiao:

                break;
            case R.id.viewAddress:
                Intent intent = new Intent();
                intent.setClass(getActivity(), ChengShiXZActivity.class);
                startActivityForResult(intent, Constant.RequestResultCode.CITY);
                break;
            case R.id.viewShiYEHHR:
                final String[] strings = new String[userApplybefore.getGrade().size()];
                for (int i = 0; i < userApplybefore.getGrade().size(); i++) {
                    strings[i] = userApplybefore.getGrade().get(i).getName() + "(¥" + userApplybefore.getGrade().get(i).getMoney() + ")";
                }
                new AlertDialog.Builder(getActivity())
                        .setItems(strings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                textBaoZhengJin.setText("¥"+userApplybefore.getGrade().get(i).getMoney());
                                textShiYeHHR.setText(userApplybefore.getGrade().get(i).getName());
                                textCHanPin.setText(userApplybefore.getGrade().get(i).getDes());
                                grade = userApplybefore.getGrade().get(i).getValue();
                            }
                        })
                        .show();
                break;
            default:
                break;
        }
    }
}
