package us.xingkong.jueqian.module.me.mymessage.allmessage;

import us.xingkong.jueqian.base.BasePresenterImpl;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class AllMessagePresenter extends BasePresenterImpl implements AllMessageContract.Presenter {
    private final AllMessageContract.View mView;

    public AllMessagePresenter(AllMessageContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    @Override
    public void getMessageList() {

    }
}
