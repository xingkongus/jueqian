package us.xingkong.jueqian.data.RealSData;

import android.content.Context;

import com.google.gson.Gson;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import us.xingkong.jueqian.base.Constants;
import us.xingkong.jueqian.bean.ForumBean.GsonBean.ForumPageBean;
import us.xingkong.jueqian.bean.ForumBean.RealmBean.Question;
import us.xingkong.jueqian.bean.ForumBean.RealmBean.User;
import us.xingkong.jueqian.utils.ToastUtils;


/**
 * Created by Garfield on 1/11/17.
 */

public class ForumRepository {
    private Context mContext;
    private Realm realm;
    private ArrayList<ForumPageBean> arr = new ArrayList<>();

    public ForumRepository(Context mContext) {
        this.mContext = mContext;
    }

    public boolean verfied() {
        /*检测用户登录是否有效*/
        return true;
    }

    public ArrayList<ForumPageBean> initDataFromLocal(Date target_time) {

        Realm.init(mContext);
        realm = Realm.getDefaultInstance();
        RealmQuery<Question> query = RealmQuery.createQuery(realm, Question.class);
        query.lessThanOrEqualTo("create_time", target_time);
        query.findAllSorted("create_time", Sort.DESCENDING);
        RealmResults<Question> results = query.findAll();

        /**从本地数据库拿数据  */
        int i = 0;
        while (results.get(i) != null) {
            if (results.get(i).getState() == Constants.QUESTION_FROZED) {
                i++;
                continue;
            }

            Gson gson = new Gson();

            ForumPageBean forumPageBean = new ForumPageBean();
            forumPageBean.setTAG1_ID(results.get(i).getTAG1_ID());
            forumPageBean.setTAG2_ID(results.get(i).getTAG2_ID());
            forumPageBean.setAnswer_count(results.get(i).getAnswer_count());
            forumPageBean.setMtitle(results.get(i).getMtitle());

            forumPageBean.setSender(getUsernameLocal(results.get(i).getSENDER_ID()));
            forumPageBean.setProfileURI(getProfilesURILocal(results.get(i).getSENDER_ID()));
            forumPageBean.setSender_state(getUserStateLocal(results.get(i).getSENDER_ID()));
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

    public void getDataFromBmob(int num, Date date, boolean isNewest) {

        BmobQuery<us.xingkong.jueqian.bean.ForumBean.BombBean.Question> query = new BmobQuery<>();
        query.order("-createdAt");
        query.addWhereEqualTo("state",1);
        if (!isNewest) {
            query.addWhereLessThanOrEqualTo("createdAt", date);
        }
        query.setLimit(num);
        query.findObjects(mContext, new FindListener<us.xingkong.jueqian.bean.ForumBean.BombBean.Question>() {

            @Override
            public void onSuccess(List<us.xingkong.jueqian.bean.ForumBean.BombBean.Question> list) {
                for (int i = 0; i<list.size();i++){
                    ForumPageBean forumPageBean = new ForumPageBean();
                    forumPageBean.setTAG1_ID(list.get(i).getTAG1_ID());
                    forumPageBean.setTAG2_ID(list.get(i).getTAG2_ID());
                    forumPageBean.setAnswer_count(list.get(i).getAnswer_count());
                    forumPageBean.setMtitle(list.get(i).getMtitle());

                    forumPageBean.setSender(getUsernameLocal(list.get(i).getSENDER_ID()));
                    forumPageBean.setProfileURI(getProfilesURILocal(list.get(i).getSENDER_ID()));
                    forumPageBean.setSender_state(getUserStateLocal(list.get(i).getSENDER_ID()));


                    arr.add(i,forumPageBean);
                }
            }

            @Override
            public void onError(int i, String s) {
                ToastUtils.shortToast(mContext,"请求bmob错误");
            }
        });
    }

    private String getProfilesURILocal(String userId) {
        RealmQuery<User> query = RealmQuery.createQuery(realm, User.class);
        query.equalTo("OBJ_ID", userId);
        RealmResults<User> results = query.findAll();
        return results.get(0).getOBJ_ID();
    }

    private String getUsernameLocal(String Id) {
        RealmQuery<User> query = RealmQuery.createQuery(realm, User.class);
        query.equalTo("OBJ_ID", Id);
        RealmResults<User> results = query.findAll();
        return results.get(0).getUsername();
    }

    private Integer getUserStateLocal(String Id) {
        RealmQuery<User> query = RealmQuery.createQuery(realm, User.class);
        query.equalTo("OBJ_ID", Id);
        RealmResults<User> results = query.findAll();
        return results.get(0).getState();
    }


}
