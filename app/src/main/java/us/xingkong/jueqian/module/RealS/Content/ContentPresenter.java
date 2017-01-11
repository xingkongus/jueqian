package us.xingkong.jueqian.module.RealS.Content;

import rx.Subscription;
import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.bean.RealSBean.DataResults;
import us.xingkong.jueqian.data.RealSData.RealSRepository;
import us.xingkong.jueqian.listener.NetSubscriber;
import us.xingkong.jueqian.utils.RxUtils;

import static us.xingkong.jueqian.base.Constants.REALS_READ_NUM;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 15:36
 */

public class ContentPresenter extends BasePresenterImpl implements ContentContract.Presenter {

    private final ContentContract.View mView;
    private RealSRepository mRealSRepository;

    public ContentPresenter(RealSRepository realSRepository, ContentContract.View view) {
        this.mView = view;
        this.mView.setPresenter(this);
        this.mRealSRepository = realSRepository;
    }

    @Override
    public void getRealSList(String type, final int page) {
        if (page == 1) {
            mView.showRefresh(true);
        }
        Subscription subscription = mRealSRepository.getDataResults(type, REALS_READ_NUM, page)
                .compose(RxUtils.<DataResults>defaultSchedulers())
                .subscribe(new NetSubscriber<DataResults>() {
                    @Override
                    public void onSuccess(DataResults data) {
                        if (page == 1) {
                            mView.showRefresh(false);
                        }
                        mView.loadSuccess(page);
                        mView.showRealSList(REALS_READ_NUM, data.getResults());
                    }

                    @Override
                    public void onFailure(String failMsg) {
                        if (page == 1) {
                            mView.showRefresh(false);
                        }
                        mView.loadFailure(page);
                        mView.showToast(failMsg);
                    }
                });
        mSubscriptions.add(subscription);
    }
}
