package us.xingkong.jueqian.module.Forum;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

import us.xingkong.jueqian.base.BasePresenter;
import us.xingkong.jueqian.base.BaseView;
import us.xingkong.jueqian.bean.ForumBean.GsonBean.GSON_ForumPageBean;

/**
 * Created by boluoxiaomo
 * Date: 17/1/8 15:33
 */

public interface ForumContract {
    interface View extends BaseView<ForumContract.Presenter> {
        void initShowList(ArrayList<GSON_ForumPageBean> arrayList);
        Context getmContext();
    }

    interface Presenter extends BasePresenter {
        void initList();
        void getForumList(boolean isNewest, Date date, int num);
    }
}
