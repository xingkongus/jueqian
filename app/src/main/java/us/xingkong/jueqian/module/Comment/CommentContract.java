package us.xingkong.jueqian.module.Comment;

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
    }
}
