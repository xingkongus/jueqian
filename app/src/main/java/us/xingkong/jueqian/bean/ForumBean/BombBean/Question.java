package us.xingkong.jueqian.bean.ForumBean.BombBean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;


/**
 * Created by Garfield on 1/11/17.
 */

public class Question extends BmobObject {

    private String mtitle;/*问题的标题*/
    private String mcontent;/*问题的内容*/
    private String TAG1_ID;/*第一个Tag的id*/
    private String TAG2_ID;/*第二个Tag的id*/
    private _User user;//提问题的用户
    private Integer focus;/*关注的数量*/
    private BmobRelation answers;
    private BmobRelation likepeople;
    private BmobRelation collectpeople;/*收藏的人的列表*/
    private BmobRelation focuspeople;/*关注的人的列表*/
    private Integer state;
    private Integer answer_count;//回答问题的数量

    public Integer getAnswer_count() {
        return answer_count;
    }

    public void setAnswer_count(Integer answer_count) {
        this.answer_count = answer_count;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public String getTAG1_ID() {
        return TAG1_ID;
    }

    public void setTAG1_ID(String TAG1_ID) {
        this.TAG1_ID = TAG1_ID;
    }

    public String getTAG2_ID() {
        return TAG2_ID;
    }

    public void setTAG2_ID(String TAG2_ID) {
        this.TAG2_ID = TAG2_ID;
    }

    public String getMcontent() {
        return mcontent;
    }

    public void setMcontent(String mcontent) {
        this.mcontent = mcontent;
    }

    public Integer getFocus() {
        return focus;
    }

    public void setFocus(Integer focus) {
        this.focus = focus;
    }

    public _User getUser() {
        return user;
    }

    public void setUser(_User user) {
        this.user = user;
    }

    public BmobRelation getAnswers() {
        return answers;
    }

    public void setAnswers(BmobRelation answers) {
        this.answers = answers;
    }

    public BmobRelation getLikepeople() {
        return likepeople;
    }

    public void setLikepeople(BmobRelation likepeople) {
        this.likepeople = likepeople;
    }

    public BmobRelation getFocuspeople() {
        return focuspeople;
    }

    public void setFocuspeople(BmobRelation focuspeople) {
        this.focuspeople = focuspeople;
    }

    public BmobRelation getCollectpeople() {
        return collectpeople;
    }

    public void setCollectpeople(BmobRelation collectpeople) {
        this.collectpeople = collectpeople;
    }

}
