package us.xingkong.jueqian.bean.ForumBean.BombBean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Garfield on 1/11/17.
 */

public class Comment extends BmobObject {
    private String mcontent;/*评论正文*/
    private _User SENDER;/*发送者的id*/
    private Question QUESTION;/*问题的id*/
    private Answer ANSWER;
    private _User ANSWERER;/*回答的id*/
    private Integer state;/*该评论的状态，-1为不显示，1为正常问题*/
    private BmobRelation like;/*该问题收到的点赞数*/

    public String getMcontent() {
        return mcontent;
    }

    public void setMcontent(String mcontent) {
        this.mcontent = mcontent;
    }

    public _User getSENDER() {
        return SENDER;
    }

    public void setSENDER(_User SENDER) {
        this.SENDER = SENDER;
    }

    public Question getQUESTION() {
        return QUESTION;
    }

    public void setQUESTION(Question QUESTION) {
        this.QUESTION = QUESTION;
    }

    public Answer getANSWER() {
        return ANSWER;
    }

    public void setANSWER(Answer ANSWER) {
        this.ANSWER = ANSWER;
    }

    public _User getANSWERER() {
        return ANSWERER;
    }

    public void setANSWERER(_User ANSWERER) {
        this.ANSWERER = ANSWERER;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public BmobRelation getLike() {
        return like;
    }

    public void setLike(BmobRelation like) {
        this.like = like;
    }
}
