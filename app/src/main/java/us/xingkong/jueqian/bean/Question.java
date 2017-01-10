package us.xingkong.jueqian.bean;

import java.util.Date;

/**
 * Created by PERFECTLIN on 2017/1/9 0009.
 */

public class Question {
    private String title; //问题
    private String discription; //问题描述
    private Date date; //问题发表时间
    private User sender; //提问者

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

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    private String tag; //分类标签
}
