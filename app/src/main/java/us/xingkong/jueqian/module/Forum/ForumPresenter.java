package us.xingkong.jueqian.module.Forum;

import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.data.RealSData.ForumRepository;

/**
 * Created by boluoxiaomo
 * Date: 17/1/9
 */

public class ForumPresenter extends BasePresenterImpl implements ForumContract.Presenter {

    private final ForumContract.View mView;
    private ForumRepository mRepository;

    public ForumPresenter(ForumContract.View view, ForumRepository mRepository) {
        mView = view;
        this.mView.setPresenter(this);
        this.mRepository = mRepository;
    }

}
