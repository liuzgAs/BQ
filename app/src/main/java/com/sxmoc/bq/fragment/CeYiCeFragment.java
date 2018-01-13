package com.sxmoc.bq.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.sxmoc.bq.R;
import com.sxmoc.bq.activity.NaoBoActivity;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseFragment;
import com.sxmoc.bq.customview.TwoBtnDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CeYiCeFragment extends ZjbBaseFragment implements View.OnClickListener {


    private View mInflate;


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
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initViews() {

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
                startTest();

                break;
            default:
                break;
        }
    }

    private void startTest() {
        //                Intent intent = new Intent();
//                intent.setClass(getActivity(), XinXiTXActivity.class);
//                startActivity(intent);
        Intent intent = new Intent();
        intent.setClass(getActivity(), NaoBoActivity.class);
        startActivity(intent);
    }


    private void startCeShi() {
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
                    startTest();
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
                startTest();
            }
        }
    }
}
