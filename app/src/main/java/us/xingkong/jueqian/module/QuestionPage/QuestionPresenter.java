package us.xingkong.jueqian.module.QuestionPage;

import us.xingkong.jueqian.base.BasePresenterImpl;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 12:15
 */

public class QuestionPresenter extends BasePresenterImpl implements QuestionContract.Presenter {

    private final QuestionContract.View mView;
    public QuestionPresenter(QuestionContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }
}
