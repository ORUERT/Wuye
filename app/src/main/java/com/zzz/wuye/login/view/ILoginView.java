package com.zzz.wuye.login.view;

/**
 * Created by oruret on 2018/3/15.
 */

public interface ILoginView {
    public void onLoginResult(Boolean result, int code);
    public void onSetProgressBarVisibility(int visibility);
}
