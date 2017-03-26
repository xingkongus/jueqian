package us.xingkong.jueqian.bean.ForumBean.BombBean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;


/**
 * Created by Garfield on 1/11/17.
 */

public class Comment extends BmobObject {
    private String mcontent;/*评论正文*/
    private String SENDER_ID;/*发送者的id*/
    private String QUESTION_ID;/*问题的id*/
    private String ANSWER_ID;/*回答的id*/
    private Integer state;/*该评论的状态，-1为不显示，1为正常问题*/
    private Integer good_count;/*该问题收到的点赞数*/
    private BmobPointer comments;/*该评论的评论*/

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

    public String getANSWER_ID() {
        return ANSWER_ID;
    }

    public void setANSWER_ID(String ANSWER_ID) {
        this.ANSWER_ID = ANSWER_ID;
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

    public BmobPointer getComments() {
        return comments;
    }

    public void setComments(BmobPointer comments) {
        this.comments = comments;
    }
}
