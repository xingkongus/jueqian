package us.xingkong.jueqian.bean;

import java.util.Date;

/**
 * Created by PERFECTLIN on 2017/1/9 0009.
 */

public class Answer {
    private Question question; //所回答的问题
    private String content; //回答的内容
    private Date date; //回答的时间
    private int good_count; //这个回答获得的赞数量

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
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

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    private Answer answer; //这个评论的评论
}
