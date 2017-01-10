package us.xingkong.jueqian.module.Forum.QuestionPage.Comment;

import us.xingkong.jueqian.base.BasePresenterImpl;

/**
 * Created by boluoxiaomo
 * Date: 17/1/9
 */

public class CommentPresenter extends BasePresenterImpl implements CommentContract.Presenter {

    private final CommentContract.View mView;
    public CommentPresenter(CommentContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }


}
