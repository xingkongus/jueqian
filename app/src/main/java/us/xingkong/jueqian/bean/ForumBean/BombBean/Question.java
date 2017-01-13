package us.xingkong.jueqian.bean.ForumBean.BombBean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Garfield on 1/11/17.
 */

public class Question extends BmobObject {

    private String mtitle;/*问题的标题*/
    private boolean isHided;/*是否为匿名问题*/
    private String mcontent;/*问题的内容*/
    private _User SENDER;/*发送者的ID*/
    private Integer state;/*问题的状态，-1既是封掉的问题，1为正常问题*/
    private TagBean TAG1;/*第一个Tag的id*/
    private TagBean TAG2;/*第二个Tag的id*/
    private BmobRelation like;/*点赞的数量*/

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public boolean isHided() {
        return isHided;
    }

    public void setHided(boolean hided) {
        isHided = hided;
    }

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public TagBean getTAG1() {
        return TAG1;
    }

    public void setTAG1(TagBean TAG1) {
        this.TAG1 = TAG1;
    }

    public TagBean getTAG2() {
        return TAG2;
    }

    public void setTAG2(TagBean TAG2) {
        this.TAG2 = TAG2;
    }

    public BmobRelation getLike() {
        return like;
    }

    public void setLike(BmobRelation like) {
        this.like = like;
    }
}
