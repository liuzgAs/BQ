package com.sxmoc.bq.fragment;


import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxmoc.bq.R;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseFragment;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.customview.NaoBoTu;
import com.sxmoc.bq.customview.RoateImg;
import com.sxmoc.bq.customview.TwoBtnDialog;
import com.sxmoc.bq.holder.LanYaViewHolder;
import com.sxmoc.bq.model.BlueBean;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.SimpleInfo;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;
import com.sxmoc.bq.util.ScreenUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CeYiCeFragment extends ZjbBaseFragment implements View.OnClickListener {


    private View mInflate;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<BlueBean> adapter;
    private String saoMiaoStatue = "正在搜索……";
    private int jieMian = 0;
    private NaoBoTu naoBo01;
    private NaoBoTu naoBo02;
    private LanYaViewHolder lanYaViewHolder;
    private TextView textLeftTime;
    private TextView textZuoNaoDis;
    private TextView textYouNaoDis;
    private View[] viewJieMian = new View[5];
    private RelativeLayout viewShangChuan;
    private RoateImg roateImg;
    private RelativeLayout viewDuQuBG;
    ObjectAnimator[] animator = new ObjectAnimator[5];
    private TextView textShangChuanStatue;
    private int screenWidth;

    public CeYiCeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_ce_yi_ce, container, false);
            BleManager.getInstance().init(getActivity().getApplication());
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
        viewJieMian[0] = mInflate.findViewById(R.id.viewKaiShiJC);
        recyclerView = mInflate.findViewById(R.id.recyclerView);
        viewJieMian[1] = mInflate.findViewById(R.id.recyclerView);
        viewJieMian[2] = mInflate.findViewById(R.id.viewNaoBo);
        naoBo01 = mInflate.findViewById(R.id.naoBo01);
        naoBo02 = mInflate.findViewById(R.id.naoBo02);
        textLeftTime = mInflate.findViewById(R.id.textLeftTime);
        textZuoNaoDis = mInflate.findViewById(R.id.textZuoNaoDis);
        textYouNaoDis = mInflate.findViewById(R.id.textYouNaoDis);
        viewShangChuan = mInflate.findViewById(R.id.viewShangChuan);
        viewDuQuBG = mInflate.findViewById(R.id.viewDuQuBG);
        viewJieMian[3] = mInflate.findViewById(R.id.viewShangChuan);
        viewJieMian[4] = mInflate.findViewById(R.id.viewDuQuBG);
        roateImg = mInflate.findViewById(R.id.roateImg);
        textShangChuanStatue = mInflate.findViewById(R.id.textShangChuanStatue);
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initViews() {
        initRecycler();
        viewJieMian[2].setPadding(0, ScreenUtils.getStatusBarHeight(getActivity()), 0, 0);

        screenWidth = ScreenUtils.getScreenWidth(getActivity());
        ImageView imageView1 = new ImageView(getActivity());
        imageView1.setImageResource(R.mipmap.jianbianquan);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams((int) ((float) screenWidth * 0.6f), (int) ((float) screenWidth * 0.6f));
        layoutParams1.addRule(RelativeLayout.CENTER_IN_PARENT);
        viewShangChuan.addView(imageView1, layoutParams1);
        for (int i = 0; i < 5; i++) {
            final ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(R.mipmap.jianbianquan);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) ((float) screenWidth * 0.6f), (int) ((float) screenWidth * 0.6f));
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            viewShangChuan.addView(imageView, layoutParams);
            if (i > 0) {
                imageView.setVisibility(View.GONE);
            }
            PropertyValuesHolder holder01 = PropertyValuesHolder.ofFloat("scaleX", 1f, 3f);
            PropertyValuesHolder holder02 = PropertyValuesHolder.ofFloat("scaleY", 1f, 3f);
            PropertyValuesHolder holder03 = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
            animator[i] = ObjectAnimator.ofPropertyValuesHolder(imageView, holder01, holder02, holder03);
            animator[i].setInterpolator(new LinearInterpolator());
            animator[i].setDuration(4000);
            animator[i].setStartDelay(0 + 1000 * i);
            animator[i].setRepeatCount(ValueAnimator.INFINITE);
            animator[i].setRepeatMode(ValueAnimator.INFINITE);
            animator[i].start();
            animator[i].addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    imageView.setVisibility(View.VISIBLE);
                }
            });
        }

        ImageView imageView3 = new ImageView(getActivity());
        imageView3.setImageResource(R.mipmap.jianbianquan);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams((int) ((float) screenWidth * 0.8f), (int) ((float) screenWidth * 0.8f));
        layoutParams3.addRule(RelativeLayout.CENTER_IN_PARENT);
        viewDuQuBG.addView(imageView3, layoutParams3);
        imageView3.setAlpha(0.4f);

        ImageView imageView2 = new ImageView(getActivity());
        imageView2.setImageResource(R.mipmap.jianbianquan);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams((int) ((float) screenWidth * 1f), (int) ((float) screenWidth * 1f));
        layoutParams2.addRule(RelativeLayout.CENTER_IN_PARENT);
        viewDuQuBG.addView(imageView2, layoutParams2);
        imageView2.setAlpha(0.2f);
        setJieMian(0);
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
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<BlueBean>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_lanya;
                lanYaViewHolder = new LanYaViewHolder(parent, layout);
                lanYaViewHolder.setOnNaoBoListener(new LanYaViewHolder.OnNaoBoListener() {
                    @Override
                    public void setNaoBo(int value01, int value02) {
                        naoBo01.setNaoBoPoint(value01);
                        naoBo02.setNaoBoPoint(value02);
                        textZuoNaoDis.setText(String.valueOf(value01));
                        textYouNaoDis.setText(String.valueOf(value02));
                    }

                    @Override
                    public void success() {
                        setJieMian(2);
                    }

                    @Override
                    public void leftTime(int leftTime) {
                        textLeftTime.setText(String.valueOf(120 - leftTime));
                    }

                    @Override
                    public void upLoad(List<String> naoBoDataList) {
                        upLoadData(naoBoDataList);
                        setJieMian(3);
                    }

                });
                return lanYaViewHolder;
            }
        });
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {

            private TextView textSouSuo;

            @Override
            public View onCreateView(ViewGroup parent) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.header_sousuo, null);
                textSouSuo = view.findViewById(R.id.textSouSuo);
                return view;
            }

            @Override
            public void onBindView(View headerView) {
                textSouSuo.setText(saoMiaoStatue);
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }
        });
    }

    /**
     * des： 网络请求参数
     * author： ZhangJieBo
     * date： 2017/8/28 0028 上午 9:55
     */
    private OkObject getOkObject(List<String> naoBoDataList) {
        String url = Constant.HOST + Constant.Url.BUYER_SAVEDATA;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("bid", "1");
        List<String> naoBoDataList00 = new ArrayList<>();
        List<String> naoBoDataList01 = new ArrayList<>();
        List<String> naoBoDataList02 = new ArrayList<>();
        List<String> naoBoDataList03 = new ArrayList<>();
        List<String> naoBoDataList04 = new ArrayList<>();
        List<String> naoBoDataList05 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            naoBoDataList00.add(naoBoDataList.get(i));
        }
        for (int i = 20; i < 40; i++) {
            naoBoDataList01.add(naoBoDataList.get(i));
        }
        for (int i = 40; i < 60; i++) {
            naoBoDataList02.add(naoBoDataList.get(i));
        }
        for (int i = 60; i < 80; i++) {
            naoBoDataList03.add(naoBoDataList.get(i));
        }
        for (int i = 80; i < 100; i++) {
            naoBoDataList04.add(naoBoDataList.get(i));
        }
        for (int i = 100; i < 120; i++) {
            naoBoDataList05.add(naoBoDataList.get(i));
        }
        params.put("raw_data1", GsonUtils.parseObject(naoBoDataList00));
        params.put("raw_data2", GsonUtils.parseObject(naoBoDataList01));
        params.put("raw_data3", GsonUtils.parseObject(naoBoDataList02));
        params.put("raw_data4", GsonUtils.parseObject(naoBoDataList03));
        params.put("raw_data5", GsonUtils.parseObject(naoBoDataList04));
        params.put("raw_data6", GsonUtils.parseObject(naoBoDataList05));
        return new OkObject(params, url);
    }

    /**
     * 上传脑波数据
     *
     * @param naoBoDataList
     */
    private void upLoadData(List<String> naoBoDataList) {
        showLoadingDialog();
        ApiClient.post(getActivity(), getOkObject(naoBoDataList), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                cancelLoadingDialog();
                LogUtil.LogShitou("CeYiCeFragment--onSuccess", s + "");
                try {
                    SimpleInfo simpleInfo = GsonUtils.parseJSON(s, SimpleInfo.class);
                    if (simpleInfo.getStatus() == 1) {
                        shangChuangWanCheng();
                    } else if (simpleInfo.getStatus() == 3) {
                        MyDialog.showReLoginDialog(getActivity());
                    } else {
                        Toast.makeText(getActivity(), simpleInfo.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "数据出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                cancelLoadingDialog();
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 上传完成
     */
    private void shangChuangWanCheng() {
        textShangChuanStatue.setText("上传完成");
        for (int i = 0; i < animator.length; i++) {
            animator[i].end();
        }
        roateImg.stopAnim();
        PropertyValuesHolder holder01 = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.6f);
        PropertyValuesHolder holder03 = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.4f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(roateImg, holder01, holder03);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(500);
        PropertyValuesHolder holder04 = PropertyValuesHolder.ofFloat("scaleY", 0.6f, 1f);
        PropertyValuesHolder holder05 = PropertyValuesHolder.ofFloat("scaleX", 1.4f, 1f);
        ObjectAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(roateImg, holder04, holder05);
        animator2.setInterpolator(new LinearInterpolator());
        animator2.setDuration(200);
        PropertyValuesHolder holder02 = PropertyValuesHolder.ofFloat("translationY", -1500);
        ObjectAnimator animator1 = ObjectAnimator.ofPropertyValuesHolder(roateImg, holder02);
        animator1.setInterpolator(new LinearInterpolator());
        animator1.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animator, animator2, animator1);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setJieMian(4);
            }
        });
    }

    /**
     * 设置界面
     *
     * @param jieMianIndex
     */
    private void setJieMian(int jieMianIndex) {
        jieMian = jieMianIndex;
        for (int i = 0; i < viewJieMian.length; i++) {
            viewJieMian[i].setVisibility(View.GONE);
        }
        viewJieMian[jieMianIndex].setVisibility(View.VISIBLE);
    }

    @Override
    protected void setListeners() {
        mInflate.findViewById(R.id.btnKaiShiJC).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnKaiShiJC:
                boolean supportBle = BleManager.getInstance().isSupportBle();
                if (!supportBle) {
                    MyDialog.showTipDialog(getActivity(), "该设备不支持蓝牙");
                    return;
                }
                final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (!bluetoothAdapter.isEnabled()) {
                    final TwoBtnDialog twoBtnDialog = new TwoBtnDialog(getActivity(), "某个应用要打开你的蓝牙", "允许", "拒绝");
                    twoBtnDialog.setClicklistener(new TwoBtnDialog.ClickListenerInterface() {
                        @Override
                        public void doConfirm() {
                            twoBtnDialog.dismiss();
                            BleManager.getInstance().enableBluetooth();
                        }

                        @Override
                        public void doCancel() {
                            twoBtnDialog.dismiss();
                        }
                    });
                    twoBtnDialog.show();
                } else {
                    checkPermissions();
                }
                break;
            default:
                break;
        }
    }

    private static final int REQUEST_CODE_OPEN_GPS = 1;
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 2;

    private void checkPermissions() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            Toast.makeText(getActivity(), getString(R.string.please_open_blue), Toast.LENGTH_LONG).show();
            return;
        }

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        List<String> permissionDeniedList = new ArrayList<>();
        for (String permission : permissions) {
            int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), permission);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted(permission);
            } else {
                permissionDeniedList.add(permission);
            }
        }
        if (!permissionDeniedList.isEmpty()) {
            String[] deniedPermissions = permissionDeniedList.toArray(new String[permissionDeniedList.size()]);
            ActivityCompat.requestPermissions(getActivity(), deniedPermissions, REQUEST_CODE_PERMISSION_LOCATION);
        }
    }

    private void onPermissionGranted(String permission) {
        switch (permission) {
            case Manifest.permission.ACCESS_FINE_LOCATION:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !checkGPSIsOpen()) {
                    final TwoBtnDialog twoBtnDialog = new TwoBtnDialog(getActivity(), getActivity().getResources().getString(R.string.gpsNotifyMsg), getActivity().getResources().getString(R.string.setting), getActivity().getResources().getString(R.string.cancel));
                    twoBtnDialog.setClicklistener(new TwoBtnDialog.ClickListenerInterface() {
                        @Override
                        public void doConfirm() {
                            twoBtnDialog.dismiss();
                        }

                        @Override
                        public void doCancel() {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, REQUEST_CODE_OPEN_GPS);
                        }
                    });
                    twoBtnDialog.show();
                } else {
                    startScan();
                }
                break;
            default:
                break;
        }
    }

    private boolean checkGPSIsOpen() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            return false;
        }
        return locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OPEN_GPS) {
            if (checkGPSIsOpen()) {
                startScan();
            }
        }
    }

    private void startScan() {
        List<BleDevice> allConnectedDevice = BleManager.getInstance().getAllConnectedDevice();
        List<BlueBean> blueBeanList = new ArrayList<>();
        for (int i = 0; i < allConnectedDevice.size(); i++) {
            BlueBean blueBean = new BlueBean(allConnectedDevice.get(i), 0);
            blueBeanList.add(blueBean);
        }
        adapter.clear();
        adapter.addAll(blueBeanList);
        setJieMian(1);
        showLoadingDialog();
        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
                /**
                 * 只扫描指定广播名的设备，可选
                 */
                .setDeviceName(true, "Mind Link")
                /**
                 * 扫描超时时间，可选，默认10秒
                 */
                .setScanTimeOut(10000)
                .build();
        BleManager.getInstance().initScanRule(scanRuleConfig);
        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {
                saoMiaoStatue = "正在搜索……";
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLeScan(BleDevice bleDevice) {
                super.onLeScan(bleDevice);
            }

            @Override
            public void onScanning(BleDevice bleDevice) {

            }

            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {
                cancelLoadingDialog();
                saoMiaoStatue = "搜索完成";
                List<BlueBean> blueBeanList = new ArrayList<>();
                for (int i = 0; i < scanResultList.size(); i++) {
                    BlueBean blueBean = new BlueBean(scanResultList.get(i), 0);
                    blueBeanList.add(blueBean);
                }
                adapter.addAll(blueBeanList);
                adapter.notifyDataSetChanged();
                if (adapter.getAllData().size() == 0) {
                    MyDialog.showTipDialog(getContext(), "没有发现设备，请确认设备是否开机");
                    setJieMian(0);
                }
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        if (jieMian == 1) {
            setJieMian(0);
            return true;
        } else if (jieMian == 2) {
            final TwoBtnDialog twoBtnDialog = new TwoBtnDialog(getActivity(), "是否终止测试？", "是", "否");
            twoBtnDialog.setClicklistener(new TwoBtnDialog.ClickListenerInterface() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
                @Override
                public void doConfirm() {
                    lanYaViewHolder.closeNotify();
                    setJieMian(1);
                    twoBtnDialog.dismiss();
                }

                @Override
                public void doCancel() {
                    twoBtnDialog.dismiss();
                }
            });
            twoBtnDialog.show();
            return true;
        } else {
            return super.onBackPressed();
        }

    }
}
