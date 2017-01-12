package us.xingkong.jueqian.module.me.mysettings.aboutme;

import us.xingkong.jueqian.base.BasePresenterImpl;

/**
 * Created by PERFECTLIN on 2017/1/11 0011.
 */

public class AboutMePresenter extends BasePresenterImpl implements AboutMeContract.Presenter {
    protected final AboutMeContract.View mView;

    public AboutMePresenter(AboutMeContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }
}
