package us.xingkong.jueqian.module.QuestionPage;

import us.xingkong.jueqian.base.BasePresenterImpl;

/**
 * Created by boluoxiaomo
 * Date: 17/1/9
 */

public class QuestionPresenter extends BasePresenterImpl implements QuestionContract.Presenter {

    private final QuestionContract.View mView;
    public QuestionPresenter(QuestionContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }
}
