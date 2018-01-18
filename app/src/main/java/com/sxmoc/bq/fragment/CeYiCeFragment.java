package com.sxmoc.bq.fragment;


import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
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
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.sxmoc.bq.R;
import com.sxmoc.bq.activity.BaoBaoLBActivity;
import com.sxmoc.bq.base.ZjbBaseFragment;
import com.sxmoc.bq.customview.TwoBtnDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CeYiCeFragment extends ZjbBaseFragment implements View.OnClickListener {


    private View mInflate;
    private ImageView imageNeiQuan;
    private ImageView imageWaiQuan;


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


    private void startCeShi() {

        checkPermissions();
    }

    private static final int REQUEST_CODE_OPEN_GPS = 1;
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 2;

    private void checkPermissions() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            Toast.makeText(getActivity(), getString(R.string.please_open_blue), Toast.LENGTH_LONG).show();
            return;
        }
//        startTest();
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

                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, REQUEST_CODE_OPEN_GPS);
                        }

                        @Override
                        public void doCancel() {
                            twoBtnDialog.dismiss();
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
