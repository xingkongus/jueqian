package us.xingkong.jueqian.module.Forum;


import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.data.RepositData.ForumRepository;


public class ForumPresenter extends BasePresenterImpl implements ForumContract.Presenter {

    private final ForumContract.View mView;
    private ForumRepository mRepository;


    public ForumPresenter(ForumContract.View view, ForumRepository mRepository) {
        mView = view;
        this.mView.setPresenter(this);
        this.mRepository = mRepository;
    }


    @Override
    public List<Question> getBmobQuestion(Context context, final ArrayList<Question> questions, final Handler handler) {
        BmobQuery<Question> query = new BmobQuery<>();
        query.setLimit(20);
        query.order("-createdAt");
        query.include("user");
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(context, new FindListener<Question>() {
            @Override
            public void onSuccess(List<Question> list) {
                for (Question question : list) {
                    question.getObjectId();
                    question.getMtitle();
                    question.getMcontent();
                    question.getTAG1_ID();
                    question.getTAG2_ID();
                    question.getUser();
                    questions.add(question);

                }
                handler.sendEmptyMessage(3);
            }

            @Override
            public void onError(int i, String s) {
                mView.showToast("网络连接错误"+s);
            }
        });

        return questions;
    }
}