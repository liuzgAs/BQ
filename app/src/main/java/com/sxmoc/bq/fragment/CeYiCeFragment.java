package com.sxmoc.bq.fragment;


import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.sxmoc.bq.customview.TwoBtnDialog;
import com.sxmoc.bq.holder.LanYaViewHolder;
import com.sxmoc.bq.model.BlueBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CeYiCeFragment extends ZjbBaseFragment implements View.OnClickListener {


    private View mInflate;
    private View viewKaiShiJC;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<BlueBean> adapter;
    private String saoMiaoStatue = "正在搜索……";
    private int jieMian = 0;

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
        viewKaiShiJC = mInflate.findViewById(R.id.viewKaiShiJC);
        recyclerView = mInflate.findViewById(R.id.recyclerView);
    }

    @Override
    protected void initViews() {
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
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<BlueBean>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_lanya;
                return new LanYaViewHolder(parent, layout);
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
            BlueBean blueBean = new BlueBean(allConnectedDevice.get(i),  0);
            blueBeanList.add(blueBean);
        }
        adapter.clear();
        adapter.addAll(blueBeanList);
        viewKaiShiJC.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        jieMian = 1;
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
                    BlueBean blueBean = new BlueBean(scanResultList.get(i),  0);
                    blueBeanList.add(blueBean);
                }
                adapter.addAll(blueBeanList);
                adapter.notifyDataSetChanged();
                if (adapter.getAllData().size()==0){
                    MyDialog.showTipDialog(getContext(),"没有发现设备，请确认设备是否开机");
                    jieMian=0;
                    recyclerView.setVisibility(View.GONE);
                    viewKaiShiJC.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        if (jieMian == 1) {
            jieMian = 0;
            recyclerView.setVisibility(View.GONE);
            viewKaiShiJC.setVisibility(View.VISIBLE);
            return true;
        } else {
            return super.onBackPressed();
        }

    }
}
