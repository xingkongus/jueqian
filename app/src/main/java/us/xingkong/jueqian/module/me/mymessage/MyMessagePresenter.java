package us.xingkong.jueqian.module.me.mymessage;

import us.xingkong.jueqian.base.BasePresenterImpl;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class MyMessagePresenter extends BasePresenterImpl implements MyMessageContract.Presenter {
    private final MyMessageContract.View mView;

    public MyMessagePresenter(MyMessageContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }
}
