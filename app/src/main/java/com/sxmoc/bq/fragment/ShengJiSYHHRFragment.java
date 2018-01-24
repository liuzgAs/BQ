package com.sxmoc.bq.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxmoc.bq.R;
import com.sxmoc.bq.base.ZjbBaseFragment;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.UserUpgrade;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShengJiSYHHRFragment extends ZjbBaseFragment {


    private View mInflate;
    private View viewContent;
    private TextView text0000;
    private TextView text0001;
    private TextView text0002;
    private TextView text0100;
    private TextView text0101;
    private TextView text0102;
    private TextView text0200;
    private TextView text0201;
    private TextView text0300;
    private TextView text0301;
    private TextView text0400;
    private TextView text0401;
    private ImageView image0200;
    private ImageView image0300;
    private ImageView image0400;
    private Button btn0200;
    private Button btn0300;
    private Button btn0400;

    public ShengJiSYHHRFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_sheng_ji_syhhr, container, false);
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
        viewContent = mInflate.findViewById(R.id.viewContent);
        text0000 = mInflate.findViewById(R.id.text0000);
        text0001 = mInflate.findViewById(R.id.text0001);
        text0002 = mInflate.findViewById(R.id.text0002);
        text0100 = mInflate.findViewById(R.id.text0100);
        text0101 = mInflate.findViewById(R.id.text0101);
        text0102 = mInflate.findViewById(R.id.text0102);
        text0200 = mInflate.findViewById(R.id.text0200);
        text0201 = mInflate.findViewById(R.id.text0201);
        text0300 = mInflate.findViewById(R.id.text0300);
        text0301 = mInflate.findViewById(R.id.text0301);
        text0400 = mInflate.findViewById(R.id.text0400);
        text0401 = mInflate.findViewById(R.id.text0401);
        image0200 = mInflate.findViewById(R.id.image0200);
        image0300 = mInflate.findViewById(R.id.image0300);
        image0400 = mInflate.findViewById(R.id.image0400);
        btn0200 = mInflate.findViewById(R.id.btn0200);
        btn0300 = mInflate.findViewById(R.id.btn0300);
        btn0400 = mInflate.findViewById(R.id.btn0400);
    }

    @Override
    protected void initViews() {
        viewContent.setVisibility(View.GONE);
    }

    @Override
    protected void setListeners() {

    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        String url = Constant.HOST + Constant.Url.USER_UPGRADE;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        return new OkObject(params, url);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(mContext, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("ShengJiSYHHRFragment--onSuccess", s + "");
                try {
                    UserUpgrade userUpgrade = GsonUtils.parseJSON(s, UserUpgrade.class);
                    List<UserUpgrade.DataBean> data = userUpgrade.getData();
                    text0001.setText(data.get(0).getName());
                    text0000.setText(data.get(0).getDw());
                    text0002.setText(String.valueOf(data.get(0).getValue()));
                    text0101.setText(data.get(1).getName());
                    text0100.setText(data.get(1).getDw());
                    text0102.setText(String.valueOf(data.get(1).getValue()));
                    List<UserUpgrade.GradeBean> gradeBeanList = userUpgrade.getGrade();
                    text0200.setText(gradeBeanList.get(0).getName());
                    text0201.setText(gradeBeanList.get(0).getDes());
                    text0300.setText(gradeBeanList.get(1).getName());
                    text0301.setText(gradeBeanList.get(1).getDes());
                    text0400.setText(gradeBeanList.get(2).getName());
                    text0401.setText(gradeBeanList.get(2).getDes());
                    btn0200.setVisibility(View.GONE);
                    image0200.setVisibility(View.GONE);
                    btn0300.setVisibility(View.GONE);
                    image0300.setVisibility(View.GONE);
                    btn0400.setVisibility(View.GONE);
                    image0400.setVisibility(View.GONE);
                    if (gradeBeanList.get(0).getIsLock() == 1) {
                        btn0200.setVisibility(View.GONE);
                        image0200.setVisibility(View.VISIBLE);
                    }
                    if (gradeBeanList.get(1).getIsLock() == 1) {
                        btn0300.setVisibility(View.GONE);
                        image0300.setVisibility(View.VISIBLE);
                    }
                    if (gradeBeanList.get(2).getIsLock() == 1) {
                        btn0400.setVisibility(View.GONE);
                        image0400.setVisibility(View.VISIBLE);
                    }
                    if (gradeBeanList.get(0).getIsUp() == 1) {
                        btn0200.setVisibility(View.VISIBLE);
                        image0200.setVisibility(View.GONE);
                    }
                    if (gradeBeanList.get(1).getIsUp() == 1) {
                        btn0300.setVisibility(View.VISIBLE);
                        image0300.setVisibility(View.GONE);
                    }
                    if (gradeBeanList.get(2).getIsUp() == 1) {
                        btn0400.setVisibility(View.VISIBLE);
                        image0400.setVisibility(View.GONE);
                    }
                    viewContent.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    Toast.makeText(mContext, "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(mContext, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
