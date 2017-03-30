package us.xingkong.jueqian.module.me.myquestions;

import cn.bmob.v3.BmobQuery;
import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;

/**
 * Created by PERFECTLIN on 2017/1/13 0013.
 */

public class MyQuestionPresenter extends BasePresenterImpl implements MyQuestionsContract.Presenter {
    private final MyQuestionsContract.View mView;

    public MyQuestionPresenter(MyQuestionsContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    public void getMyQuestions() {
        BmobQuery<_User> bmobQuery=new BmobQuery<>();
//        bmobQuery.getObject(JueQianAPP.getAppContext(), "S7mYLLLt", new GetListener<_User>() {
//            @Override
//            public void onSuccess(_User user) {
//                mView.showToast("请求问题列表成功");
//
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//
//            }
//        });
    }
}
