package us.xingkong.jueqian.module.Forum;

import us.xingkong.jueqian.base.BasePresenterImpl;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 15:36
 */

public class ForumPresenter extends BasePresenterImpl implements ForumContract.Presenter {

    private final ForumContract.View mView;
    public ForumPresenter(ForumContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }

}
