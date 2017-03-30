package us.xingkong.jueqian.module.Forum.NewPage;

import android.content.Context;

import us.xingkong.jueqian.base.BasePresenter;
import us.xingkong.jueqian.base.BaseView;



/**
 * Created by boluoxiaomo
 * Date: 17/1/9
 */

public interface NewContract {
    interface View extends BaseView<NewContract.Presenter> {
    }

    interface Presenter extends BasePresenter {
        void addQuestion(Context context,String title,String content,String tag1,String tag2);
    }

}
