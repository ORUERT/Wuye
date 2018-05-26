package com.zzz.wuye.register.model;
//
//import cn.bmob.v3.BmobObject;
import com.zzz.wuye.location.model.locationModel;
import com.zzz.wuye.news.model._Article;
import com.zzz.wuye.payment.model.bill;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by oruret on 2018/4/3.
 */

public class User extends BmobUser{

    String sex;
    String nickName;
    String billdate;
    List<locationModel> place;
//    List<bill> bill;
    List<String> favor;
//    String verifycode;
    BmobFile image = null;
    int type;


    public User(){

    }

    public User(String sex, String nickName, BmobFile image, int type) {
        this.sex = sex;
        this.nickName = nickName;
        this.image = image;
        this.type = type;
    }

    public String getBilldate() {
        return billdate;
    }

    public void setBilldate(String billdate) {
        this.billdate = billdate;
    }


    public List<String> getFavor() {
        return favor;
    }

    public void setFavor(List<String> favor) {
        this.favor = favor;
    }
//
//    public List<com.zzz.wuye.payment.model.bill> getBill() {
//        return bill;
//    }
//
//    public void setBill(List<com.zzz.wuye.payment.model.bill> bill) {
//        this.bill = bill;
//    }

    public List<locationModel> getPlace() {
        return place;
    }

    public void setPlace(List<locationModel> place) {
        this.place = place;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
