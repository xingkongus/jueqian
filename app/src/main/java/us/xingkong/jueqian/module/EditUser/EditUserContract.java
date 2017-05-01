package us.xingkong.jueqian.module.EditUser;

import android.content.Context;

import us.xingkong.jueqian.base.BasePresenter;
import us.xingkong.jueqian.base.BaseView;


public interface EditUserContract {

    interface View extends BaseView<EditUserContract.Presenter> {
    }
    interface Presenter extends BasePresenter {
        void saveUser(Context context, String url, String nick, String phone, String email, String sex);
//        void pushURL(Context context,Uri uri);
    }
}
