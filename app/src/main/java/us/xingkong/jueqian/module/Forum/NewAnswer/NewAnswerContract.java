package us.xingkong.jueqian.module.Forum.NewAnswer;

import android.content.Context;
import android.os.Handler;

import us.xingkong.jueqian.base.BasePresenter;
import us.xingkong.jueqian.base.BaseView;

/**
 * Created by lenovo on 2017/3/30.
 */

public interface NewAnswerContract {
    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {
        void addNewAnswer(Context context,String newAnswer,String questionID,Handler handler,String question_userID);
    }
}
