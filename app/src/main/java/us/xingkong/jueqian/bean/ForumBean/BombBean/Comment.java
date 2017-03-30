package us.xingkong.jueqian.bean.ForumBean.BombBean;

import cn.bmob.v3.BmobObject;
import us.xingkong.jueqian.bean.LoginRegistBean.Userinfo;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;



/**
 * Created by Garfield on 1/11/17.
 */

public class Comment extends BmobObject {
    private String mcontent;/*评论正文*/
    private Integer state;/*该评论的状态，-1为不显示，1为正常问题*/
    private Integer good_count;/*该问题收到的点赞数*/
    private Userinfo userinfo;//评论的用户
    private Question question;//评论的问题
    private Integer likes;/*该问题收到的点赞数*/
    private _User user;/*评论的用户*/
    private BmobPointer answer;/*评论所属的回答*/
    private BmobPointer comments;/*该评论的评论*/
    private BmobRelation likepeople;/*点赞的用户*/


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


    public BmobPointer getAnswer() {
        return answer;
    }

    public void setAnswer(BmobPointer answer) {
        this.answer = answer;
    }

    public BmobPointer getComments() {
        return comments;
    }

    public void setComments(BmobPointer comments) {
        this.comments = comments;
    }

    public BmobRelation getLikepeople() {
        return likepeople;
    }

    public void setLikepeople(BmobRelation likepeople) {
        this.likepeople = likepeople;
    }
}
