package us.xingkong.jueqian.module.me.mymainpage.editinfo;

import us.xingkong.jueqian.base.BasePresenterImpl;

/**
 * Created by PERFECTLIN on 2017/4/30 0030.
 */

public class EditInfoPresenter extends BasePresenterImpl implements EditInfoContract.Presenter {
    private final EditInfoContract.View mView;

    public EditInfoPresenter(EditInfoContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }
}
