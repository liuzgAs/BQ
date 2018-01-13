package com.sxmoc.bq.fragment;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sxmoc.bq.R;
import com.sxmoc.bq.activity.ChengShiXZActivity;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseFragment;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.model.CheckIdCard;
import com.sxmoc.bq.model.IndexCitylist;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.UserApply;
import com.sxmoc.bq.model.UserApplybefore;
import com.sxmoc.bq.util.ACache;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;

import java.util.HashMap;

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
    private EditText editRealName;
    private EditText editPhone;
    private EditText editCard;
    private EditText textAddressDetail;
    private CheckBox checkBox;
    private EditText editCompany;

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
        editRealName = mInflate.findViewById(R.id.editRealName);
        editPhone = mInflate.findViewById(R.id.editPhone);
        editCard = mInflate.findViewById(R.id.editCard);
        textAddressDetail = mInflate.findViewById(R.id.textAddressDetail);
        checkBox = mInflate.findViewById(R.id.checkBox);
        editCompany = mInflate.findViewById(R.id.editCompany);
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
        if (requestCode == Constant.RequestResultCode.CITY && resultCode == Constant.RequestResultCode.CITY) {
            IndexCitylist.CityEntity.ListEntity cityBean = (IndexCitylist.CityEntity.ListEntity) data.getSerializableExtra(Constant.IntentKey.BEAN);
            cityBeanName = cityBean.getName();
            textAddress.setText(cityBeanName);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTiJiao:
                if (grade == -1) {
                    Toast.makeText(getActivity(), "请选择事业合伙人等级", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editCompany.getText().toString().trim())) {
                    Toast.makeText(getActivity(), "请填写公司名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editRealName.getText().toString().trim())) {
                    Toast.makeText(getContext(), "请填写真实姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editPhone.getText().toString().trim())) {
                    Toast.makeText(getActivity(), "请填写联系电话", Toast.LENGTH_SHORT).show();
                    return;
                }
                CheckIdCard checkIdCard = new CheckIdCard(editCard.getText().toString().trim());
                if (!checkIdCard.validate()) {
                    Toast.makeText(getActivity(), "请输入正确的身份证号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(cityBeanName)) {
                    Toast.makeText(getActivity(), "请选择寄货城市", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(textAddressDetail.getText().toString().trim())) {
                    Toast.makeText(getActivity(), "请填写寄货详细地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!checkBox.isChecked()){
                    Toast.makeText(getActivity(), "请阅读并同意《合作协议》", Toast.LENGTH_SHORT).show();
                    return;
                }
                tiJiao();
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
                                textBaoZhengJin.setText("¥" + userApplybefore.getGrade().get(i).getMoney());
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

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        ACache aCache = ACache.get(getActivity(), Constant.Acache.LOCATION);
        String did = aCache.getAsString(Constant.Acache.DID);
        String url = Constant.HOST + Constant.Url.USER_APPLY;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime",tokenTime);
        }
        params.put("company",editCompany.getText().toString().trim());
        params.put("name",editRealName.getText().toString().trim());
        params.put("mobile",editPhone.getText().toString().trim());
        params.put("card",editCard.getText().toString().trim());
        params.put("address",textAddressDetail.getText().toString().trim());
        params.put("did",did);
        params.put("grade",grade+"");
        params.put("area",cityBeanName+"");
        return new OkObject(params, url);
    }

    /**
     * 提交
     */
    private void tiJiao() {
        showLoadingDialog();
        ApiClient.post(getActivity(), getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("ShenQIngSYHHRFragment--onSuccess",s+ "");
                try {
                    UserApply userApply = GsonUtils.parseJSON(s, UserApply.class);
                    if (userApply.getStatus()==1){

                    }else if (userApply.getStatus()==3){
                        MyDialog.showReLoginDialog(getActivity());
                    }else {
                        Toast.makeText(getActivity(), userApply.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(),"数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
