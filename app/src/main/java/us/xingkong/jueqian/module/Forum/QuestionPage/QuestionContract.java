package us.xingkong.jueqian.module.Forum.QuestionPage;

import android.content.Context;
import android.os.Handler;

import us.xingkong.jueqian.base.BasePresenter;
import us.xingkong.jueqian.base.BaseView;

/**
 * Created by boluoxiaomo
 * Date: 17/1/9
 */

public interface QuestionContract {

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {
        void getQuestion(Context context,String questionID,Handler handler);
        void getQuestionAnswer(Context context, Handler handler, String questionID);
    }
}
