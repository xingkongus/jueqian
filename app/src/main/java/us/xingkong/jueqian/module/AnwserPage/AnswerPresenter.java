package us.xingkong.jueqian.module.AnwserPage;

import us.xingkong.jueqian.base.BasePresenterImpl;

/**
 * Created by boluoxiaomo
 * Date: 17/1/9
 */

public class AnswerPresenter extends BasePresenterImpl implements AnswerContract.Presenter {

    private final AnswerContract.View mView;
    public AnswerPresenter(AnswerContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }

}
