package us.xingkong.jueqian.module.Forum;

import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;

import us.xingkong.jueqian.base.BasePresenter;
import us.xingkong.jueqian.base.BaseView;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;


public interface ForumContract {
    interface View extends BaseView<Presenter> {
        void setRecyclewViewBug();
    }

    interface Presenter extends BasePresenter {
        void getBmobQuestion(Context context, ArrayList<Question> questions, Handler handler,int flag);
        void getMoreBmobQuestion(Context context, ArrayList<Question> questions, Handler handler,int item_count);
    }
}
