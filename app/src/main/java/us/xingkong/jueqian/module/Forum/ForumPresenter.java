package us.xingkong.jueqian.module.Forum;

import us.xingkong.jueqian.base.BasePresenterImpl;

/**
 * Created by boluoxiaomo
 * Date: 17/1/9
 */

public class ForumPresenter extends BasePresenterImpl implements ForumContract.Presenter {

    private final ForumContract.View mView;
    public ForumPresenter(ForumContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }

}
