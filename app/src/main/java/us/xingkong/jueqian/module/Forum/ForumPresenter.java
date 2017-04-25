package us.xingkong.jueqian.module.Forum;


import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.data.RepositData.ForumRepository;


/**
 * Created by boluoxiaomo
 * Date: 17/1/9
 */

public class ForumPresenter extends BasePresenterImpl implements ForumContract.Presenter {

    private static int FLAG = 0;

    private final ForumContract.View mView;
    private ForumRepository mRepository;
    private List<Question> infoSet=new ArrayList<>(
    );


    private Lock lock = new ReentrantLock();
    Condition condition_pro = lock.newCondition();
    Condition condition_con = lock.newCondition();


    public ForumPresenter(ForumContract.View view, ForumRepository mRepository) {
        mView = view;
        this.mView.setPresenter(this);
        this.mRepository = mRepository;
    }




    @Override
    public List<Question> getBmobQuestion(Context context, final ArrayList<Question> questions, final Handler handler) {
        BmobQuery<Question> query=new BmobQuery<>();
        query.setLimit(20);
        query.order("-createdAt");
        query.include("user");
        query.findObjects(context, new FindListener<Question>() {
            @Override
            public void onSuccess(List<Question> list) {
                for(Question question:list){
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

            }
        });

        return questions;
    }


//
//    @Override
//    public void getForumListFromBmob(boolean isNewest, Date date, int num) {
//        i = 0;
//        infoSet = new ArrayList<>();
//        BmobQuery<Question> query = new BmobQuery<>();
//        query.order("-createdAt");
//        query.addWhereEqualTo("state", 1);
//        if (!isNewest) {
//            query.addWhereLessThanOrEqualTo("createdAt", date);
//        }
//        query.setLimit(num);
//        query.findObjects(mView.getmContext(), new FindListener<Question>() {
//
//            @Override
//            public void onSuccess(final List<Question> list) {
//                /*for (int i = 0; i < list.size(); i++) */
//
//                size_of_list = list.size();
//                while (i < list.size()) {
//
//                    /**    private String OBJ_ID;
//                     private String profileURI;
//                     private String sender;
//                     private Integer sender_state;
//                     private String mtitle;
//                     private String isHided;
//                     private String TAG1;
//                     private String TAG2;
//                     private Integer good_count;
//                     private Integer answer_count;
//                     private Date time_create;
//                     private Date last_update;*/
//                    forumPageBean = new GSON_ForumPageBean();
//                    forumPageBean.setOBJ_ID(list.get(i).getObjectId());
//                    forumPageBean.setMtitle(list.get(i).getMtitle());
//                    forumPageBean.setHided(list.get(i).isHided());
//                    forumPageBean.setGood_count(list.get(i).getLike());
//                    forumPageBean.setAnswer_count(list.get(i).getAnswer_count());
//                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    try {
//                        forumPageBean.setTime_create(format.parse(list.get(i).getCreatedAt()));
//                        forumPageBean.setLast_update(format.parse(list.get(i).getUpdatedAt()));
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    Log.d("hhhh", "这里-" + i);
//
////                    forumPageBean.setTAG1(getTag(list.get(i).getTAG1_ID()));
////                    forumPageBean.setTAG2(list.get(i).getTAG2_ID());
//                    getLeastInfo(forumPageBean, list.get(i).getTAG1_ID(), list.get(i).getTAG2_ID(), list.get(i).getSENDER_ID());
////                    lock.lock();
////                    try {
////                        condition_con.await();
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    } finally {
////                        lock.unlock();
////                    }
//                    while (FLAG == 0) {
//                    }
//                    FLAG = 0;
//                }
//
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                ToastUtils.shortToast(mView.getmContext(), "请求bmob错误");
//            }
//        });
//
//
//    }
//
//    private void getLeastInfo(final GSON_ForumPageBean forumPageBean, String tag1_id, final String tag2_id, final String sender_id) {
////        lock.lock();
//
//        BmobQuery<TagBean> bmobQuery = new BmobQuery<>();
//        bmobQuery.getObject(mView.getmContext(), tag1_id, new GetListener<TagBean>() {
//            @Override
//            public void onSuccess(TagBean tagBean) {
//                forumPageBean.setTAG1(tagBean.getTag());
//                getTag2(forumPageBean, tag2_id, sender_id);
//                Log.d("hhhh", "这里--" + i);
//
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                ToastUtils.shortToast(mView.getmContext(), "请求bmob错误");
//            }
//        });
//
//
//    }
//
//
//    private void getTag2(final GSON_ForumPageBean forumPageBean, final String tag_id, final String sender_id) {
//        BmobQuery<TagBean> bmobQuery = new BmobQuery<>();
//        bmobQuery.getObject(mView.getmContext(), tag_id, new GetListener<TagBean>() {
//            @Override
//            public void onSuccess(TagBean tagBean) {
//                forumPageBean.setTAG2(tagBean.getTag());
//                getUserInfo(forumPageBean, sender_id);
//                Log.d("hhhh", "这里---" + i);
//
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                ToastUtils.shortToast(mView.getmContext(), "请求bmob错误");
//            }
//        });
//    }
//
//    private void getUserInfo(final GSON_ForumPageBean forumPageBean, final String sender_id) {
//        BmobQuery<_User> bmobQuery = new BmobQuery<>();
//        bmobQuery.getObject(mView.getmContext(), sender_id, new GetListener<_User>() {
//            @Override
//            public void onSuccess(_User user) {
////                lock.lock();
//                forumPageBean.setSender(user.getUsername());
//                forumPageBean.setSender_state(user.getState());
//                forumPageBean.setProfileURL(user.getProfile().getUrl());
//                infoSet.add(i, forumPageBean);
//                i++;
//                Log.d("hhhh", "这里----" + i);
//                if (i == size_of_list) {
//
//                    Log.d("hhhh", "这里" + infoSet.size());
//                    infoSet.size();
//                    mView.initShowList(infoSet);
//                }
//                FLAG = 1;
////                condition_con.signal();
////                try {
////
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////                lock.unlock();
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                ToastUtils.shortToast(mView.getmContext(), "请求bmob错误");
//            }
//        });
//    }
//
//
}
