package us.xingkong.jueqian.module.Forum.NewPage;

import android.content.Context;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;

/**
 * Created by boluoxiaomo
 * Date: 17/1/9
 */

public class NewPresenter extends BasePresenterImpl implements NewContract.Presenter {

    private final NewContract.View mView;
    public NewPresenter(NewContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public void addQuestion(final Context context, String title, final String content, String tag1, String tag2) {
        _User user= BmobUser.getCurrentUser(context,_User.class);
        if(user!=null){
            Question question=new Question();
            question.setUser(user);
            question.setMcontent(content);
            question.setMtitle(title);
            question.setTAG1_ID(tag1);
            question.setTAG2_ID(tag2);
            question.setState(1);
            question.save(context, new SaveListener() {
                @Override
                public void onSuccess() {
                    mView.showToast("save success");
                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
        }
    }
}
