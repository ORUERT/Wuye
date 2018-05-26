package com.zzz.wuye.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by djzhao on 17/05/02.
 */

public class Comment extends BmobObject {

    private String username;

    private String userid;

    private String comment;



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
