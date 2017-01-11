package us.xingkong.jueqian.bean.ForumBean.RealmBean;

import java.sql.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Garfield on 1/11/17.
 */

public class Comment extends RealmObject {

    @PrimaryKey
    private String OBJ_ID;

    private String mcontent;/*评论正文*/
    private String SENDER_ID;/*发送者的id*/
    private String QUESTION_ID;/*问题的id*/
    private String ANSWER_ID;/*回答的id*/
    private Integer state;/*该评论的状态，-1为不显示，1为正常问题*/
    private Integer good_count;/*该问题收到的点赞数*/
    private Date last_update;/*作者最后更新时间*/
    private Date last_add;/*最后入库时间*/

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }

    public Date getLast_add() {
        return last_add;
    }

    public void setLast_add(Date last_add) {
        this.last_add = last_add;
    }

    public String getOBJ_ID() {
        return OBJ_ID;
    }

    public void setOBJ_ID(String OBJ_ID) {
        this.OBJ_ID = OBJ_ID;
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
}
