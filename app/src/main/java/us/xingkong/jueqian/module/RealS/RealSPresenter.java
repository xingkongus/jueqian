package us.xingkong.jueqian.module.RealS;

import us.xingkong.jueqian.base.BasePresenterImpl;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/10 14:39
 */

public class RealSPresenter extends BasePresenterImpl implements RealSContract.Presenter {

    private final RealSContract.View mView;
    public RealSPresenter(RealSContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }
}
