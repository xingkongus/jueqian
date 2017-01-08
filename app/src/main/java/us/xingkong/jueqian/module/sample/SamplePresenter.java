package us.xingkong.jueqian.module.sample;

import us.xingkong.jueqian.base.BasePresenterImpl;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 11:28
 */

public class SamplePresenter extends BasePresenterImpl implements SampleContract.Presenter {

    private final SampleContract.View mView;
    public SamplePresenter(SampleContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }
}
