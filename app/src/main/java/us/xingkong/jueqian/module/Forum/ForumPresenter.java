package us.xingkong.jueqian.module.Forum;


import android.content.Context;
import android.os.Handler;
import android.os.Message;

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
    public void getBmobQuestion(Context context, final ArrayList<Question> questions, final Handler handler, int flag) {
        BmobQuery<Question> query = new BmobQuery<>();
        query.setLimit(20);
        query.order("-createdAt");
        query.include("user");
        if (flag == 1) {   //flag判断当前是刷新还是第一次加载页面
            Boolean isInCache = query.hasCachedResult(context, Question.class);
            if (isInCache) {
                query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
            } else {
                query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ONLY);
            }
        } else if (flag == 2) {
            query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ONLY);
        }
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
                    question.getAnswer_count();
                    question.getState();
                    questions.add(question);

                }
                Message msg=new Message();
                msg.obj=questions;
                msg.what=5;
                handler.sendMessage(msg);
            }

            @Override
            public void onError(int i, String s) {
                mView.showToast("网络有点差哦！可重新刷新！");
                handler.sendEmptyMessage(4);
            }
        });

    }

    @Override
    public void getMoreBmobQuestion(Context context, final ArrayList<Question> questions, final Handler handler,int item_count) {
        BmobQuery<Question> query = new BmobQuery<>();
        query.setSkip(item_count);
        query.setLimit(20);
        query.order("-createdAt");
        query.include("user");
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
                    question.getAnswer_count();
                    question.getState();
                    questions.add(question);

                }
                Message msg=new Message();
                msg.obj=questions;
                msg.what=6;
                handler.sendMessage(msg);
            }

            @Override
            public void onError(int i, String s) {
                handler.sendEmptyMessage(7);
            }
        });
    }
}