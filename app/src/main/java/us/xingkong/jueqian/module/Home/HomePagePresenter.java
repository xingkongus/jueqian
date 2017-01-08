package us.xingkong.jueqian.module.Home;

import us.xingkong.jueqian.base.BasePresenterImpl;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 15:36
 */

public class HomePagePresenter extends BasePresenterImpl implements HomePageContract.Presenter {

    private final HomePageContract.View mView;
    public HomePagePresenter(HomePageContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }
}
