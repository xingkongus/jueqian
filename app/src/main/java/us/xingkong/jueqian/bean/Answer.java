package us.xingkong.jueqian.bean;

import java.util.Date;

/**
 * Created by PERFECTLIN on 2017/1/9 0009.
 */

public class Answer {
    private String answerID; //回答的ID
    private String questionID; //回答的问题的ID
    private String senderID; //提问者ID
    private String content; //回答的内容
    private Date date; //回答的时间
    private int good_count; //这个回答获得的赞数量

    public String getAnswerID() {
        return answerID;
    }

    public void setAnswerID(String answerID) {
        this.answerID = answerID;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getGood_count() {
        return good_count;
    }

    public void setGood_count(int good_count) {
        this.good_count = good_count;
    }
}
