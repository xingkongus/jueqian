package us.xingkong.jueqian.module.RealS.Content;

import java.util.List;
import java.util.Map;

import us.xingkong.jueqian.base.BasePresenter;
import us.xingkong.jueqian.base.BaseView;
import us.xingkong.jueqian.bean.RealSBean.Results;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 15:33
 */

public interface ContentContract {
    interface View extends BaseView<ContentContract.Presenter> {
        void loadSuccess(int page);

        void loadFailure(int page);

        void showRefresh(boolean isRefresh);

        void showRealSList(int page, List<Results> realSList);
    }

    interface Presenter extends BasePresenter {
        void getRealSList(String type, int page);
    }
}
