package us.xingkong.jueqian.bean.ForumBean.BombBean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;


/**
 * Created by Garfield on 1/11/17.
 */

public class Answer extends BmobObject {

    private String mcontent; /*回答的正文*/
    private Integer state;/*问题的状态，-1为举报成功的问题，1为正常问*/
    private Integer ups;/*赞同数*/
    private _User user;/*回答者*/
    private Question question;/*所回答的问题*/
    private BmobRelation comments;/*回答的评论列表*/
    private BmobRelation likepeople;/*点赞的人的列表*/

    public Integer getUps() {
        return ups;
    }

    public void setUps(Integer ups) {
        this.ups = ups;
    }

    public _User getUser() {
        return user;
    }

    public void setUser(_User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public BmobRelation getComments() {
        return comments;
    }

    public void setComments(BmobRelation comments) {
        this.comments = comments;
    }

    public BmobRelation getLikepeople() {
        return likepeople;
    }

    public void setLikepeople(BmobRelation likepeople) {
        this.likepeople = likepeople;
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


}
