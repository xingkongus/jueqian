package us.xingkong.jueqian.module.me.myanswer;

import us.xingkong.jueqian.base.BasePresenterImpl;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class MyAnswerPresenter extends BasePresenterImpl implements MyAnswerContract.Presenter {
    private final MyAnswerContract.View mView;

    public MyAnswerPresenter(MyAnswerContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }
}
