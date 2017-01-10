package us.xingkong.jueqian.module.me;

import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.module.main.MainContract;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class MePresenter extends BasePresenterImpl implements MeContract.Presenter {

    private final MeContract.View mView;

    public MePresenter(MeContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }
}
