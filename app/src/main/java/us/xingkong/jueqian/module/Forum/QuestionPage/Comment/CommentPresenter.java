package us.xingkong.jueqian.module.Forum.QuestionPage.Comment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Answer;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Comment;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;


public class CommentPresenter extends BasePresenterImpl implements CommentContract.Presenter {

    private final CommentContract.View mView;

    public CommentPresenter(CommentContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }


    @Override
    public void getAnswer(final Context context, String answerID, final Handler handler) {
        BmobQuery<Answer> query = new BmobQuery<>();
        query.include("user");
        query.getObject(context, answerID, new GetListener<Answer>() {
            @Override
            public void onSuccess(Answer answer) {
                answer.getState();
                answer.getUser();
                answer.getMcontent();
                Message msg = new Message();
                msg.obj = answer;
                msg.what = 1;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(int i, String s) {
                mView.showToast("网络连接超时");
            }
        });
    }

    @Override
    public void getAnswerComments(final Context context, final Handler handler, String answerID, final ArrayList<Comment> comments) {
        BmobQuery<Comment> query = new BmobQuery<>();
        Answer answer = new Answer();
        answer.setObjectId(answerID);
        query.addWhereEqualTo("answer", new BmobPointer(answer));
        query.include("user");
        query.order("-createdAt");
        query.setLimit(20);
        query.findObjects(context, new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {
                for (Comment comment : list) {
                    comment.getState();
                    comment.getUser();
                    comment.getMcontent();
                    comments.add(comment);
                }
                handler.sendEmptyMessage(2);
            }

            @Override
            public void onError(int i, String s) {
                mView.showToast("网络连接超时");
            }
        });
    }

    @Override
    public void addNewComment(Context context, final Handler handler, String comment_content, String answerID, String questionID, String answer_userID) {
        _User user = BmobUser.getCurrentUser(context, _User.class);
        Answer answer = new Answer();
        answer.setObjectId(answerID);
        Question question = new Question();
        question.setObjectId(questionID);
        final Comment comment = new Comment();
        comment.setUser(user);
        comment.setQuestion(question);
        comment.setAnswer(answer);
        comment.setMcontent(comment_content);
        comment.setState(1);
        comment.setLikes(0);
        comment.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                mView.showToast("添加评论成功");
                Message msg = new Message();
                Bundle bundle=new Bundle();
                bundle.putString("new_commentID",comment.getObjectId());
                msg.setData(bundle);
                msg.obj = comment;
                msg.what = 5;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(int i, String s) {
                mView.showToast("网络连接超时");
            }
        });



    }

    @Override
    public void getNewComment(Context context, final Handler handler, String newCommentID) {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.include("user");
        query.getObject(context, newCommentID, new GetListener<Comment>() {
            @Override
            public void onSuccess(Comment comment) {
                comment.getUser();
                comment.getMcontent();
                Message msg = new Message();
                msg.obj = comment;
                msg.what = 6;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(int i, String s) {
            }
        });

    }

    @Override
    public void getMoreComment(Context context, final Handler handler, String answerID, final ArrayList<Comment> newComments, int item_count) {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.setLimit(20);
        query.setSkip(item_count);
        Answer answer = new Answer();
        answer.setObjectId(answerID);
        query.addWhereEqualTo("answer", new BmobPointer(answer));
        query.include("user");
        query.order("-createdAt");
        query.findObjects(context, new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {
                for (Comment comment : list) {
                    comment.getState();
                    comment.getUser();
                    comment.getMcontent();
                    newComments.add(comment);
                }
                if (newComments != null) {
                    Message msg=new Message();
                    msg.obj=newComments;
                    msg.what=7;
                    handler.sendMessage(msg);
                }
            }

            @Override
            public void onError(int i, String s) {
                mView.showToast("网络连接超时");
            }
        });
    }
}
