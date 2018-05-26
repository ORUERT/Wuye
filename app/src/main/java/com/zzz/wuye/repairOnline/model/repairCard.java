package com.zzz.wuye.repairOnline.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by oruret on 2018/4/2.
 */

public class repairCard extends BmobObject implements Serializable{
    String projecttitle;
    String projectcontent;
    String connectname;
    String handlename;
    String phonenum;
    boolean check;
    List<BmobFile> image = null;

    public String getProjecttitle() {
        return projecttitle;
    }

    public void setProjecttitle(String projecttitle) {
        this.projecttitle = projecttitle;
    }

    public String getProjectcontent() {
        return projectcontent;
    }

    public void setProjectcontent(String projectcontent) {
        this.projectcontent = projectcontent;
    }

    public String getConnectname() {
        return connectname;
    }

    public void setConnectname(String connectname) {
        this.connectname = connectname;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
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

    public String getHandlename() {
        return handlename;
    }

    public void setHandlename(String handlename) {
        this.handlename = handlename;
    }
}
