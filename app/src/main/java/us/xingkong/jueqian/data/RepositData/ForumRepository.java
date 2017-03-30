package us.xingkong.jueqian.data.RepositData;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;

import cn.bmob.v3.BmobQuery;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import us.xingkong.jueqian.base.Constants;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.bean.ForumBean.GsonBean.GSON_ForumPageBean;
import us.xingkong.jueqian.bean.ForumBean.RealmBean.ForumPageBean;


/**
 * Created by Garfield on 1/11/17.
 */

public class ForumRepository {
    private static int isGot_getTag;
    private static int isGot_getDataFromBmob;
    private static int isGot1;
    private static int isGot2;
    private static int isGot3;
    private String mTag;
    private String mUsername;
    private Integer mState;
    private String mProfileURL;
    private Context mContext;
    private Realm realm;
    private ArrayList<GSON_ForumPageBean> arr = new ArrayList<>();
    BmobQuery<Question> query;

    public ForumRepository(Context mContext) {
        this.mContext = mContext;
    }

    public boolean verfied() {
        /*检测用户登录是否有效*/
        return true;
    }

    public ArrayList<GSON_ForumPageBean> initDataFromLocal() {

        Realm.init(mContext);
        realm = Realm.getDefaultInstance();
        RealmQuery<ForumPageBean> query = RealmQuery.createQuery(realm, ForumPageBean.class);
        if (query.findAll() != null) {
            if (query.findAll().size() != 0) {
//                query.lessThanOrEqualTo("create_time", target_time);
                query.findAllSorted("create_time", Sort.DESCENDING);

                RealmResults<ForumPageBean> results = query.findAll();

                /**从本地数据库拿数据  */
                int i = 0;
                while (results.get(i) != null) {
                    if (results.get(i).getState() == Constants.QUESTION_FROZED) {
                        i++;
                        continue;
                    }
                    Gson gson = new Gson();
                    GSON_ForumPageBean forumPageBean = new GSON_ForumPageBean();
                    forumPageBean.setTAG1(results.get(i).getTAG1());
                    forumPageBean.setTAG2(results.get(i).getTAG2());
                    forumPageBean.setAnswer_count(results.get(i).getAnswer_count());
                    forumPageBean.setMtitle(results.get(i).getMtitle());
                    forumPageBean.setHided(results.get(i).isHided());
                    forumPageBean.setSender(results.get(i).getSender());
                    forumPageBean.setProfileURL(results.get(i).getProfileURL());
                    forumPageBean.setSender_state(results.get(i).getSender_state());
                    arr.add(forumPageBean);
                    if (arr.size() >= 20) {
                        break;
                    }
                    i++;
                }
            }
        }
        if (arr.size() != 0) {
            return arr;
        } else {
            return null;
        }
    }

//    public ArrayList<GSON_ForumPageBean> getDataFromBmob(int num, Date date, boolean isNewest) {
//        isGot_getDataFromBmob = 0;
////        Bmob.initialize(mContext, "2d6a319fa542339021237173a1990ead");
//        query = new BmobQuery<>();
//        query.order("-createdAt");
//        query.addWhereEqualTo("state", 1);
//        if (!isNewest) {
//            query.addWhereLessThanOrEqualTo("createdAt", date);
//        }
//        query.setLimit(num);
//        query.findObjects(mContext, new FindListener<Question>() {
//
//            @Override
//            public void onSuccess(List<Question> list) {
//                isGot_getDataFromBmob = 1;
//                for (int i = 0; i < list.size(); i++) {
//                    /** private String OBJ_ID;
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
//                    GSON_ForumPageBean forumPageBean = new GSON_ForumPageBean();
//                    forumPageBean.setOBJ_ID(list.get(i).getObjectId());
//                    forumPageBean.setMtitle(list.get(i).getMtitle());
//                    forumPageBean.setHided(list.get(i).isHided());
//                    forumPageBean.setGood_count(list.get(i).getLike());
//                    forumPageBean.setAnswer_count(list.get(i).getAnswer_count());
//                    forumPageBean.setTime_create(new Date(list.get(i).getCreatedAt()));
//                    forumPageBean.setLast_update(new Date(list.get(i).getUpdatedAt()));
//
//                    forumPageBean.setTAG1(getTag(list.get(i).getTAG1_ID()));
//                    forumPageBean.setTAG2(list.get(i).getTAG2_ID());
//                    forumPageBean.setSender_state(getUserState(list.get(i).getSENDER_ID()));
//                    forumPageBean.setSender(getUsername(list.get(i).getSENDER_ID()));
//                    forumPageBean.setProfileURL(getProfileURL(list.get(i).getSENDER_ID()));
//                    arr.add(i, forumPageBean);
//                }
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                ToastUtils.shortToast(mContext, "请求bmob错误");
//                Log.d("bf", "意外发生了！67");
//                isGot_getDataFromBmob = -1;
//            }
//        });
//        return arr;
//    }
//
//    private String getUsername(String sender_id) {
//        isGot1 = 0;
//        BmobQuery<_User> bmobQuery = new BmobQuery<>();
//        bmobQuery.getObject(mContext, sender_id, new GetListener<_User>() {
//            @Override
//            public void onSuccess(_User user) {
//                isGot1 = 1;
//                mUsername = user.getUsername();
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                isGot1 = -1;
//                ToastUtils.shortToast(mContext, "请求bmob错误");
//            }
//        });
//        while (isGot1 == 0) {
//        }
//        return mUsername;
//    }
//
//    private Integer getUserState(String sender_id) {
//        isGot2 = 0;
//        BmobQuery<_User> bmobQuery = new BmobQuery<>();
//        bmobQuery.getObject(mContext, sender_id, new GetListener<_User>() {
//            @Override
//            public void onSuccess(_User user) {
//                isGot2 = 1;
//                mState = user.getState();
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                isGot2 = -1;
//                ToastUtils.shortToast(mContext, "请求bmob错误");
//            }
//        });
//        while (isGot2 == 0) {
//        }
//        return mState;
//    }
//
//    private String getProfileURL(final String sender_id) {
//        isGot3 = 0;
//        BmobQuery<_User> bmobQuery = new BmobQuery<>();
//        bmobQuery.getObject(mContext, sender_id, new GetListener<_User>() {
//            @Override
//            public void onSuccess(_User user) {
//                isGot3 = 1;
//                mProfileURL = user.getProfile().getUrl();
//
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                isGot3 = -1;
//                ToastUtils.shortToast(mContext, "请求bmob错误");
//            }
//        });
//        while (isGot3 == 0) {
//        }
//        if (isGot3 == -1) {
//            return null;
//        }
//        return mProfileURL;
//    }
//
//    private String getTag(final String tag_id) {
//        isGot_getTag = 0;
//        BmobQuery<TagBean> bmobQuery = new BmobQuery<>();
//        bmobQuery.getObject(mContext, tag_id, new GetListener<TagBean>() {
//            @Override
//            public void onSuccess(TagBean tagBean) {
//                isGot_getTag = 1;
//                mTag = tagBean.getTag();
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                isGot_getTag = -1;
//                ToastUtils.shortToast(mContext, "请求bmob错误");
//            }
//        });
//        while (isGot_getTag == 0) {
//        }
//        return mTag;
//    }


}
