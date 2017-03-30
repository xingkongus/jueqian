package us.xingkong.jueqian.bean.LoginRegistBean;

import cn.bmob.v3.BmobUser;

/**
 * Created by lenovo on 2017/1/13.
 */

public class Userinfo extends BmobUser {
    String nick;
    String sex;
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
