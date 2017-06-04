package us.xingkong.jueqian.module.Forum.NewPage;

import android.content.Context;
import android.os.Handler;

import java.util.List;

import us.xingkong.jueqian.base.BasePresenter;
import us.xingkong.jueqian.base.BaseView;


/**
 * Created by boluoxiaomo
 * Date: 17/1/9
 */

public interface NewContract {
    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {
        void addQuestion(Context context, String title, String content, String tag1, String tag2, Handler handler);

        void addMyQuestion(Context context, String myquestionID,Handler handler);

        int upLoadImage(Context context, String content, Handler handler);

        void saveURL(List<String> imageFiles, String newQuestionID, Context context, Handler handler);

        void changeURL(String newQuestionID, Context mContext, Handler handler, String newQuestionContent);
    }

}
