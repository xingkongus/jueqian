package us.xingkong.jueqian.bean.ForumBean.BombBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by PERFECTLIN on 2017/5/4 0004.
 */

public class Follow extends BmobObject {
    private _User followUser;
    private _User followedUser;

    public _User getFollowUser() {
        return followUser;
    }

    public void setFollowUser(_User followUser) {
        this.followUser = followUser;
    }

    public _User getFollowedUser() {
        return followedUser;
    }

    public void setFollowedUser(_User followedUser) {
        this.followedUser = followedUser;
    }
}
