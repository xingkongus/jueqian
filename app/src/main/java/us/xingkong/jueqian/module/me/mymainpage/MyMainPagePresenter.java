package us.xingkong.jueqian.module.me.mainpage;

import us.xingkong.jueqian.base.BasePresenterImpl;

/**
 * Created by PERFECTLIN on 2017/4/30 0030.
 */

public class MainPagePresenter extends BasePresenterImpl implements MainPageContract.Presenter {
    private final MainPageContract.View mView;

    public MainPagePresenter(MainPageContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }
}
