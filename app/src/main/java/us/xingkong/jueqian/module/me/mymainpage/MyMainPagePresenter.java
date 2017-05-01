package us.xingkong.jueqian.module.me.mymainpage;

import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.module.me.mainpage.MainPageContract;

/**
 * Created by PERFECTLIN on 2017/4/30 0030.
 */

public class MyMainPagePresenter extends BasePresenterImpl implements MyMainPageContract.Presenter {
    private final MyMainPageContract.View mView;

    public MyMainPagePresenter(MyMainPageContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }
}
