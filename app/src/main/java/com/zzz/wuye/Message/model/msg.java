package com.zzz.wuye.Message.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by oruret on 2018/4/19.
 */

public class msg extends BmobObject{
    private String username;
    private String userid;
    private String content;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
