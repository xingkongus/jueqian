package us.xingkong.jueqian.bean.ForumBean.BombBean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Garfield on 1/11/17.
 */

public class Answer extends BmobObject {

    private String mcontent; /*回答的正文*/
    private _User SENDER;/*发送者的id*/
    private Question QUESTION;/*问题的id*/
    private Integer state;/*问题的状态，-1为举报成功的问题，1为正常问*/
    private BmobRelation like;/*点赞数*/

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
