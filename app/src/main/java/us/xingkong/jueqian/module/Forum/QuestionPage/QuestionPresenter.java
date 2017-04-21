package us.xingkong.jueqian.module.Forum.QuestionPage;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;
import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Answer;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;

/**
 * Created by boluoxiaomo
 * Date: 17/1/9
 */

public class QuestionPresenter extends BasePresenterImpl implements QuestionContract.Presenter {

    private final QuestionContract.View mView;
    public QuestionPresenter(QuestionContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public void getQuestion(Context context, final String questionID, final Handler handler) {

        BmobQuery<Question> query=new BmobQuery<>();
        query.getObject(context, questionID, new GetListener<Question>() {
            @Override
            public void onSuccess(Question question) {
                question.getTAG1_ID();
                question.getTAG2_ID();
                question.getMcontent();
                question.getMtitle();
                question.getUser().getObjectId();
                question.getFocus();
                Message msg=new Message();
                msg.what=1;
                msg.obj=question;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(int i, String s) {
                mView.showToast("加载失败");
            }
        });


    }

    @Override
    public void getQuestionAnswer(Context context, final Handler handler, String questionID,final ArrayList<Answer> answers) {
        BmobQuery<Answer> query=new BmobQuery<>();
        Question question=new Question();
        question.setObjectId(questionID);
        query.addWhereEqualTo("question", new BmobPointer(question));
        query.include("user,question.user");
        query.order("-createdAt");
        query.findObjects(context, new FindListener<Answer>() {
            @Override
            public void onSuccess(List<Answer> list) {
                for(Answer answer:list){
                    answer.getUser().getUsername();
                    answer.getMcontent();
                    answer.getUps();
                    answer.getState();
                    answer.getObjectId();
                    answers.add(answer);
                }

            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void zan(Context context, Handler handler, String questionID) {
        _User user= BmobUser.getCurrentUser(context,_User.class);
        Question question=new Question();
        question.setObjectId(questionID);
        BmobRelation bmobRelation=new BmobRelation();
        bmobRelation.add(user);
        question.setLikepeople(bmobRelation);
        question.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                mView.showToast("赞成功");
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });

    }

    @Override
    public void shoucan(Context context, Handler handler, String questionID) {
        _User user=BmobUser.getCurrentUser(context,_User.class);
        Question question=new Question();
        question.setObjectId(questionID);
        BmobRelation bmobRelation=new BmobRelation();
        bmobRelation.add(question);
        user.setCollections(bmobRelation);
        user.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                mView.showToast("收藏成功");
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }
}
