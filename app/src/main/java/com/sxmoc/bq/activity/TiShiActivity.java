package com.sxmoc.bq.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.sxmoc.bq.R;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.util.LogUtil;
import com.sxmoc.bq.util.ScreenUtils;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * des： 网页
 * author： zhangjiebo
 * date： 2017/7/6 0006 下午 1:40
 */
public class TiShiActivity extends ZjbBaseActivity implements View.OnClickListener , EasyPermissions.PermissionCallbacks {

//    private WebView mWebView;
//    private String mUrl;
//    private String title;
//    private WebSettings mSettings;
//    private ProgressBar pb1;
    private TextView mTv_title;
    private View viewBar;
    private Button btnZhiDao;
    private int type;
    private int bid;
    private static final int LOCATION = 1991;
    private BroadcastReceiver reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                    switch (blueState) {
                        case BluetoothAdapter.STATE_TURNING_ON:
                            break;
                        case BluetoothAdapter.STATE_ON:
                            cancelLoadingDialog();
                            //开始扫描
                            test();
                            break;
                        case BluetoothAdapter.STATE_TURNING_OFF:
                            break;
                        case BluetoothAdapter.STATE_OFF:
                            break;
                        default:
                            break;
                    }
                    break;
                default:

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_tishi);
        init();
    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
//        mUrl = intent.getStringExtra(Constant.IntentKey.URL);
//        title = intent.getStringExtra(Constant.IntentKey.TITLE);
        type = intent.getIntExtra(Constant.IntentKey.TYPE, 0);
        bid = intent.getIntExtra(Constant.IntentKey.ID, 0);
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void findID() {
        viewBar = findViewById(R.id.viewBar);
//        mWebView = (WebView) findViewById(R.id.webView);
//        pb1 = (ProgressBar) findViewById(R.id.progressBar2);
        mTv_title = (TextView) findViewById(R.id.textViewTitle);
        btnZhiDao = (Button) findViewById(R.id.btnZhiDao);
    }

    @Override
    protected void initViews() {
        ViewGroup.LayoutParams layoutParams = viewBar.getLayoutParams();
        layoutParams.height = (int) (getResources().getDimension(R.dimen.titleHeight) + ScreenUtils.getStatusBarHeight(this));
        viewBar.setLayoutParams(layoutParams);
        ((TextView)findViewById(R.id.textViewTitle)).setText("注意事项");
//        if (!TextUtils.isEmpty(title)) {
//            mTv_title.setText(title);
//        }
//        mWebView.loadUrl(mUrl);
//        mWebView.setWebViewClient(new WebViewClient());//覆盖第三方浏览器
//        mSettings = mWebView.getSettings();
//        mSettings.setJavaScriptEnabled(true);
//        mSettings.setUseWideViewPort(true);
//        mSettings.setLoadWithOverviewMode(true);
//        mWebView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//                pb1.setProgress(newProgress);
//                if (newProgress == 100) {
//                    pb1.setVisibility(View.GONE);
//                } else {
//                    pb1.setVisibility(View.VISIBLE);
//                }
//            }
//        });
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        btnZhiDao.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnZhiDao:
                if (type==1){
                    boolean supportBle = BleManager.getInstance().isSupportBle();
                    if (!supportBle) {
                        MyDialog.showTipDialog(TiShiActivity.this, "该设备不支持蓝牙");
                        return;
                    }
                    if (!checkGPSIsOpen()) {
                        LogUtil.LogShitou("WelcomeActivity--onCreate", "GPS未开启");
                        openGPSSettings();
                    } else {
                        LogUtil.LogShitou("WelcomeActivity--onCreate", "GPS开启");
                        methodRequiresTwoPermission();
                    }
                }else {
                    finish();
                }
                break;
            case R.id.imageBack:
                back();
                break;
            default:

                break;
        }
    }

    /**
     * 检测GPS是否打开
     *
     * @return
     */
    private boolean checkGPSIsOpen() {
        boolean isOpen;
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        isOpen = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
        return isOpen;
    }

    private int GPS_REQUEST_CODE = 10;

    /**
     * 跳转GPS设置
     */
    private void openGPSSettings() {
        if (checkGPSIsOpen()) {
            methodRequiresTwoPermission();
        } else {
            //没有打开则弹出对话框
            new AlertDialog.Builder(this)
                    .setTitle(R.string.notifyTitle)
                    .setMessage(R.string.gpsNotifyMsg)
                    // 拒绝, 退出应用
                    .setNegativeButton(R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })

                    .setPositiveButton(R.string.setting,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //跳转GPS设置界面
                                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivityForResult(intent, GPS_REQUEST_CODE);
                                }
                            })

                    .setCancelable(false)
                    .show();

        }
    }

    private void test() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            showLoadingDialog();
            bluetoothAdapter.enable();
        } else {
            kaiShi();
        }
    }

    /**
     * 跳转测试
     */
    private void kaiShi() {
        Intent intent = new Intent();
        intent.putExtra(Constant.IntentKey.ID, bid);
        intent.setClass(TiShiActivity.this, NaoBoActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_REQUEST_CODE) {
            //做需要做的事情，比如再次检测是否打开GPS了 或者定位
            openGPSSettings();
        }
    }

    @AfterPermissionGranted(LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            test();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "需要开启定位权限",
                    LOCATION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(this, "开启定位成功", Toast.LENGTH_SHORT)
                    .show();
            test();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        } else {
            methodRequiresTwoPermission();
        }
    }

    private void back() {
//        if (mWebView.canGoBack()) {
//            mWebView.goBack();
//        } else {
            finish();
//        }
    }

    @Override
    public void onBackPressed() {
        back();
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(reciver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reciver);
    }
}
