package us.xingkong.jueqian.data.RealSData;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import us.xingkong.jueqian.base.Constants;
import us.xingkong.jueqian.bean.ForumBean.BombBean.TagBean;
import us.xingkong.jueqian.bean.ForumBean.BombBean.User;
import us.xingkong.jueqian.bean.ForumBean.GsonBean.GSON_ForumPageBean;
import us.xingkong.jueqian.bean.ForumBean.RealmBean.ForumPageBean;
import us.xingkong.jueqian.utils.IOFiles;
import us.xingkong.jueqian.utils.ToastUtils;


/**
 * Created by Garfield on 1/11/17.
 */

public class ForumRepository {
    private int isGot_getTag;
    private int isGot_getDataFromBmob;
    private int isGot1;
    private int isGot2;
    private int isGot3;
    private String mTag;
    private String mUsername;
    private Integer mState;
    private String mProfileURI;
    private Context mContext;
    private Realm realm;
    private ArrayList<GSON_ForumPageBean> arr = new ArrayList<>();

    public ForumRepository(Context mContext) {
        this.mContext = mContext;
    }

    public boolean verfied() {
        /*检测用户登录是否有效*/
        return true;
    }

    public ArrayList<GSON_ForumPageBean> initDataFromLocal(Date target_time) {

        Realm.init(mContext);
        realm = Realm.getDefaultInstance();
        RealmQuery<ForumPageBean> query = RealmQuery.createQuery(realm, ForumPageBean.class);
        query.lessThanOrEqualTo("create_time", target_time);
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

            forumPageBean.setSender(results.get(i).getSender());
            forumPageBean.setProfileURI(results.get(i).getProfileURI());
            forumPageBean.setSender_state(results.get(i).getSender_state());
            arr.add(forumPageBean);
            if (arr.size() >= 20) {
                break;
            }
            i++;
        }
        if (arr.size() != 0) {
            return arr;
        } else {
            getDataFromBmob(20, new Date(System.currentTimeMillis()), false);
            return null;
        }
    }

    public ArrayList<GSON_ForumPageBean> getDataFromBmob(int num, Date date, boolean isNewest) {
        isGot_getDataFromBmob = 0;
        BmobQuery<us.xingkong.jueqian.bean.ForumBean.BombBean.Question> query = new BmobQuery<>();
        query.order("-createdAt");
        query.addWhereEqualTo("state", 1);
        if (!isNewest) {
            query.addWhereLessThanOrEqualTo("createdAt", date);
        }
        query.setLimit(num);
        query.findObjects(mContext, new FindListener<us.xingkong.jueqian.bean.ForumBean.BombBean.Question>() {

            @Override
            public void onSuccess(List<us.xingkong.jueqian.bean.ForumBean.BombBean.Question> list) {
                isGot_getDataFromBmob = 1;
                for (int i = 0; i < list.size(); i++) {

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
                    forumPageBean.setProfileURI(getProfileURI(list.get(i).getSENDER_ID()));
                    forumPageBean.setOBJ_ID(list.get(i).getObjectId());
                    forumPageBean.setSender(getUsername(list.get(i).getSENDER_ID()));
                    forumPageBean.setSender_state(getUserState(list.get(i).getSENDER_ID()));
                    forumPageBean.setMtitle(list.get(i).getMtitle());
                    forumPageBean.setIsHided(list.get(i).getIsHided());
                    forumPageBean.setTAG1(getTag(list.get(i).getTAG1_ID()));
                    forumPageBean.setTAG2(list.get(i).getTAG2_ID());
                    forumPageBean.setGood_count(list.get(i).getGood_count());
                    forumPageBean.setAnswer_count(list.get(i).getAnswer_count());
                    forumPageBean.setTime_create(Date.valueOf(list.get(i).getCreatedAt()));
                    forumPageBean.setLast_update(Date.valueOf(list.get(i).getUpdatedAt()));
                    arr.add(i, forumPageBean);
                }

            }

            @Override
            public void onError(int i, String s) {
                ToastUtils.shortToast(mContext, "请求bmob错误");
                isGot_getDataFromBmob = -1;
            }
        });
        while (isGot_getDataFromBmob == 0) {
        }
        return arr;
    }

    private String getUsername(String sender_id) {
        isGot1 = 0;
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(mContext, sender_id, new GetListener<User>() {
            @Override
            public void onSuccess(User user) {
                isGot1 = 1;
                mUsername = user.getUsername();
            }

            @Override
            public void onFailure(int i, String s) {
                isGot1 = -1;
                ToastUtils.shortToast(mContext, "请求bmob错误");
            }
        });
        while (isGot1 == 0) {
        }
        return mUsername;
    }

    private Integer getUserState(String sender_id) {
        isGot2 = 0;
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(mContext, sender_id, new GetListener<User>() {
            @Override
            public void onSuccess(User user) {
                isGot2 = 1;
                mState = user.getState();
            }

            @Override
            public void onFailure(int i, String s) {
                isGot2 = -1;
                ToastUtils.shortToast(mContext, "请求bmob错误");
            }
        });
        while (isGot2 == 0) {
        }
        return mState;
    }

    private String getProfileURI(String sender_id) {
        isGot3 = 0;
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(mContext, sender_id, new GetListener<User>() {
            @Override
            public void onSuccess(User user) {
                isGot3 = 1;
//                user.getProfile().getUrl();
//                IOFiles.toSaveFile(BitmapFactory.user.getProfile().)
//                mState = user.getProfile();
            }

            @Override
            public void onFailure(int i, String s) {
                isGot3 = -1;
                ToastUtils.shortToast(mContext, "请求bmob错误");
            }
        });
        while (isGot3 == 0) {
        }
        return mProfileURI;
    }

    private String getTag(final String tag_id) {
        isGot_getTag = 0;
        BmobQuery<TagBean> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(mContext, tag_id, new GetListener<TagBean>() {
            @Override
            public void onSuccess(TagBean tagBean) {
                isGot_getTag = 1;
                mTag = tagBean.getTag();
            }

            @Override
            public void onFailure(int i, String s) {
                isGot_getTag = -1;
                ToastUtils.shortToast(mContext, "请求bmob错误");
            }
        });
        while (isGot_getTag == 0) {
        }
        return mTag;
    }


}
