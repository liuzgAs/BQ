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
import com.sxmoc.bq.activity.NaoBoActivity;
import com.sxmoc.bq.base.MyDialog;
import com.sxmoc.bq.model.BlueBean;
import com.sxmoc.bq.model.NaoBo;
import com.sxmoc.bq.util.ByteUtils;
import com.sxmoc.bq.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class LanYaViewHolder extends BaseViewHolder<BlueBean> {

    private final TextView textName;
    private final TextView textDes;
    private final Button btnLianJie;
    private final TextView textStatue;
    private int pingJie = 0;
    private String pingJieStr = "";
    private List<List<NaoBo>> naoBoList = new ArrayList<>();
    BlueBean data;
    int num = 0;
    int index = 0;

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
                    ((NaoBoActivity) getContext()).showLoadingDialog();
                    connect();
                } else if (data.getStatue() == 1) {
                    caoZuo();
                } else {
                    closeNotify();
                }
            }
        });
        textStatue = $(R.id.textStatue);
        textStatue.setVisibility(View.GONE);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void closeNotify() {
        BluetoothGatt bluetoothGatt = BleManager.getInstance().getBluetoothGatt(data.getBleDevice());
        if (bluetoothGatt != null) {
            final List<BluetoothGattService> services = bluetoothGatt.getServices();
            List<BluetoothGattCharacteristic> characteristics = services.get(2).getCharacteristics();
            final BluetoothGattCharacteristic bluetoothGattCharacteristic = characteristics.get(0);
            BleManager.getInstance().stopNotify(data.getBleDevice(), bluetoothGattCharacteristic.getService().getUuid().toString(),
                    bluetoothGattCharacteristic.getUuid().toString());
            data.setStatue(1);
            btnLianJie.setText("测试");
        } else {
            textStatue.setVisibility(View.GONE);
            btnLianJie.setText("连接");
            data.setStatue(0);
        }
        BleManager.getInstance().disconnectAllDevice();
        ((NaoBoActivity) getContext()).initNaoBo();
        num = 0;
        index = 0;
    }

    /**
     * 操作
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void caoZuo() {
        num = 0;
        index = 0;
        naoBoList.clear();
        for (int i = 0; i < 120; i++) {
            List<NaoBo> stringList = new ArrayList<>();
            naoBoList.add(stringList);
        }
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
                        LogUtil.LogShitou("NaoBoActivity--onNotifySuccess", "打开通知操作成功");
                        data.setStatue(2);
                        btnLianJie.setText("取消测试");
                        ((NaoBoActivity) getContext()).success();
                    }

                    @Override
                    public void onNotifyFailure(BleException exception) {
                        LogUtil.LogShitou("NaoBoActivity--onNotifyFailure", "打开通知操作失败");
                        // 打开通知操作失败
                        MyDialog.showTipDialog(getContext(), "打开通知操作失败");
                    }

                    @Override
                    public void onCharacteristicChanged(byte[] data) {
                        // 打开通知后，设备发过来的数据将在这里出现
                        /**
                         * a55a026b020f01ff000000000000000005
                         * a55a02e002870295000000000000000004
                         * a55a026b020f01ff000000000000000f05
                         * a55a02e002870295000000000000000f04
                         * a55a02 e0         0287 0295 000000000000000004
                         * 数据头  第几个数据包
                         * 000000000000000004a55a02
                         */
                        String hexString = HexUtil.formatHexString(data, false);
                        LogUtil.LogShitou("LanYaViewHolder--onCharacteristicChanged截取前", "" + hexString);
                        pingJie++;
                        pingJieStr = pingJieStr + hexString;
                        if (pingJie==8){
                            pingJie=0;
                            pingJieStr = "";
                        }
                        String[] split = hexString.split("a55a02");
                        for (int i = 0; i < split.length; i++) {

                            if (split[i].length() == 28) {
                                LogUtil.LogShitou("LanYaViewHolder--onCharacteristicChanged", "截取后" + split[i]);
                                byte[] bytes = HexUtil.hexStringToBytes(split[i]);
                                int zuoNao = ByteUtils.bytesUInt(bytes[1]) * 256 + ByteUtils.bytesUInt(bytes[2]);
                                int youNao = ByteUtils.bytesUInt(bytes[3]) * 256 + ByteUtils.bytesUInt(bytes[4]);
                                LogUtil.LogShitou("LanYaViewHolder--onCharacteristicChanged", "左脑" + zuoNao);
                                LogUtil.LogShitou("LanYaViewHolder--onCharacteristicChanged", "右脑" + youNao);
                                naoBoList.get(index).add(new NaoBo(zuoNao, youNao));
                                if (num % 8 == 0) {
                                    ((NaoBoActivity) getContext()).setNaoBo(zuoNao, youNao);
                                }
                                num++;
                                LogUtil.LogShitou("LanYaViewHolder--onCharacteristicChanged", "num" + num);
                                if (num == 256) {
                                    num = 0;
                                    index++;
                                    ((NaoBoActivity) getContext()).leftTime(index);
                                    LogUtil.LogShitou("LanYaViewHolder--onCharacteristicChanged", "index" + index);
                                    if (index == 120) {
                                        index = 0;
                                        closeNotify();
                                        List<String> naoBoDataList = new ArrayList<>();
                                        for (int j = 0; j < naoBoList.size(); j++) {
                                            naoBoDataList.add("A  " + j * 256);
                                            StringBuffer zuoNaoData = new StringBuffer();
                                            for (int k = 0; k < naoBoList.get(j).size(); k++) {
                                                if (k < naoBoList.get(j).size() - 1) {
                                                    if (naoBoList.get(j).get(k).getZuoNao() > 700) {
                                                        zuoNaoData.append("1000,");
                                                    } else {
                                                        zuoNaoData.append(String.valueOf(naoBoList.get(j).get(k).getZuoNao() + ","));
                                                    }
                                                } else {
                                                    if (naoBoList.get(j).get(k).getZuoNao() > 700) {
                                                        zuoNaoData.append("1000,");
                                                    } else {
                                                        zuoNaoData.append(String.valueOf(naoBoList.get(j).get(k).getZuoNao()));
                                                    }
                                                }
                                            }
                                            naoBoDataList.add(zuoNaoData.toString());
                                            naoBoDataList.add("B  " + j * 256);
                                            StringBuffer youNaoData = new StringBuffer();
                                            for (int k = 0; k < naoBoList.get(j).size(); k++) {
                                                if (k < naoBoList.get(j).size() - 1) {
                                                    if (naoBoList.get(j).get(k).getYouNao() > 700) {
                                                        youNaoData.append("1000,");
                                                    } else {
                                                        youNaoData.append(String.valueOf(naoBoList.get(j).get(k).getYouNao() + ","));
                                                    }
                                                } else {
                                                    if (naoBoList.get(j).get(k).getYouNao() > 700) {
                                                        youNaoData.append("1000,");
                                                    } else {
                                                        youNaoData.append(String.valueOf(naoBoList.get(j).get(k).getYouNao()));
                                                    }
                                                }
                                            }
                                            naoBoDataList.add(youNaoData.toString());
                                        }
                                        LogUtil.LogShitou("LanYaViewHolder--onCharacteristicChanged", "" + naoBoDataList.size());
                                        ((NaoBoActivity) getContext()).upLoad(naoBoDataList);
                                    }
                                }
                            }
                        }
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
            btnLianJie.setText("开始测试");
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
                LogUtil.LogShitou("NaoBoActivity--onStartConnect", "开始连接");
            }

            @Override
            public void onConnectFail(BleException exception) {
                // 连接失败
                LogUtil.LogShitou("NaoBoActivity--onConnectFail", "连接失败");
                ((NaoBoActivity) getContext()).cancelLoadingDialog();
                try {
                    MyDialog.showTipDialog(getContext(), "连接失败");
                } catch (Exception e) {
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onConnectSuccess(final BleDevice bleDevice, BluetoothGatt gatt, int status) {
                data.setStatue(1);
                textStatue.setVisibility(View.VISIBLE);
                btnLianJie.setText("开始测试");
                ((NaoBoActivity) getContext()).cancelLoadingDialog();
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                // 连接中断，isActiveDisConnected表示是否是主动调用了断开连接方法
                LogUtil.LogShitou("NaoBoActivity--onDisConnected", "连接中断");
//                MyDialog.dialogFinish((NaoBoActivity)getContext(), "连接中断");
                data.setStatue(0);
                textStatue.setVisibility(View.GONE);
                btnLianJie.setText("连接");
            }
        });
    }

}
