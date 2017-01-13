package us.xingkong.jueqian.bean.ForumBean.BombBean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Garfield on 1/11/17.
 */

public class _User extends BmobObject {


    private String username;
    private Boolean isBoy;//性别
    private BmobFile profile;//头像
    private String selfIntro;//自我介绍
    private Integer state;//用户状态
    private List<String> questionsID;//提问列表
    private List<String> collectionsID;//收藏列表

}
