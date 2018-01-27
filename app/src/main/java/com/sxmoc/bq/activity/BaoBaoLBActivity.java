package com.sxmoc.bq.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.sxmoc.bq.R;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.base.ZjbBaseActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.holder.BaoBaoViewHolder;
import com.sxmoc.bq.model.OkObject;
import com.sxmoc.bq.model.TesterGettester;
import com.sxmoc.bq.util.ApiClient;
import com.sxmoc.bq.util.GsonUtils;
import com.sxmoc.bq.util.LogUtil;

import java.util.HashMap;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class BaoBaoLBActivity extends ZjbBaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, EasyPermissions.PermissionCallbacks {
    private static final int LOCATION = 1991;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<TesterGettester.DataBean> adapter;
    private BroadcastReceiver reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.BroadcastCode.KAISHICESHI:
                    onRefresh();
                    break;
                case Constant.BroadcastCode.XIUGAIBAOBAO:
                    onRefresh();
                    break;
                default:

                    break;
            }
        }
    };
    private int bid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_bao_lb);
        BleManager.getInstance().init(getApplication());
        init();
    }

    @Override
    protected void initSP() {

    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void findID() {
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);

    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.textViewTitle)).setText("选择宝宝");
        initRecycler();
    }

    /**
     * 初始化recyclerview
     */
    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerDecoration itemDecoration = new DividerDecoration(Color.TRANSPARENT, (int) getResources().getDimension(R.dimen.line_width), 0, 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setRefreshingColorResources(R.color.basic_color);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<TesterGettester.DataBean>(BaoBaoLBActivity.this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                int layout = R.layout.item_bb;
                return new BaoBaoViewHolder(parent, layout);
            }
        });
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                ApiClient.post(BaoBaoLBActivity.this, getOkObject(), new ApiClient.CallBack() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtil.LogShitou("DingDanGLActivity--加载更多", s + "");
                        try {
                            page++;
                            TesterGettester testerGettester = GsonUtils.parseJSON(s, TesterGettester.class);
                            int status = testerGettester.getStatus();
                            if (status == 1) {
                                List<TesterGettester.DataBean> dataBeanList = testerGettester.getData();
                                adapter.addAll(dataBeanList);
                            } else if (status == 3) {
                                MyDialog.showReLoginDialog(BaoBaoLBActivity.this);
                            } else {
                                adapter.pauseMore();
                            }
                        } catch (Exception e) {
                            adapter.pauseMore();
                        }
                    }

                    @Override
                    public void onError() {
                        adapter.pauseMore();
                    }
                });
            }

            @Override
            public void onMoreClick() {

            }
        });
        adapter.setNoMore(R.layout.view_nomore, new RecyclerArrayAdapter.OnNoMoreListener() {
            @Override
            public void onNoMoreShow() {

            }

            @Override
            public void onNoMoreClick() {
            }
        });
        adapter.setError(R.layout.view_error, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
                adapter.resumeMore();
            }

            @Override
            public void onErrorClick() {
                adapter.resumeMore();
            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                intent.setClass(BaoBaoLBActivity.this, TiShiActivity.class);
                intent.putExtra(Constant.IntentKey.TITLE, "注意事项");
                intent.putExtra(Constant.IntentKey.URL, Constant.Url.PRECAUTIONS);
                intent.putExtra(Constant.IntentKey.TYPE,1);
                intent.putExtra(Constant.IntentKey.ID,adapter.getItem(position).getBid());
                startActivity(intent);
                finish();
            }
        });
        recyclerView.setRefreshListener(this);
    }

    @Override
    protected void setListeners() {
        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.btnXinZeng).setOnClickListener(this);
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
        intent.setClass(BaoBaoLBActivity.this, NaoBoActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void initData() {
        onRefresh();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnXinZeng:
                Intent intent = new Intent();
                intent.setClass(this, XinXiTXActivity.class);
                startActivity(intent);
                break;
            case R.id.imageBack:
                finish();
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
        String url = Constant.HOST + Constant.Url.TESTER_GETTESTER;
        HashMap<String, String> params = new HashMap<>();
        if (isLogin) {
            params.put("uid", userInfo.getUid());
            params.put("tokenTime", tokenTime);
        }
        params.put("p", String.valueOf(page));
        return new OkObject(params, url);
    }

    private int page = 1;

    @Override
    public void onRefresh() {
        page = 1;
        ApiClient.post(this, getOkObject(), new ApiClient.CallBack() {
            @Override
            public void onSuccess(String s) {
                LogUtil.LogShitou("宝宝列表", s);
                try {
                    page++;
                    TesterGettester testerGettester = GsonUtils.parseJSON(s, TesterGettester.class);
                    if (testerGettester.getStatus() == 1) {
                        List<TesterGettester.DataBean> dataBeanList = testerGettester.getData();
                        adapter.clear();
                        adapter.addAll(dataBeanList);
                    } else if (testerGettester.getStatus() == 3) {
                        MyDialog.showReLoginDialog(BaoBaoLBActivity.this);
                    } else {
                        showError(testerGettester.getInfo());
                    }
                } catch (Exception e) {
                    showError("数据出错");
                }
            }

            @Override
            public void onError() {
                showError("网络出错");
            }

            /**
             * 错误显示
             * @param msg
             */
            private void showError(String msg) {
                try {
                    View viewLoader = LayoutInflater.from(BaoBaoLBActivity.this).inflate(R.layout.view_loaderror, null);
                    TextView textMsg = viewLoader.findViewById(R.id.textMsg);
                    textMsg.setText(msg);
                    viewLoader.findViewById(R.id.buttonReLoad).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recyclerView.showProgress();
                            initData();
                        }
                    });
                    recyclerView.setErrorView(viewLoader);
                    recyclerView.showError();
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BroadcastCode.KAISHICESHI);
        filter.addAction(Constant.BroadcastCode.XIUGAIBAOBAO);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(reciver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reciver);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_REQUEST_CODE) {
            //做需要做的事情，比如再次检测是否打开GPS了 或者定位
            openGPSSettings();
        }
    }


//    @Override
//    public void onBackPressed() {
//        if (isBack){
//            super.onBackPressed();
//        }
//    }
}
