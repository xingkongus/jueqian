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


public class QuestionPresenter extends BasePresenterImpl implements QuestionContract.Presenter {

    private final QuestionContract.View mView;

    public QuestionPresenter(QuestionContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public void getQuestion(Context context, final String questionID, final Handler handler) {

        final BmobQuery<Question> query = new BmobQuery<>();
        query.include("user");
        query.getObject(context, questionID, new GetListener<Question>() {
            @Override
            public void onSuccess(Question question) {
                question.getTAG1_ID();
                question.getTAG2_ID();
                question.getMcontent();
                question.getMtitle();
                question.getUser();
                question.getFocus();
                question.getImageFiles();
                Message msg = new Message();
                msg.what = 1;
                msg.obj = question;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(int i, String s) {
                mView.showToast("网络有点差哦！");
            }
        });


    }

    @Override
    public ArrayList<Answer> getQuestionAnswer(Context context, final Handler handler, String questionID, final ArrayList<Answer> answers) {
        BmobQuery<Answer> query = new BmobQuery<>();
        query.include("user,question.user");
        Question question = new Question();
        question.setObjectId(questionID);
        query.addWhereEqualTo("question", new BmobPointer(question));
        query.order("-createdAt");
        query.findObjects(context, new FindListener<Answer>() {
            @Override
            public void onSuccess(List<Answer> list) {
                for (Answer answer : list) {
                    answer.getUser();
                    answer.getMcontent();
                    answer.getUps();
                    answer.getState();
                    answer.getObjectId();
                    answers.add(answer);
                }
                if (answers != null) {
                    handler.sendEmptyMessage(3);
                }
            }

            @Override
            public void onError(int i, String s) {
                mView.showToast("网络有点差哦！");
            }
        });
        return answers;
    }

    @Override
    public void zan(Context context, final Handler handler, String questionID) {
        _User user = BmobUser.getCurrentUser(context, _User.class);
        Question question = new Question();
        question.setObjectId(questionID);
        BmobRelation bmobRelation = new BmobRelation();
        bmobRelation.add(user);
        question.setLikepeople(bmobRelation);
        question.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                mView.showToast("已赞该问题！");
                Message msg=new Message();
                msg.what=11;
                msg.obj=1;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(int i, String s) {
                mView.showToast("网络有点差哦！可重新尝试下！");
            }
        });

    }

    @Override
    public void shoucan(Context context, final Handler handler, String questionID, String question_userID) {
        _User user = BmobUser.getCurrentUser(context, _User.class);
        Question question = new Question();
        question.setObjectId(questionID);
        if (question_userID.equals(user.getObjectId()))
            return;
        BmobRelation bmobRelation = new BmobRelation();
        bmobRelation.add(question);
        user.setCollections(bmobRelation);
        user.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                mView.showToast("已收藏！");
                Message msg=new Message();
                msg.what=11;
                msg.obj=3;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(int i, String s) {
                mView.showToast("网络有点差哦！可重新尝试收藏！");
            }
        });
    }

    @Override
    public void addRecentlook(Context context, String questionID, String question_userID, _User user) {
        if (!question_userID.equals(user.getObjectId())) {
            Question question = new Question();
            question.setObjectId(questionID);
            BmobRelation bmobRelation = new BmobRelation();
            bmobRelation.add(question);
            user.setRecentlooks(bmobRelation);
            user.update(context, new UpdateListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(int i, String s) {
                }
            });
        }
    }

    @Override
    public void quxiaoZan(Context context, String questionID, final Handler handler) {
        _User user = BmobUser.getCurrentUser(context, _User.class);
        Question question = new Question();
        question.setObjectId(questionID);
        BmobRelation bmobRelation = new BmobRelation();
        bmobRelation.remove(user);
        question.setLikepeople(bmobRelation);
        question.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                mView.showToast("已取消赞");
                Message msg=new Message();
                msg.what=11;
                msg.obj=2;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(int i, String s) {
                mView.showToast("网络有点差哦！可重新尝试下！");
            }
        });
    }

    @Override
    public void quxiaoShouzan(Context context, String questionID, final Handler handler) {
        _User user = BmobUser.getCurrentUser(context, _User.class);
        Question question = new Question();
        question.setObjectId(questionID);
        BmobRelation bmobRelation = new BmobRelation();
        bmobRelation.remove(question);
        user.setCollections(bmobRelation);
        user.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                mView.showToast("已取消收藏");
                Message msg=new Message();
                msg.what=11;
                msg.obj=4;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(int i, String s) {
                mView.showToast("网络有点差哦！可重新尝试取消收藏！");
            }
        });
    }

    @Override
    public void zanStateChange(Context context, String questionID, int flag) {
        if (flag == 0) {
            Question question = new Question();
            question.setState(1);
            question.update(context, questionID, new UpdateListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
        } else if (flag == 1) {
            Question question = new Question();
            question.setState(0);
            question.update(context, questionID, new UpdateListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
        }

    }

    @Override
    public void shouzanStateChange(Context context, String questionID, int flag) {
        if (flag == 0) {
            Question question = new Question();
            question.setShouzanFlag(1);
            question.update(context, questionID, new UpdateListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
        } else if (flag == 1) {
            Question question = new Question();
            question.setShouzanFlag(0);
            question.update(context, questionID, new UpdateListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
        }
    }
}
