package us.xingkong.jueqian.module.me.mysettings;

import us.xingkong.jueqian.base.BasePresenterImpl;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class MySettingsPresenter extends BasePresenterImpl implements MySettingsContract.Presenter {
    private final MySettingsContract.View mView;

    public MySettingsPresenter(MySettingsContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }
}
