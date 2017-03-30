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
    private String selfsign;//个性签名
    private Boolean gender;//性别
    private BmobFile profile;//头像
    private Integer state;//用户状态
    private BmobRelation collections;//我的收藏列表
    private BmobRelation questions;//我的问题列表
    private BmobRelation answers;//我的回答列表
    private BmobRelation recentlooks;//最近浏览列表
    private BmobRelation comments;//回复我的列表

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSelfsign() {
        return selfsign;
    }

    public void setSelfsign(String selfsign) {
        this.selfsign = selfsign;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public BmobRelation getCollections() {
        return collections;
    }

    public void setCollections(BmobRelation collections) {
        this.collections = collections;
    }

    public BmobRelation getQuestions() {
        return questions;
    }

    public void setQuestions(BmobRelation questions) {
        this.questions = questions;
    }

    public BmobRelation getAnswers() {
        return answers;
    }

    public void setAnswers(BmobRelation answers) {
        this.answers = answers;
    }

    public BmobRelation getRecentlooks() {
        return recentlooks;
    }

    public void setRecentlooks(BmobRelation recentlooks) {
        this.recentlooks = recentlooks;
    }

    public BmobRelation getComments() {
        return comments;
    }

    public void setComments(BmobRelation comments) {
        this.comments = comments;
    }
}
