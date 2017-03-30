package us.xingkong.jueqian.module.Regist;

import android.content.Context;
import android.os.Handler;
import android.widget.EditText;

import us.xingkong.jueqian.base.BasePresenter;
import us.xingkong.jueqian.base.BaseView;



public interface RegistContract {
    interface View extends BaseView<Presenter> {
    }
    interface Presenter extends BasePresenter {
        void regist(final Context context, final String username, final String password, Handler handler);
        void setEditText(EditText editText);
    }
}
