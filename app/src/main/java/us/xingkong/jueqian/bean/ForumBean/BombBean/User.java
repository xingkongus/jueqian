package us.xingkong.jueqian.bean.ForumBean.BombBean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Garfield on 1/11/17.
 */

public class User extends BmobObject {
    public String getAccount() {
        return ACCOUNT;
    }

    public void setAccount(String ACCOUNT) {
        this.ACCOUNT = ACCOUNT;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public BmobFile getProfile() {
        return profile;
    }

    public void setProfile(BmobFile profile) {
        this.profile = profile;
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

    private String ACCOUNT;//账号
    private Boolean gender;//性别
    private BmobFile profile;//头像
    private Boolean selfIntro;//自我介绍
    private Integer state;//用户状态
}
