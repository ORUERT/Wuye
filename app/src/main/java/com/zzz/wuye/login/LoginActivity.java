package com.zzz.wuye.login;

import java.util.Map;

//import cn.bmob.v3.Bmob;
//import cn.bmob.v3.exception.BmobException;
//import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
//import okhttp3.Call;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
//import com.google.gson.Gson;
import com.zzz.wuye.R;
import com.zzz.wuye.location.placeActivity;
import com.zzz.wuye.login.view.ILoginView;
import com.zzz.wuye.register.model.User;
import com.zzz.wuye.utils.Constants;
import com.zzz.wuye.utils.MyDialogHandler;
//import com.zzz.wuye.utils.SharedPreferencesUtils;
import com.zzz.wuye.view.ConfigActivity;
import com.zzz.wuye.view.MainActivity;
import com.zzz.wuye.register.RegisterActivity;
import com.zzz.wuye.view.base.BaseActivity;
import com.rey.material.widget.CheckBox;
//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.callback.StringCallback;

public class LoginActivity extends BaseActivity implements ILoginView,OnClickListener {
    private EditText et_username;
    private EditText et_password;

//    private Button bt_login;
//    private Button bt_register;
    private Context mContext;

    private ActionProcessButton bt_login;

    private ActionProcessButton bt_register;

    private CheckBox cb_rember;

    private boolean rember_pw = false;

    private MyDialogHandler uiFlusHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;

        findViewById();
        initView();
    }

    private void login() {
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        //d.判断用户名密码是否为空，不为空请求服务器（省略，默认请求成功）
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(mContext, "不可留空", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i("helo",username+password+rember_pw);
        // 服务端验证
        checkUser();
//         openActivity(MainActivity.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_bt_login:
                login();
                break;
            case R.id.checkbox_rember:
                rember_pw = (!rember_pw);
                break;
            case R.id.login_bt_register:
//                openActivity(RegisterActivity.class);
                Log.i("hello","register start");
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
//                Log.i("hello","start");
                startActivity(intent);
//                openActivity(RegisterActivity.class);
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            openActivity(ConfigActivity.class);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void findViewById() {
        et_username = $(R.id.login_et_username);
        et_password = $(R.id.login_et_password);
        cb_rember = $(R.id.checkbox_rember);
        bt_login = (ActionProcessButton) findViewById(R.id.login_bt_login);

        bt_register = $(R.id.login_bt_register);
    }

    @Override
    protected void initView() {
        bt_login.setOnClickListener(this);
        bt_register.setOnClickListener(this);
        cb_rember.setOnClickListener(this);

        cb_rember.setChecked(rember_pw);
        bt_login.setMode(ActionProcessButton.Mode.PROGRESS);
        bt_login.setProgress(0);
//        echo();
        uiFlusHandler = new MyDialogHandler(mContext, "登录中...");
    }


    private void checkUser() {
        uiFlusHandler.sendEmptyMessage(SHOW_LOADING_DIALOG);

        User bu2 = new User();
        bu2.setUsername(String.valueOf(et_username.getText()));
        bu2.setPassword(String.valueOf(et_password.getText()));
        bu2.login(new SaveListener<User>() {

            @Override
            public void done(User bmobUser, BmobException e) {
                if(e==null){
                    DisplayToast("登录成功:");
                    //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                    //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                    uiFlusHandler.sendEmptyMessage(DISMISS_LOADING_DIALOG);
//                    openActivity(placeActivity.class);

                    openActivity(MainActivity.class);
                    finish();
                }else{
                    uiFlusHandler.sendEmptyMessage(DISMISS_LOADING_DIALOG);
                    DisplayToast("登录失败");
                    Log.e("e", String.valueOf(e));
                }
            }
        });
    }

    @Override
    public void onLoginResult(Boolean result, int code) {

    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {

    }

}
