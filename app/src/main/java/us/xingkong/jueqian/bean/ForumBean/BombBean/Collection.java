package us.xingkong.jueqian.bean.ForumBean.BombBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by PERFECTLIN on 2017/4/28 0028.
 */

public class Collection extends BmobObject {
    private _User user;
    private Question question;

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
}
