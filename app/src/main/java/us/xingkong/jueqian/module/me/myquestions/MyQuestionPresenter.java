package us.xingkong.jueqian.module.me.myquestions;

import us.xingkong.jueqian.base.BasePresenter;
import us.xingkong.jueqian.base.BasePresenterImpl;

/**
 * Created by PERFECTLIN on 2017/1/13 0013.
 */

public class MyQuestionPresenter extends BasePresenterImpl implements MyQuestionsContract.Presenter {
    private final MyQuestionsContract.View mView;

    public MyQuestionPresenter(MyQuestionsContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }
}
