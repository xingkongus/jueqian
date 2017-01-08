package us.xingkong.jueqian.base;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 10:50
 */

public abstract class BasePresenterImpl implements BasePresenter {
    protected CompositeSubscription mSubscriptions;

    @Override
    public void onStart() {
        if (mSubscriptions == null) {
            mSubscriptions = new CompositeSubscription();
        }
    }

    @Override
    public void onDestroy() {
        if (mSubscriptions != null && mSubscriptions.hasSubscriptions()) {
            mSubscriptions.unsubscribe();
            mSubscriptions.clear();
        }
    }
}
