package us.xingkong.jueqian.module.Forum.QuestionPage.Comment;

import android.content.Context;
import android.os.Handler;

import us.xingkong.jueqian.base.BasePresenter;
import us.xingkong.jueqian.base.BaseView;

/**
 * Created by boluoxiaomo
 * Date: 17/1/9
 */

public interface CommentContract {
    interface View extends BaseView<CommentContract.Presenter> {

    }

    interface Presenter extends BasePresenter {
        void setComment(Context context, Handler handler);
    }
}
