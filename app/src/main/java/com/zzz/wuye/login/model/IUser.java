package com.zzz.wuye.login.model;

/**
 * Created by oruret on 2018/3/15.
 */

public interface IUser {
    String getName();

    String getPasswd();

    int checkUserValidity(String name, String passwd);
}
