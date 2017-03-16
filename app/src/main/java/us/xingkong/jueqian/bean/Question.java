package us.xingkong.jueqian.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by PERFECTLIN on 2017/1/9 0009.
 */

public class Question {
    private String questionID;
    private String senderID;
    private String title; //问题
    private String discription; //问题描述
    private Date date; //问题发表时间
    private boolean isHidedUser; //是否匿名
    private String tag; //分类标签

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isHidedUser() {
        return isHidedUser;
    }

    public void setHidedUser(boolean hidedUser) {
        isHidedUser = hidedUser;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
