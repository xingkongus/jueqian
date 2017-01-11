package us.xingkong.jueqian.bean;

/**
 * Created by PERFECTLIN on 2017/1/9 0009.
 */

public class Message {

    private User user; //发出消息的用户
    private Question question; //消息关联的问题
    private Answer answer; //消息的回答

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
