package us.xingkong.jueqian.module.me.mycollection;

import us.xingkong.jueqian.base.BasePresenterImpl;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class MyCollectionPresenter extends BasePresenterImpl implements MyCollectionContract.Presenter {
    private final MyCollectionContract.View mView;

    public MyCollectionPresenter(MyCollectionContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }
}
