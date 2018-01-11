package com.sxmoc.bq.model;

import android.bluetooth.BluetoothGattCharacteristic;

import com.clj.fastble.data.BleDevice;

/**
 * Created by zhangjiebo on 2018/1/11/011.
 *
 * @author ZhangJieBo
 */

public class BlueBean {
    private BleDevice bleDevice;
    private int Statue ;
    private BluetoothGattCharacteristic bluetoothGattCharacteristic;

    public BlueBean(BleDevice bleDevice,  int statue) {
        this.bleDevice = bleDevice;
        Statue = statue;
    }

    public BluetoothGattCharacteristic getBluetoothGattCharacteristic() {
        return bluetoothGattCharacteristic;
    }

    public void setBluetoothGattCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        this.bluetoothGattCharacteristic = bluetoothGattCharacteristic;
    }

    public BleDevice getBleDevice() {
        return bleDevice;
    }

    public void setBleDevice(BleDevice bleDevice) {
        this.bleDevice = bleDevice;
    }


    public int getStatue() {
        return Statue;
    }

    public void setStatue(int statue) {
        Statue = statue;
    }
}
