package us.xingkong.jueqian.bean;

import java.util.List;

/**
 * Created by PERFECTLIN on 2017/1/9 0009.
 */

public class User {
    private String username; //用户名
    private String password; //密码

    private String nickname; //昵称
    private String icon;  //头像URL
    private String selfIntrodution; //自我介绍
    private Boolean isTeacher; //是否是教师
    private String phone; //手机号码

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSelfIntrodution() {
        return selfIntrodution;
    }

    public void setSelfIntrodution(String selfIntrodution) {
        this.selfIntrodution = selfIntrodution;
    }

    public Boolean getTeacher() {
        return isTeacher;
    }

    public void setTeacher(Boolean teacher) {
        isTeacher = teacher;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
