package us.xingkong.jueqian.bean;

import java.util.List;

/**
 * Created by PERFECTLIN on 2017/1/9 0009.
 */

public class User {
    private String username; //用户名
    private String password; //密码
    private String icon;  //头像URL
    private String selfIntrodution; //自我介绍
    private Boolean isTeacher; //是否是教师
    private List<User> followers; //粉丝
    private List<User> following; //关注的人
    private List<Question> myQuestions; //我提出的问题
    private List<Answer> myAnswers; //我的回答
    private List<Question> recentLook; //最近浏览的问题
    private List<Message> myMessage; //我的消息

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSelfIntrodution() {
        return selfIntrodution;
    }

    public void setSelfIntrodution(String selfIntrodution) {
        this.selfIntrodution = selfIntrodution;
    }

    public Boolean getTeacher() {
        return isTeacher;
    }

    public void setTeacher(Boolean teacher) {
        isTeacher = teacher;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public List<Question> getMyQuestions() {
        return myQuestions;
    }

    public void setMyQuestions(List<Question> myQuestions) {
        this.myQuestions = myQuestions;
    }

    public List<Answer> getMyAnswers() {
        return myAnswers;
    }

    public void setMyAnswers(List<Answer> myAnswers) {
        this.myAnswers = myAnswers;
    }

    public List<Question> getRecentLook() {
        return recentLook;
    }

    public void setRecentLook(List<Question> recentLook) {
        this.recentLook = recentLook;
    }

    public List<Message> getMyMessage() {
        return myMessage;
    }

    public void setMyMessage(List<Message> myMessage) {
        this.myMessage = myMessage;
    }
}
