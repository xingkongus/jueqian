package us.xingkong.jueqian.listener;

import rx.Subscriber;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.bean.RealSBean.BaseRealSBean;
import us.xingkong.jueqian.utils.LogUtils;
import us.xingkong.jueqian.utils.NetUtils;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/10 12:41
 */

public abstract class NetSubscriber<T extends BaseRealSBean> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (NetUtils.isConnected(JueQianAPP.getAppContext())) {
            // 没有网络
            onFailure("网络连接失败，请检查网络设置");
        } else {
            onFailure("获取数据失败，请稍后重试");
        }
    }

    @Override
    public void onNext(final T t) {
        LogUtils.$Log(t.toString());
        if (!t.isError()) {
            onSuccess(t);
        } else {
            onFailure(t.toString());
        }
    }

    /**
     * 网络请求成功回调(isError为false)
     *
     * @param data 成功获取到的数据
     */
    public abstract void onSuccess(T data);

    /**
     * 网络请求失败回调(Status为false或onError)
     *
     * @param failMsg 失败信息
     */
    public abstract void onFailure(String failMsg);
}

