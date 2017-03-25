package us.xingkong.jueqian.bean.ForumBean.BombBean;

import cn.bmob.v3.BmobObject;

import cn.bmob.v3.datatype.BmobRelation;


/**
 * Created by Garfield on 1/11/17.
 */

public class Answer extends BmobObject {

    private String mcontent; /*回答的正文*/
    private String SENDER_ID;/*发送者的id*/
    private String QUESTION_ID;/*问题的id*/
    private Integer state;/*问题的状态，-1为举报成功的问题，1为正常问*/
    private Integer likes;/*点赞数*/

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getMcontent() {
        return mcontent;
    }

    public void setMcontent(String mcontent) {
        this.mcontent = mcontent;
    }

    public String getSENDER_ID() {
        return SENDER_ID;
    }

    public void setSENDER_ID(String SENDER_ID) {
        this.SENDER_ID = SENDER_ID;
    }

    public String getQUESTION_ID() {
        return QUESTION_ID;
    }

    public void setQUESTION_ID(String QUESTION_ID) {
        this.QUESTION_ID = QUESTION_ID;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
