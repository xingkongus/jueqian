package us.xingkong.jueqian.module.ChangePassWord;

import android.content.Context;

import us.xingkong.jueqian.base.BasePresenter;
import us.xingkong.jueqian.base.BaseView;

/**
 * Created by lenovo on 2017/1/12.
 */

public interface ChangePWContract {
    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter{
        void checkpd(Context context,String repd,String nowpd);
    }
}
