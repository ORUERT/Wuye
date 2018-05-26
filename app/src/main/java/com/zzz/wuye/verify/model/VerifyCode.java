package com.zzz.wuye.verify.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by oruret on 2018/4/17.
 */

public class VerifyCode extends BmobObject{
    private String main;
    private String key;
    private String floor;
    private String doorNum;
    private int check;

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getDoorNum() {
        return doorNum;
    }

    public void setDoorNum(String doorNum) {
        this.doorNum = doorNum;
    }
}
