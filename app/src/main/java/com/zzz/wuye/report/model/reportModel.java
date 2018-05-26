package com.zzz.wuye.report.model;

import java.io.Serializable;
import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by oruret on 2018/4/10.
 */

public class reportModel extends BmobObject implements Serializable{
    String username;
    String telephone;
    String content;

    public reportModel(){

    }
    public reportModel(String username, String telephone, String content) {
        this.username = username;
        this.telephone = telephone;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
