package us.xingkong.jueqian.module.main;

import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.module.sample.SampleContract;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 12:15
 */

public class MainPresenter extends BasePresenterImpl implements MainContract.Presenter {

    private final MainContract.View mView;
    public MainPresenter(MainContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }
}
