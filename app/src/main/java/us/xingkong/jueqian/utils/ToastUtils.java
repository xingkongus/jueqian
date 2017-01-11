package us.xingkong.jueqian.utils;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import us.xingkong.jueqian.JueQianAPP;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 11：01
 */

public class ToastUtils {

    private ToastUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    public static void shortToast(View.OnClickListener onClickListener, CharSequence msg) {
        show(JueQianAPP.getAppContext(), msg, Toast.LENGTH_SHORT);
    }



    public static void shortToast(Context context, CharSequence msg) {
        show(context, msg, Toast.LENGTH_SHORT);
    }

    public static void shortToast(Context context, int msg) {
        show(context, msg, Toast.LENGTH_SHORT);
    }

    public static void longToast(Context context, CharSequence msg) {
        show(context, msg, Toast.LENGTH_LONG);
    }

    public static void longToast(Context context, int msg) {
        show(context, msg, Toast.LENGTH_LONG);
    }

    /**
     * 显示指定时长的Toast
     *
     * @param context
     * @param msg      Toast内容
     * @param duration Toast时长
     */
    public static void show(Context context, CharSequence msg, int duration) {
        Toast.makeText(context.getApplicationContext(), msg, duration).show();
    }

    /**
     * 显示指定时长的Toast
     *
     * @param context
     * @param msg      Toast内容的资源Id
     * @param duration Toast时长
     */
    public static void show(Context context, int msg, int duration) {
        Toast.makeText(context.getApplicationContext(), msg, duration).show();
    }


}
