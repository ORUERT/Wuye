package com.zzz.wuye.location.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by oruret on 2018/4/11.
 */

public class locationModel implements Serializable{
    private String mainlocal;
    private String floor;
    private String housenum;
    private String connectname;
    private String telephone;
    private boolean autolocation;

    public locationModel(String mainlocal, String floor, String housenum, String connectname, String telephone, boolean autolocation) {
        this.mainlocal = mainlocal;
        this.floor = floor;
        this.housenum = housenum;
        this.connectname = connectname;
        this.telephone = telephone;
        this.autolocation = autolocation;
    }

    public String getMainlocal() {
        return mainlocal;
    }

    public void setMainlocal(String mainlocal) {
        this.mainlocal = mainlocal;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getHousenum() {
        return housenum;
    }

    public void setHousenum(String housenum) {
        this.housenum = housenum;
    }

    public String getConnectname() {
        return connectname;
    }

    public void setConnectname(String connectname) {
        this.connectname = connectname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isAutolocation() {
        return autolocation;
    }

    public void setAutolocation(boolean autolocation) {
        this.autolocation = autolocation;
    }
}
