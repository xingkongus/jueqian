package us.xingkong.jueqian.bean.ForumBean.BombBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by PERFECTLIN on 2017/4/20 0020.
 */

public class NewMessage extends BmobObject {
    private _User sender;//发送新消息的用户
    private _User receiver;//接收新消息的用户
    private String content;//新消息内容
    private Integer TYPE;//新消息类型 1为回答 2为评论

    public _User getSender() {
        return sender;
    }

    public void setSender(_User sender) {
        this.sender = sender;
    }

    public _User getReceiver() {
        return receiver;
    }

    public void setReceiver(_User receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTYPE() {
        return TYPE;
    }

    public void setTYPE(Integer TYPE) {
        this.TYPE = TYPE;
    }
}
