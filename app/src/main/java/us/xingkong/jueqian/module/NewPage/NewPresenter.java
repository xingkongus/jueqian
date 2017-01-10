package us.xingkong.jueqian.module.NewPage;

import us.xingkong.jueqian.base.BasePresenterImpl;

/**
 * Created by boluoxiaomo
 * Date: 17/1/9
 */

public class NewPresenter extends BasePresenterImpl implements NewContract.Presenter {

    private final NewContract.View mView;
    public NewPresenter(NewContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }

}
