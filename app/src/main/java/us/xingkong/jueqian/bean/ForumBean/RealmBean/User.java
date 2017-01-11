package us.xingkong.jueqian.bean.ForumBean.RealmBean;

import cn.bmob.v3.datatype.BmobFile;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Garfield on 1/11/17.
 */

public class User extends RealmObject {



    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }



    public Boolean getSelfIntro() {
        return selfIntro;
    }

    public void setSelfIntro(Boolean selfIntro) {
        this.selfIntro = selfIntro;
    }

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

    public String getACCOUNT() {
        return ACCOUNT;
    }

    public void setACCOUNT(String ACCOUNT) {
        this.ACCOUNT = ACCOUNT;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @PrimaryKey
    private String OBJ_ID;//用户主键

    private String ACCOUNT;//账号
    private Boolean gender;//性别
    private String profile;//头像
    private Boolean selfIntro;//自我介绍
    private Integer state;//用户状态
}
