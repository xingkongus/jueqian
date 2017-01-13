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


}
