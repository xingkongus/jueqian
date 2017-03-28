package us.xingkong.jueqian.bean.ForumBean.BombBean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;
import us.xingkong.jueqian.bean.LoginRegistBean.Userinfo;


/**
 * Created by Garfield on 1/11/17.
 */

public class Question extends BmobObject {

    private String mtitle;/*问题的标题*/
    private String mcontent;/*问题的内容*/
    private Integer state;/*问题的状态，-1既是封掉的问题，1为正常问题*/
    private String TAG1_ID;/*第一个Tag的id*/
    private String TAG2_ID;/*第二个Tag的id*/
    private Integer good_count;/*点赞的数量*/
    private Integer answer_count;/*回答的数量*/
    private Userinfo userinfo;//提问题的用户
    private BmobRelation star;//收藏问题的用户

    public BmobRelation getStar() {
        return star;
    }

    public void setStar(BmobRelation star) {
        this.star = star;
    }

    public Userinfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(Userinfo userinfo) {
        this.userinfo = userinfo;
    }

    public Integer getAnswer_count() {
        return answer_count;
    }

    public void setAnswer_count(Integer answer_count) {
        this.answer_count = answer_count;
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
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

    public Integer getGood_count() {
        return good_count;
    }

    public void setGood_count(Integer good_count) {
        this.good_count = good_count;
    }

}
