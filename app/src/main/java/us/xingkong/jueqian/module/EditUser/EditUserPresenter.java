package us.xingkong.jueqian.module.EditUser;

import us.xingkong.jueqian.base.BasePresenterImpl;


public class EditUserPresenter extends BasePresenterImpl implements EditUserContract.Presenter {

    private final EditUserContract.View mView;
    public EditUserPresenter(EditUserContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }

}
