package com.sxmoc.bq.base;

import android.content.Context;
import android.content.Intent;
import android.os.Process;

import com.sxmoc.bq.activity.DengLuActivity;
import com.sxmoc.bq.constant.Constant;
import com.sxmoc.bq.util.ACache;


/**
 * Created by Administrator on 2017/8/27.
 */
public class ToLoginActivity {

    /**
     * des： 登录
     * author： ZhangJieBo
     * date： 2017/7/6 0006 下午 2:14
     */
    public static void toLoginActivity(Context context) {
//        MyApplication.getInstance().exit();
        ACache aCache = ACache.get(context, Constant.Acache.APP);
        aCache.clear();
        Intent intent = new Intent(context, DengLuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        // 杀掉进程
        Process.killProcess(Process.myPid());
        System.exit(0);
    }
}
