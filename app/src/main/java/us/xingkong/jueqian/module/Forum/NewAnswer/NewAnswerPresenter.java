package us.xingkong.jueqian.module.Forum.NewAnswer;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Answer;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;
import us.xingkong.jueqian.module.Forum.QuestionPage.QuestionActivity;


/**
 * Created by lenovo on 2017/3/30.
 */

public class NewAnswerPresenter extends BasePresenterImpl implements NewAnswerContract.Presenter {

    private final NewAnswerContract.View mView;

    public NewAnswerPresenter(NewAnswerContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }


    @Override
    public void addNewAnswer(final Context context, String newAnswer, final String questionID, final Handler handler) {
        _User user= BmobUser.getCurrentUser(context,_User.class);
        Question question=new Question();
        question.setObjectId(questionID);
        Answer answer=new Answer();
        answer.setUser(user);
        answer.setQuestion(question);
        answer.setMcontent(newAnswer);
        answer.setState(1);//用户状态
        answer.setUps(0);//点赞数
        answer.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Intent intent=new Intent(context, QuestionActivity.class);
                intent.putExtra("questionid",questionID);
                context.startActivity(intent);
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onFailure(int i, String s) {
                mView.showToast("网络连接超时");
            }
        });
    }

}
