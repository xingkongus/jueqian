package us.xingkong.jueqian.module.Forum.NewAnswer;

import us.xingkong.jueqian.base.BasePresenterImpl;

/**
 * Created by lenovo on 2017/3/30.
 */

public class NewAnswerPresenter extends BasePresenterImpl implements NewAnswerContract.Presenter {
    private final NewAnswerContract.View mView;

    public NewAnswerPresenter(NewAnswerContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }
}
