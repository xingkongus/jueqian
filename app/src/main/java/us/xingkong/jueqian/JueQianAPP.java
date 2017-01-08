package us.xingkong.jueqian;

import android.app.Application;
import android.content.Context;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 11:04
 */

public class JueQianAPP extends Application {

    private static Context appContext;

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
}
