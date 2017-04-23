package us.xingkong.jueqian.module.me.mymainpage.editinfo;

import java.util.List;

import us.xingkong.jueqian.base.BasePresenter;
import us.xingkong.jueqian.base.BaseView;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;

/**
 * Created by PERFECTLIN on 2017/4/20 0020.
 */

public class EditInfoContract {
    interface View extends BaseView<EditInfoContract.Presenter> {
    }

    interface Presenter extends BasePresenter {
        List<Question> getCollections();
    }
}
