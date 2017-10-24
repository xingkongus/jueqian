package us.xingkong.jueqian.bean.ForumBean.BombBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by PERFECTLIN on 2017/5/1 0001.
 */

public class NewMessage extends BmobObject {
    private _User sender;
    private _User receiver;
    private Comment messComment;
    private Answer messAnswer;
    private Integer TYPE;
    private Integer isRead;
    private Question question;
    private Answer answer;

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public _User getSender() {
        return sender;
    }

    public void setSender(_User sender) {
        this.sender = sender;
    }

    public _User getReceiver() {
        return receiver;
    }

    public void setReceiver(_User receiver) {
        this.receiver = receiver;
    }

    public Comment getMessComment() {
        return messComment;
    }

    public void setMessComment(Comment messComment) {
        this.messComment = messComment;
    }

    public Answer getMessAnswer() {
        return messAnswer;
    }

    public void setMessAnswer(Answer messAnswer) {
        this.messAnswer = messAnswer;
    }

    public Integer getTYPE() {
        return TYPE;
    }

    public void setTYPE(Integer TYPE) {
        this.TYPE = TYPE;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }


}
