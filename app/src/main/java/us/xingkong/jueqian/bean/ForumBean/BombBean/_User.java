package us.xingkong.jueqian.bean.ForumBean.BombBean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Garfield on 1/11/17.
 */

public class _User extends BmobObject {


    private String username;
    private Boolean isBoy;//性别
    private BmobFile profile;//头像
    private String selfIntro;//自我介绍
    private Integer state;//用户状态
    private List<String> questionsID;//提问列表
    private List<String> collectionsID;//收藏列表

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getBoy() {
        return isBoy;
    }

    public void setBoy(Boolean boy) {
        isBoy = boy;
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

    public List<String> getQuestionsID() {
        return questionsID;
    }

    public void setQuestionsID(List<String> questionsID) {
        this.questionsID = questionsID;
    }

    public List<String> getCollectionsID() {
        return collectionsID;
    }

    public void setCollectionsID(List<String> collectionsID) {
        this.collectionsID = collectionsID;
    }
}
