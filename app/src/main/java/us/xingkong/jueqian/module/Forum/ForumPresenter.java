package us.xingkong.jueqian.module.Forum;

import android.util.Log;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.bean.ForumBean.BombBean.TagBean;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;
import us.xingkong.jueqian.bean.ForumBean.GsonBean.GSON_ForumPageBean;
import us.xingkong.jueqian.data.RepositData.ForumRepository;
import us.xingkong.jueqian.utils.ToastUtils;


/**
 * Created by boluoxiaomo
 * Date: 17/1/9
 */

public class ForumPresenter extends BasePresenterImpl implements ForumContract.Presenter {

    private final ForumContract.View mView;
    private ForumRepository mRepository;
    private ArrayList<GSON_ForumPageBean> infoSet;
    private int i;
    private int size_of_list;

    public ForumPresenter(ForumContract.View view, ForumRepository mRepository) {
        mView = view;
        this.mView.setPresenter(this);
        this.mRepository = mRepository;
    }


    @Override
    public void initList() {

//        if (mRepository.initDataFromLocal(new Date(System.currentTimeMillis())) != null) {
//            Log.d("bf", "意外发生了！44");
////            mView.initShowList(mRepository.initDataFromLocal(new Date(System.currentTimeMillis())));
//            mView.initShowList(infoSet);
//        } else {
//            Log.d("bf", "意外发生了！45");
//            mView.showToast("initList fail;get null from local&cloud");
//        }
        getForumList(true, new Date(System.currentTimeMillis()), 20);
    }


    @Override
    public void getForumList(boolean isNewest, Date date, int num) {
        i = 0;
        infoSet = new ArrayList<>();
//        Bmob.initialize(mContext, "2d6a319fa542339021237173a1990ead");
        BmobQuery<Question> query = new BmobQuery<>();
        query.order("-createdAt");
        query.addWhereEqualTo("state", 1);
        if (!isNewest) {
            query.addWhereLessThanOrEqualTo("createdAt", date);
        }
        query.setLimit(num);
        query.findObjects(mView.getmContext(), new FindListener<Question>() {

            @Override
            public void onSuccess(List<Question> list) {
                /*for (int i = 0; i < list.size(); i++) */
                size_of_list = list.size();
//                while (i < list.size()) {
                    /**    private String OBJ_ID;
                     private String profileURI;
                     private String sender;
                     private Integer sender_state;
                     private String mtitle;
                     private String isHided;
                     private String TAG1;
                     private String TAG2;
                     private Integer good_count;
                     private Integer answer_count;
                     private Date time_create;
                     private Date last_update;*/
                    GSON_ForumPageBean forumPageBean = new GSON_ForumPageBean();
                    forumPageBean.setOBJ_ID(list.get(i).getObjectId());
                    forumPageBean.setMtitle(list.get(i).getMtitle());
                    forumPageBean.setHided(list.get(i).isHided());
                    forumPageBean.setGood_count(list.get(i).getGood_count());
                    forumPageBean.setAnswer_count(list.get(i).getAnswer_count());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        forumPageBean.setTime_create(format.parse(list.get(i).getCreatedAt()));
                        forumPageBean.setLast_update(format.parse(list.get(i).getUpdatedAt()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Log.d("hhhh", "这里-" + i);

//                    forumPageBean.setTAG1(getTag(list.get(i).getTAG1_ID()));
//                    forumPageBean.setTAG2(list.get(i).getTAG2_ID());
                    getLeastInfo(forumPageBean, list.get(i).getTAG1_ID(), list.get(i).getTAG2_ID(), list.get(i).getSENDER_ID());


//                }

            }

            @Override
            public void onError(int i, String s) {
                ToastUtils.shortToast(mView.getmContext(), "请求bmob错误");
            }
        });


    }

    private void getLeastInfo(final GSON_ForumPageBean forumPageBean, String tag1_id, final String tag2_id, final String sender_id) {
        BmobQuery<TagBean> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(mView.getmContext(), tag1_id, new GetListener<TagBean>() {
            @Override
            public void onSuccess(TagBean tagBean) {
                forumPageBean.setTAG1(tagBean.getTag());
                getTag2(forumPageBean, tag2_id, sender_id);
                Log.d("hhhh", "这里--" + i);

            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.shortToast(mView.getmContext(), "请求bmob错误");
            }
        });

    }


    private void getTag2(final GSON_ForumPageBean forumPageBean, final String tag_id, final String sender_id) {
        BmobQuery<TagBean> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(mView.getmContext(), tag_id, new GetListener<TagBean>() {
            @Override
            public void onSuccess(TagBean tagBean) {
                forumPageBean.setTAG2(tagBean.getTag());
                getUserInfo(forumPageBean, sender_id);
                Log.d("hhhh", "这里---" + i);

            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.shortToast(mView.getmContext(), "请求bmob错误");
            }
        });
    }

    private void getUserInfo(final GSON_ForumPageBean forumPageBean, final String sender_id) {
        BmobQuery<_User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(mView.getmContext(), sender_id, new GetListener<_User>() {
            @Override
            public void onSuccess(_User user) {
                forumPageBean.setSender(user.getUsername());
                forumPageBean.setSender_state(user.getState());
                forumPageBean.setProfileURL(user.getProfile().getUrl());
                infoSet.add(i, forumPageBean);
                i++;
                Log.d("hhhh", "这里----" + i);
//                if (i == size_of_list) {
                    Log.d("hhhh", "这里" + infoSet.size());
                    infoSet.size();
                    mView.initShowList(infoSet);
//                }
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.shortToast(mView.getmContext(), "请求bmob错误");
            }
        });
    }


}
