package com.zzz.wuye.payment.model;

import java.util.Date;

/**
 * Created by oruret on 2018/3/29.
 */

public class bill {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    String name;
    String account;

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    int number;
    boolean flag;
    String locate;

    public bill(String name, String account, int number, boolean flag, String locate, Date date) {
        this.name = name;
        this.account = account;
        this.number = number;
        this.flag = flag;
        this.locate = locate;
        this.date = date;
    }

    Date date;
}

