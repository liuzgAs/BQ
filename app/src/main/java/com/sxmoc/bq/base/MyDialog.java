package com.sxmoc.bq.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.sxmoc.bq.customview.SingleBtnDialog;


/**
 * Created by Administrator on 2017/8/27.
 */
public class MyDialog {
    public static void showReLoginDialog(final Context context) {
        final SingleBtnDialog singleBtnDialog = new SingleBtnDialog(context, "您的账号在其他设备上登录，请重新登录", "确认");
        singleBtnDialog.setClicklistener(new SingleBtnDialog.ClickListenerInterface() {
            @Override
            public void doWhat() {
                ToLoginActivity.toLoginActivity(context);
            }
        });
        singleBtnDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    singleBtnDialog.dismiss();
                    ToLoginActivity.toLoginActivity(context);
                }
                return false;
            }
        });
        singleBtnDialog.show();
    }

    /**
     * 单个按钮无操作
     *
     * @param msg
     */
    public static void showTipDialog(Context context, String msg) {
        final SingleBtnDialog singleBtnDialog = new SingleBtnDialog(context, msg, "确认");
        singleBtnDialog.setClicklistener(new SingleBtnDialog.ClickListenerInterface() {
            @Override
            public void doWhat() {
                singleBtnDialog.dismiss();
            }
        });
        singleBtnDialog.show();
    }

    public static void dialogFinish(final Activity activity, String msg) {
        SingleBtnDialog singleBtnDialog = new SingleBtnDialog(activity, msg, "确认");
        singleBtnDialog.setClicklistener(new SingleBtnDialog.ClickListenerInterface() {
            @Override
            public void doWhat() {
                activity.finish();
            }
        });
        singleBtnDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    dialog.dismiss();
                    activity.finish();
                }
                return false;
            }
        });
        singleBtnDialog.setCancelable(false);
        singleBtnDialog.show();
    }



}
