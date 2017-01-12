package us.xingkong.jueqian.bean.ForumBean.RealmBean;



import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Garfield on 1/12/17.
 */

public class ForumPageBean extends RealmObject{
    @PrimaryKey
    private String OBJ_ID;

    private String profileURL;
    private String sender;
    private Integer sender_state;
    private String mtitle;/*问题的标题*/
    private boolean isHided;/*是否为匿名问题*/
    private String TAG1;/*第一个Tag的id*/
    private String TAG2;/*第二个Tag的id*/
    private Integer good_count;/*点赞的数量*/
    private Integer answer_count;/*点赞的数量*/
    private Date time_create;/*被作者创建的时间*/
    private Date last_update;/*作者最后更新时间*/
    private Integer state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getOBJ_ID() {
        return OBJ_ID;
    }

    public void setOBJ_ID(String OBJ_ID) {
        this.OBJ_ID = OBJ_ID;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Integer getSender_state() {
        return sender_state;
    }

    public void setSender_state(Integer sender_state) {
        this.sender_state = sender_state;
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public boolean isHided() {
        return isHided;
    }

    public void setHided(boolean hided) {
        isHided = hided;
    }

    public String getTAG1() {
        return TAG1;
    }

    public void setTAG1(String TAG1) {
        this.TAG1 = TAG1;
    }

    public String getTAG2() {
        return TAG2;
    }

    public void setTAG2(String TAG2) {
        this.TAG2 = TAG2;
    }

    public Integer getGood_count() {
        return good_count;
    }

    public void setGood_count(Integer good_count) {
        this.good_count = good_count;
    }

    public Integer getAnswer_count() {
        return answer_count;
    }

    public void setAnswer_count(Integer answer_count) {
        this.answer_count = answer_count;
    }

    public Date getTime_create() {
        return time_create;
    }

    public void setTime_create(Date time_create) {
        this.time_create = time_create;
    }

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }
}
