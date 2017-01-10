package us.xingkong.jueqian.module.Regist;

import android.content.Context;

import us.xingkong.jueqian.base.BasePresenter;
import us.xingkong.jueqian.base.BaseView;



public interface RegistContract {
    interface View extends BaseView<Presenter> {
    }
    interface Presenter extends BasePresenter {
        void regist(final Context context, final String username, final String password);
        void a();
    }
}
