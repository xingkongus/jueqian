package us.xingkong.jueqian.module.Home;

import us.xingkong.jueqian.base.BasePresenter;
import us.xingkong.jueqian.base.BaseView;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 15:33
 */

public interface HomePageContract {
    interface View extends BaseView<HomePageContract.Presenter> {
    }

    interface Presenter extends BasePresenter {
    }
}
