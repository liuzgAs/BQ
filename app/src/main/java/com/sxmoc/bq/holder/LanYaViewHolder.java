package com.sxmoc.bq.holder;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.utils.HexUtil;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.sxmoc.bq.R;
import com.sxmoc.bq.activity.MainActivity;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.model.BlueBean;
import com.sxmoc.bq.util.LogUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class LanYaViewHolder extends BaseViewHolder<BlueBean> {

    private final TextView textName;
    private final TextView textDes;
    private final Button btnLianJie;
    private final TextView textStatue;
    BlueBean data;

    public LanYaViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        textName = $(R.id.textName);
        textDes = $(R.id.textDes);
        btnLianJie = $(R.id.btnLianJie);
        btnLianJie.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View view) {
                if (data.getStatue() == 0) {
                    ((MainActivity) getContext()).showLoadingDialog();
                    connect();
                } else if (data.getStatue() == 1) {
                    caoZuo();
                } else {
                    BluetoothGatt bluetoothGatt = BleManager.getInstance().getBluetoothGatt(data.getBleDevice());
                    final List<BluetoothGattService> services = bluetoothGatt.getServices();
                    List<BluetoothGattCharacteristic> characteristics = services.get(2).getCharacteristics();
                    final BluetoothGattCharacteristic bluetoothGattCharacteristic = characteristics.get(0);
                    BleManager.getInstance().stopNotify(data.getBleDevice(), bluetoothGattCharacteristic.getService().getUuid().toString(),
                            bluetoothGattCharacteristic.getUuid().toString());
                    data.setStatue(1);
                    btnLianJie.setText("测试");
                }
            }
        });
        textStatue = $(R.id.textStatue);
        textStatue.setVisibility(View.GONE);
    }

    /**
     * 操作
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void caoZuo() {
        BluetoothGatt bluetoothGatt = BleManager.getInstance().getBluetoothGatt(data.getBleDevice());
        final List<BluetoothGattService> services = bluetoothGatt.getServices();
        List<BluetoothGattCharacteristic> characteristics = services.get(2).getCharacteristics();
        final BluetoothGattCharacteristic bluetoothGattCharacteristic = characteristics.get(0);
        BleManager.getInstance().notify(
                data.getBleDevice(),
                bluetoothGattCharacteristic.getService().getUuid().toString(),
                bluetoothGattCharacteristic.getUuid().toString(),
                new BleNotifyCallback() {
                    @Override
                    public void onNotifySuccess() {
                        // 打开通知操作成功
                        LogUtil.LogShitou("MainActivity--onNotifySuccess", "打开通知操作成功");
                        data.setStatue(2);
                        btnLianJie.setText("取消测试");
                    }

                    @Override
                    public void onNotifyFailure(BleException exception) {
                        LogUtil.LogShitou("MainActivity--onNotifyFailure", "打开通知操作失败");
                        // 打开通知操作失败
                        MyDialog.showTipDialog(getContext(), "打开通知操作失败");
                    }

                    @Override
                    public void onCharacteristicChanged(byte[] data) {
                        // 打开通知后，设备发过来的数据将在这里出现
                        LogUtil.LogShitou("MainActivity--onCharacteristicChanged", HexUtil.formatHexString(data, true));
                    }
                });
    }

    @Override
    public void setData(BlueBean data) {
        super.setData(data);
        this.data = data;
        BleDevice bleDevice = data.getBleDevice();
        textName.setText(bleDevice.getName());
        textDes.setText(bleDevice.getMac());
        boolean connected = BleManager.getInstance().isConnected(bleDevice);
        if (connected) {
            textStatue.setVisibility(View.VISIBLE);
            btnLianJie.setText("测试");
            data.setStatue(1);
        } else {
            textStatue.setVisibility(View.GONE);
            btnLianJie.setText("连接");
            data.setStatue(0);
        }
    }

    private void connect() {
        BleManager.getInstance().connect(data.getBleDevice(), new BleGattCallback() {
            @Override
            public void onStartConnect() {
                // 开始连接
                LogUtil.LogShitou("MainActivity--onStartConnect", "开始连接");
            }

            @Override
            public void onConnectFail(BleException exception) {
                // 连接失败
                LogUtil.LogShitou("MainActivity--onConnectFail", "连接失败");
                ((MainActivity) getContext()).cancelLoadingDialog();
                MyDialog.showTipDialog(getContext(), "连接失败");
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onConnectSuccess(final BleDevice bleDevice, BluetoothGatt gatt, int status) {
                data.setStatue(1);
                textStatue.setVisibility(View.VISIBLE);
                btnLianJie.setText("测试");
                ((MainActivity) getContext()).cancelLoadingDialog();
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                // 连接中断，isActiveDisConnected表示是否是主动调用了断开连接方法
                LogUtil.LogShitou("MainActivity--onDisConnected", "连接中断");
                data.setStatue(0);
                textStatue.setVisibility(View.GONE);
                btnLianJie.setText("连接");
            }
        });
    }

}
