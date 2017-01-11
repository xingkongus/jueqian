package us.xingkong.jueqian.bean.ForumBean.GsonBean;

import java.sql.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Garfield on 1/11/17.
 */

public class ForumPageBean {
    @PrimaryKey
    private String OBJ_ID;

    private String profileURI;
    private String sender;
    private Integer sender_state;
    private String mtitle;/*问题的标题*/
    private String isHided;/*是否为匿名问题*/
    private String TAG1_ID;/*第一个Tag的id*/
    private String TAG2_ID;/*第二个Tag的id*/
    private Integer good_count;/*点赞的数量*/
    private Integer answer_count;/*点赞的数量*/
    private Date time_create;/*被作者创建的时间*/
    private Date last_update;/*作者最后更新时间*/


    public String getOBJ_ID() {
        return OBJ_ID;
    }

    public void setOBJ_ID(String OBJ_ID) {
        this.OBJ_ID = OBJ_ID;
    }

    public String getProfileURI() {
        return profileURI;
    }

    public void setProfileURI(String profileURI) {
        this.profileURI = profileURI;
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

    public String getIsHided() {
        return isHided;
    }

    public void setIsHided(String isHided) {
        this.isHided = isHided;
    }

    public String getTAG1_ID() {
        return TAG1_ID;
    }

    public void setTAG1_ID(String TAG1_ID) {
        this.TAG1_ID = TAG1_ID;
    }

    public String getTAG2_ID() {
        return TAG2_ID;
    }

    public void setTAG2_ID(String TAG2_ID) {
        this.TAG2_ID = TAG2_ID;
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
