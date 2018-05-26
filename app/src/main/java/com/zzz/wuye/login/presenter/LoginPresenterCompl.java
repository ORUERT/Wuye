package com.zzz.wuye.login.presenter;

import android.os.Looper;

import com.zzz.wuye.login.model.IUser;
import com.zzz.wuye.login.model.UserModel;

import android.os.Handler;

/**
 * Created by oruret on 2018/3/15.
 */

public class LoginPresenterCompl implements ILoginPresenter {
    IUser user;
    Handler handler;
    public LoginPresenterCompl(){
        initUser();
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void doLogin(String name,String passwd){
        Boolean isLoginSuccess = true;
        final int code = user.checkUserValidity(name , passwd);
        if(code!=0)isLoginSuccess = false;
        final Boolean result = isLoginSuccess;
//        handler.postDelayed()
    }

    @Override
    public void setProgressBarVisiblity(int visiblity) {

    }

    private void initUser(){
        user = new UserModel("admin","admin");
    }
}
