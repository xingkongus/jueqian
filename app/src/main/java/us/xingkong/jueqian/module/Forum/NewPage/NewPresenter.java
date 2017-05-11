package us.xingkong.jueqian.module.Forum.NewPage;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;


public class NewPresenter extends BasePresenterImpl implements NewContract.Presenter {

    private final NewContract.View mView;
    public NewPresenter(NewContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public void addQuestion(final Context context, String title, final String content, String tag1, String tag2, final Handler handler) {
        _User user= BmobUser.getCurrentUser(context,_User.class);
        if(user!=null){
            final Question question=new Question();
            question.setUser(user);
            question.setMcontent(content);
            question.setMtitle(title);
            question.setTAG1_ID(tag1);
            question.setTAG2_ID(tag2);
            question.setState(0);
            question.setAnswer_count(0);
            question.setShouzanFlag(0);
            question.save(context, new SaveListener() {
                @Override
                public void onSuccess() {
                    mView.showToast("添加成功");
                    Message msg=new Message();
                    msg.obj=question.getObjectId();
                    msg.what=1;
                    handler.sendMessage(msg);
                }

                @Override
                public void onFailure(int i, String s) {
                    mView.showToast("网络连接超时");
                }
            });
        }
    }

    @Override
    public void addMyQuestion(Context context, String myquestionID) {
        _User user=BmobUser.getCurrentUser(context,_User.class);
        Question question=new Question();
        question.setObjectId(myquestionID);
        BmobRelation bmobRelation=new BmobRelation();
        bmobRelation.add(question);
        user.setQuestions(bmobRelation);
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
