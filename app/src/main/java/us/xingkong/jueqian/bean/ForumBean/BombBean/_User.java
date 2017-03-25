package us.xingkong.jueqian.bean.ForumBean.BombBean;


import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Garfield on 1/11/17.
 */

public class _User extends BmobUser {
    private String nickname; //昵称
    private Boolean gender;//性别
    private BmobFile profile;//头像
    private String selfIntro;//自我介绍
    private Integer state;//用户状态

    public String getUsername() {
        return nickname;
    }

    public void setUsername(String username) {
        this.nickname = username;
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

    public String getSelfIntro() {
        return selfIntro;
    }

    public void setSelfIntro(String selfIntro) {
        this.selfIntro = selfIntro;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
