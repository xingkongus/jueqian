package us.xingkong.jueqian;

import android.app.Application;
import android.content.Context;

import cn.bmob.v3.Bmob;
import us.xingkong.jueqian.utils.ToastUtils;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 11:04
 */

public class JueQianAPP extends Application {

    private static Context appContext;
    private static long exitTime = 0;

    /**
     * 获取Application的Context
     *
     * @return 全局Context
     */
    public static Context getAppContext() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        Bmob.initialize(this, "0f9a062b723b72b09a2543f2c6b81090"); //2d6a319fa542339021237173a1990ead
    }

    /**
     * 退出APP
     */
    public static void exitApp() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtils.shortToast(getAppContext(), appContext.getString(R.string.text_press_again));
            exitTime = System.currentTimeMillis();
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
