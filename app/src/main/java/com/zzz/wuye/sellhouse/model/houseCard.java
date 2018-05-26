package com.zzz.wuye.sellhouse.model;

import com.zzz.wuye.location.model.locationModel;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by oruret on 2018/5/4.
 */

public class houseCard extends BmobObject implements Serializable {
    private String housetitle;
    private String housecontent;
    private String houseprice;
    private String houseconnect;
    private String housetele;
    private locationModel place;
    private boolean check;
    private String uploadId;

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    private List<BmobFile> image;

    public String getHousetitle() {
        return housetitle;
    }

    public void setHousetitle(String housetitle) {
        this.housetitle = housetitle;
    }

    public String getHousecontent() {
        return housecontent;
    }

    public void setHousecontent(String housecontent) {
        this.housecontent = housecontent;
    }

    public String getHouseprice() {
        return houseprice;
    }

    public void setHouseprice(String houseprice) {
        this.houseprice = houseprice;
    }

    public String getHouseconnect() {
        return houseconnect;
    }

    public void setHouseconnect(String houseconnect) {
        this.houseconnect = houseconnect;
    }

    public String getHousetele() {
        return housetele;
    }

    public void setHousetele(String housetele) {
        this.housetele = housetele;
    }

    public locationModel getPlace() {
        return place;
    }

    public void setPlace(locationModel place) {
        this.place = place;
    }

    public List<BmobFile> getImage() {
        return image;
    }

    public void setImage(List<BmobFile> image) {
        this.image = image;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}

