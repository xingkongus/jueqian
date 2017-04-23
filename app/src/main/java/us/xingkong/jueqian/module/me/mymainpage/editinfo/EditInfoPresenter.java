package us.xingkong.jueqian.module.me.mymainpage.editinfo;

import java.util.List;

import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;

/**
 * Created by PERFECTLIN on 2017/4/20 0020.
 */
public class EditInfoPresenter extends BasePresenterImpl implements EditInfoContract.Presenter {
    private final EditInfoContract.View mView;
    static List<Question> questions;

    public EditInfoPresenter(EditInfoContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    @Override
    public List<Question> getCollections() {
        return null;
    }
}
