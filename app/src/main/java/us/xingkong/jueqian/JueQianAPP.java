package us.xingkong.jueqian;

import android.app.Application;
import android.content.Context;

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
    }

    /**
     * 退出APP
     */
    public static void exitApp() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtils.shortToast(getAppContext(), appContext.getString(R.string.text_press_again));
            exitTime = System.currentTimeMillis();
        }
    }
}
