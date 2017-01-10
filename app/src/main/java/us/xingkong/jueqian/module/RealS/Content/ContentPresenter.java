package us.xingkong.jueqian.module.RealS.Content;

import us.xingkong.jueqian.base.BasePresenterImpl;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 15:36
 */

public class ContentPresenter extends BasePresenterImpl implements ContentContract.Presenter {

    private final ContentContract.View mView;
    public ContentPresenter(ContentContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }
}
