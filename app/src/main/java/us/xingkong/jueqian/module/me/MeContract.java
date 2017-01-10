package us.xingkong.jueqian.module.me;

import us.xingkong.jueqian.base.BasePresenter;
import us.xingkong.jueqian.base.BaseView;
import us.xingkong.jueqian.module.main.MainContract;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class MeContract {
    interface View extends BaseView<MeContract.Presenter> {
    }

    interface Presenter extends BasePresenter {
    }
}
