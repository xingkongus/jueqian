package us.xingkong.jueqian.module.me.mycollection;

import java.util.List;

import us.xingkong.jueqian.base.BasePresenter;
import us.xingkong.jueqian.base.BaseView;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class MyCollectionContract {
    interface View extends BaseView<MyCollectionContract.Presenter> {
    }

    interface Presenter extends BasePresenter {
        List<Question> getCollections();
    }
}
