package com.sxmoc.bq.fragment;


import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.reflect.TypeToken;
import com.sxmoc.bq.R;
import com.sxmoc.bq.activity.WebActivity;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseFragment;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.model.IndexCitylist;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.ProvinceBean;
import com.sxmoc.bq.model.UserApply;
import com.sxmoc.bq.model.UserApplybefore;
import com.sxmoc.bq.util.ACache;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GetJsonDataUtil;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShenQIngSYHHRFragment extends ZjbBaseFragment implements View.OnClickListener {


    private View mInflate;
    private TextView textBaoZhengJin;
    private int grade = -1;
    private TextView textShiYeHHR;
    private TextView textCHanPin;
    private TextView textAddress;
    private EditText editRealName;
    private EditText editPhone;
    private EditText editCard;
    private EditText textAddressDetail;
    private CheckBox checkBox;
    private EditText editCompany;
    private UserApplybefore userApplybefore;
    private View[] jieMian = new View[3];
    private ImageView imageShenHe2;
    private TextView textShenHeTitle2;
    private TextView textShenHeDes2;
    private ImageView imageShenHe1;
    private TextView textShenHeTitle1;
    private TextView textShenHeDes1;
    private ImageView imageChengGong;
    private TextView textCode;
    private TextView textCompany;
    private TextView textAccount;
    private TextView textBank;
    private TextView textReceiving;
    private List<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private List<ProvinceBean> jsonBean;
    private View lineShangJi;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 写子线程中的操作,解析省市区数据
                initJsonData();
            }
        }).start();
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
        jieMian[0] = mInflate.findViewById(R.id.viewZiLiaoTX);
        jieMian[1] = mInflate.findViewById(R.id.viewShenHeZhong);
        jieMian[2] = mInflate.findViewById(R.id.viewShengHeCG);
        imageShenHe2 = jieMian[2].findViewById(R.id.imageShenHe);
        textShenHeTitle2 = jieMian[2].findViewById(R.id.textShenHeTitle);
        textShenHeDes2 = jieMian[2].findViewById(R.id.textShenHeDes);
        imageShenHe1 = jieMian[1].findViewById(R.id.imageShenHe);
        textShenHeTitle1 = jieMian[1].findViewById(R.id.textShenHeTitle);
        textShenHeDes1 = jieMian[1].findViewById(R.id.textShenHeDes);
        imageChengGong = mInflate.findViewById(R.id.imageChengGong);
        textCode = mInflate.findViewById(R.id.textCode);
        textCompany = mInflate.findViewById(R.id.textCompany);
        textAccount = mInflate.findViewById(R.id.textAccount);
        textBank = mInflate.findViewById(R.id.textBank);
        textReceiving = mInflate.findViewById(R.id.textReceiving);
        lineShangJi = mInflate.findViewById(R.id.lineShangJi);
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getShenQingQianOkObject() {
        String url = Constant.HOST + Constant.Url.USER_APPLYBEFORE;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        return new OkObject(params, url);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setListeners() {
        mInflate.findViewById(R.id.viewShiYEHHR).setOnClickListener(this);
        mInflate.findViewById(R.id.viewAddress).setOnClickListener(this);
        mInflate.findViewById(R.id.btnTiJiao).setOnClickListener(this);
        mInflate.findViewById(R.id.textXieYi).setOnClickListener(this);
        mInflate.findViewById(R.id.viewShiBieMa).setOnClickListener(this);
        mInflate.findViewById(R.id.viewGongSiMingCheng).setOnClickListener(this);
        mInflate.findViewById(R.id.viewDuiGongZhangHao).setOnClickListener(this);
        mInflate.findViewById(R.id.viewKiaHuHang).setOnClickListener(this);
        mInflate.findViewById(R.id.viewFuKuanJinE).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        ApiClient.post(mContext, getShenQingQianOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("WoDeFragment--onSuccess", s + "");
                try {
                    userApplybefore = GsonUtils.parseJSON(s, UserApplybefore.class);
                    if (userApplybefore.getStatus() == 1) {
                        switch (userApplybefore.getState()) {
                            case 0:
                                setJieMian(1);
                                UserApplybefore.Bank bank = userApplybefore.getBank();
                                imageShenHe1.setImageResource(R.mipmap.shenhezhong);
                                textShenHeTitle1.setText(userApplybefore.getTipsTitle());
                                textShenHeDes1.setText(userApplybefore.getTipsDes());
                                if (bank != null) {
                                    textCode.setText(bank.getCode());
                                    textCompany.setText(bank.getCompany());
                                    textAccount.setText(bank.getAccount());
                                    textBank.setText(bank.getBank());
                                    textReceiving.setText(bank.getReceiving());
                                }
                                break;
                            case 1:
                                setJieMian(2);
                                imageShenHe2.setImageResource(R.mipmap.shenhechenggong);
                                textShenHeTitle2.setText(userApplybefore.getTipsTitle());
                                textShenHeDes2.setText(userApplybefore.getTipsDes());
                                imageChengGong.setImageResource(R.mipmap.shengji);
                                imageChengGong.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        setJieMian(0);
                                    }
                                });
                                editRealName.setText(userApplybefore.getData().getName());
                                editPhone.setText(userApplybefore.getData().getMobile());
                                editCard.setText(userApplybefore.getData().getCard());
                                textAddress.setText(userApplybefore.getData().getArea());
                                textAddressDetail.setText(userApplybefore.getData().getAddress());
                                if (userApplybefore.getIsup() == 1) {
                                    imageChengGong.setVisibility(View.VISIBLE);
                                    lineShangJi.setVisibility(View.VISIBLE);
                                } else {
                                    imageChengGong.setVisibility(View.GONE);
                                    lineShangJi.setVisibility(View.GONE);
                                }
                                break;
                            case 2:
                                setJieMian(2);
                                imageShenHe2.setImageResource(R.mipmap.shenheshibai);
                                textShenHeTitle2.setText(userApplybefore.getTipsTitle());
                                textShenHeDes2.setText(userApplybefore.getTipsDes());
                                imageChengGong.setImageResource(R.mipmap.chongxinshenqing);
                                imageChengGong.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        setJieMian(0);
                                    }
                                });
                                editRealName.setText(userApplybefore.getData().getName());
                                editPhone.setText(userApplybefore.getData().getMobile());
                                editCard.setText(userApplybefore.getData().getCard());
                                textAddress.setText(userApplybefore.getData().getArea());
                                textAddressDetail.setText(userApplybefore.getData().getAddress());
                                break;
                            case 3:
                                setJieMian(0);
                                break;
                            default:
                                setJieMian(0);
                                break;
                        }
                    } else if (userApplybefore.getStatus() == 3) {
                        MyDialog.showReLoginDialog(mContext);
                    } else {
                        Toast.makeText(mContext, userApplybefore.getInfo(), Toast.LENGTH_SHORT).show();
                    }
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

    public void setJieMian(int index) {
        for (int i = 0; i < jieMian.length; i++) {
            jieMian[i].setVisibility(View.GONE);
        }
        jieMian[index].setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.RequestResultCode.CITY && resultCode == Constant.RequestResultCode.CITY) {
            IndexCitylist.CityEntity.ListEntity cityBean = (IndexCitylist.CityEntity.ListEntity) data.getSerializableExtra(Constant.IntentKey.BEAN);
            textAddress.setText(cityBean.getName());
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.viewShiBieMa:
                fuZhi(textCode.getText().toString().trim());
                break;
            case R.id.viewGongSiMingCheng:
                fuZhi(textCompany.getText().toString().trim());
                break;
            case R.id.viewDuiGongZhangHao:
                fuZhi(textAccount.getText().toString().trim());
                break;
            case R.id.viewKiaHuHang:
                fuZhi(textBank.getText().toString().trim());
                break;
            case R.id.viewFuKuanJinE:
                fuZhi(textReceiving.getText().toString().trim());
                break;
            case R.id.textXieYi:
                intent.setClass(mContext, WebActivity.class);
                intent.putExtra(Constant.IntentKey.TITLE, "合作协议");
                intent.putExtra(Constant.IntentKey.URL, Constant.Url.PARTNER);
                startActivity(intent);
                break;
            case R.id.btnTiJiao:
                if (grade == -1) {
                    Toast.makeText(mContext, "请选择事业合伙人等级", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (TextUtils.isEmpty(editCompany.getText().toString().trim())) {
//                    Toast.makeText(mContext, "请填写公司名称", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if (TextUtils.isEmpty(editRealName.getText().toString().trim())) {
                    Toast.makeText(getContext(), "请填写真实姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editPhone.getText().toString().trim())) {
                    Toast.makeText(mContext, "请填写联系电话", Toast.LENGTH_SHORT).show();
                    return;
                }
//                CheckIdCard checkIdCard = new CheckIdCard(editCard.getText().toString().trim());
//                if (!checkIdCard.validate()) {
//                    Toast.makeText(mContext, "请输入正确的身份证号", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if (TextUtils.isEmpty(textAddress.getText().toString().trim())) {
                    Toast.makeText(mContext, "请选择寄货城市", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(textAddressDetail.getText().toString().trim())) {
                    Toast.makeText(mContext, "请填写寄货详细地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!checkBox.isChecked()) {
                    Toast.makeText(mContext, "请阅读并同意《合作协议》", Toast.LENGTH_SHORT).show();
                    return;
                }
                tiJiao();
                break;
            case R.id.viewAddress:
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(mContext.getWindow().getDecorView().getWindowToken(),
                            0);
                }
                /**
                 * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
                 */

                OptionsPickerView pvOptions = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        textAddress.setText(options1Items.get(options1).getPickerViewText() + "-" + options2Items.get(options1).get(options2) + "-" + options3Items.get(options1).get(options2).get(options3));
                    }
                })
                        .setTitleText("地区选择")
                        .setContentTextSize(20)//设置滚轮文字大小
                        .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                        .setSelectOptions(0, 0, 0)//默认选中项
                        .setBgColor(getResources().getColor(R.color.background))
                        .setTitleBgColor(getResources().getColor(R.color.white))
                        .setTitleColor(getResources().getColor(R.color.light_black))
                        .setCancelColor(getResources().getColor(R.color.text_gray))
                        .setSubmitColor(getResources().getColor(R.color.basic_color))
                        .setTextColorCenter(getResources().getColor(R.color.basic_color))
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .setBackgroundId(0x66000000) //设置外部遮罩颜色
                        .build();

                //pvOptions.setSelectOptions(1,1);
        /*pvOptions.setPicker(options1Items);//一级选择器*/
//                pvOptions.setPicker(options1Items, options2Items);//二级选择器
                pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
                pvOptions.show();
                break;
            case R.id.viewShiYEHHR:
                final String[] strings = new String[userApplybefore.getGrade().size()];
                for (int i = 0; i < userApplybefore.getGrade().size(); i++) {
                    strings[i] = userApplybefore.getGrade().get(i).getName() + userApplybefore.getGrade().get(i).getMoney();
                }
                new AlertDialog.Builder(mContext)
                        .setItems(strings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                textBaoZhengJin.setText(userApplybefore.getGrade().get(i).getMoney());
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
     * 复制文本
     */
    private void fuZhi(String text) {
        ClipboardManager cmb = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(text); //将内容放入粘贴管理器,在别的地方长按选择"粘贴"即可
        Toast.makeText(getContext(), "复制文本成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject() {
        ACache aCache = ACache.get(mContext, Constant.Acache.LOCATION);
        String did = aCache.getAsString(Constant.Acache.DID);
        String url = Constant.HOST + Constant.Url.USER_APPLY;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
//        params.put("company", editCompany.getText().toString().trim());
        params.put("name", editRealName.getText().toString().trim());
        params.put("mobile", editPhone.getText().toString().trim());
        params.put("card", editCard.getText().toString().trim());
        params.put("address", textAddressDetail.getText().toString().trim());
        params.put("did", did);
        params.put("grade", grade + "");
        params.put("area", textAddress.getText().toString().trim());
        return new OkObject(params, url);
    }

    /**
     * 提交
     */
    private void tiJiao() {
        showLoadingDialog();
        ApiClient.post(mContext, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("ShenQIngSYHHRFragment--onSuccess", s + "");
                try {
                    UserApply userApply = GsonUtils.parseJSON(s, UserApply.class);
                    if (userApply.getStatus() == 1) {
                        initData();
                    } else if (userApply.getStatus() == 3) {
                        MyDialog.showReLoginDialog(mContext);
                    } else {
                        Toast.makeText(mContext, userApply.getInfo(), Toast.LENGTH_SHORT).show();
                    }
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

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(mContext, "province.json");//获取assets目录下的json文件数据
        Type type = new TypeToken<ArrayList<ProvinceBean>>() {
        }.getType();
        jsonBean = GsonUtils.parseJSONArray(JsonData, type);

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCity().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCity().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCity().get(c).getArea() == null
                        || jsonBean.get(i).getCity().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCity().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCity().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
        LogUtil.LogShitou("XinZengDZActivity--options1Items", "" + options1Items.size());
        LogUtil.LogShitou("XinZengDZActivity--options2Items", "" + options2Items.size());
        LogUtil.LogShitou("XinZengDZActivity--options3Items", "" + options3Items.size());
    }
}
