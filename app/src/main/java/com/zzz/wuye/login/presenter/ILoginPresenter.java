package com.zzz.wuye.login.presenter;

/**
 * Created by oruret on 2018/3/15.
 */

public interface ILoginPresenter {
    void doLogin(String name, String passwd);
    void setProgressBarVisiblity(int visiblity);
}
