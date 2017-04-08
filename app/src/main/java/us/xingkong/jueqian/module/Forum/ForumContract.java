package us.xingkong.jueqian.module.Forum;

import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import us.xingkong.jueqian.base.BasePresenter;
import us.xingkong.jueqian.base.BaseView;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;

/**
 * Created by boluoxiaomo
 * Date: 17/1/8 15:33
 */

public interface ForumContract {
    interface View extends BaseView<ForumContract.Presenter> {

    }

    interface Presenter extends BasePresenter {
        List<Question> getBmobQuestion(Context context, ArrayList<Question> questions, Handler handler);
    }
}
