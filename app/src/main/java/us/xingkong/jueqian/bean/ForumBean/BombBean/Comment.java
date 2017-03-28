package us.xingkong.jueqian.bean.ForumBean.BombBean;

import cn.bmob.v3.BmobObject;
import us.xingkong.jueqian.bean.LoginRegistBean.Userinfo;


/**
 * Created by Garfield on 1/11/17.
 */

public class Comment extends BmobObject {
    private String mcontent;/*评论正文*/
    private Integer state;/*该评论的状态，-1为不显示，1为正常问题*/
    private Integer good_count;/*该问题收到的点赞数*/
    private Userinfo userinfo;//评论的用户
    private Question question;//评论的问题

    public Userinfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(Userinfo userinfo) {
        this.userinfo = userinfo;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getMcontent() {
        return mcontent;
    }

    public void setMcontent(String mcontent) {
        this.mcontent = mcontent;
    }


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getGood_count() {
        return good_count;
    }

    public void setGood_count(Integer good_count) {
        this.good_count = good_count;
    }

}
