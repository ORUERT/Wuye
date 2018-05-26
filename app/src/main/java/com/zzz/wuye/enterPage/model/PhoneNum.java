package com.zzz.wuye.enterPage.model;

import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by oruret on 2018/4/1.
 */

public class PhoneNum extends BmobObject {
    String userName;
    String phoneNum;

    public PhoneNum(String userName, String phoneNum) {
        this.userName = userName;
        this.phoneNum = phoneNum;
    }
    public PhoneNum(){

    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
