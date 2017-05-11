package us.xingkong.jueqian.bean.ForumBean.BombBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Jying on 2017/5/7.
 */

public class Ding extends BmobObject{
    private _User ding;
    private Answer dinged;

    public _User getDing() {
        return ding;
    }

    public void setDing(_User ding) {
        this.ding = ding;
    }

    public Answer getDinged() {
        return dinged;
    }

    public void setDinged(Answer dinged) {
        this.dinged = dinged;
    }
}
