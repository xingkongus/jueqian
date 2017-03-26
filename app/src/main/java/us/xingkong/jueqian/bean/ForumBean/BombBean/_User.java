package us.xingkong.jueqian.bean.ForumBean.BombBean;


import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Garfield on 1/11/17.
 */

public class _User extends BmobUser {
    private String nickname; //昵称
    private Boolean gender;//性别
    private BmobFile profile;//头像
    private String selfIntro;//自我介绍
    private Integer state;//用户状态
    private BmobPointer collections;//我的收藏列表
    private BmobPointer questions;//我的问题列表
    private BmobPointer answers;//我的回答列表
    private BmobPointer recentlooks;//最近浏览列表
    private BmobPointer comments;//回复我的列表

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public BmobPointer getCollections() {
        return collections;
    }

    public void setCollections(BmobPointer collections) {
        this.collections = collections;
    }

    public BmobPointer getQuestions() {
        return questions;
    }

    public void setQuestions(BmobPointer questions) {
        this.questions = questions;
    }

    public BmobPointer getAnswers() {
        return answers;
    }

    public void setAnswers(BmobPointer answers) {
        this.answers = answers;
    }

    public BmobPointer getRecentlooks() {
        return recentlooks;
    }

    public void setRecentlooks(BmobPointer recentlooks) {
        this.recentlooks = recentlooks;
    }

    public BmobPointer getComments() {
        return comments;
    }

    public void setComments(BmobPointer comments) {
        this.comments = comments;
    }
}
