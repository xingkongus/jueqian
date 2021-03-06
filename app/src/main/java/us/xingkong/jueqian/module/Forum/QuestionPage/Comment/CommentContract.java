package us.xingkong.jueqian.module.Forum.QuestionPage.Comment;

import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;

import us.xingkong.jueqian.base.BasePresenter;
import us.xingkong.jueqian.base.BaseView;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Comment;


public interface CommentContract {
    interface View extends BaseView<Presenter> {
        void setRecyclewViewBug();
    }

    interface Presenter extends BasePresenter {
        void getAnswer(Context context,String answerID,Handler handler);
        void getAnswerComments(Context context, Handler handler, String answerID, ArrayList<Comment> comments);
        void addNewComment(Context context,Handler handler,String comment,String answerID,String questionID,String answer_userID);
        void getNewComment(Context context,Handler handler,String newCommentID);
        void getMoreComment(Context context,Handler handler,String answerID,ArrayList<Comment>newComments,int item_count);
    }
}
