package us.xingkong.jueqian.module.Login;

import android.content.Context;

import us.xingkong.jueqian.base.BasePresenter;
import us.xingkong.jueqian.base.BaseView;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 11:27
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {
        void login(Context context, String username, String password);

    }
}
