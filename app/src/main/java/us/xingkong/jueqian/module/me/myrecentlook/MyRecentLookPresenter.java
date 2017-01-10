package us.xingkong.jueqian.module.me.myrecentlook;

import us.xingkong.jueqian.base.BasePresenterImpl;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class MyRecentLookPresenter extends BasePresenterImpl implements MyRecentLookContract.Presenter {
    private final MyRecentLookContract.View mView;

    public MyRecentLookPresenter(MyRecentLookContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }
}
