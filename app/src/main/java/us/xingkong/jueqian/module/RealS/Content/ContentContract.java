package us.xingkong.jueqian.module.RealS.Content;

import us.xingkong.jueqian.base.BasePresenter;
import us.xingkong.jueqian.base.BaseView;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 15:33
 */

public interface ContentContract {
    interface View extends BaseView<ContentContract.Presenter> {
    }

    interface Presenter extends BasePresenter {
    }
}
