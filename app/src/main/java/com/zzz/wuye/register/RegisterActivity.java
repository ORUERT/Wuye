package com.zzz.wuye.register;

//import cn.bmob.v3.exception.BmobException;
//import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
//import okhttp3.Call;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

//import com.google.gson.Gson;
//import com.google.gson.JsonSyntaxException;
import com.rey.material.widget.CheckBox;
import com.zzz.wuye.R;
import com.zzz.wuye.register.model.User;
import com.zzz.wuye.utils.AppManager;
import com.zzz.wuye.utils.MyDialogHandler;
//import com.zzz.wuye.utils.SharedPreferencesUtils;
import com.zzz.wuye.view.MainActivity;
import com.zzz.wuye.view.base.BaseActivity;
//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.callback.StringCallback;

public class RegisterActivity extends BaseActivity implements OnClickListener {
    private String TITLE_NAME = "注册";
    private View title_back;
    private TextView titleText;

    private Context mContext;
    private EditText et_phonenum;
    private EditText et_verifycode;
    private EditText et_nickname;
    private EditText et_password;
    private EditText et_repassword;
    private Button register_login;
    private Button verifybutton;
    private RadioGroup radio_sex;
    private EditText et_location;
    private EditText et_checkcode;
    private CheckBox checkboxmale;
    private CheckBox checkboxfemale;
    private ImageView et_location_button;
//    private Context mcontent;
    private MyDialogHandler uiFlusHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewById();
        initView();
    }

    @Override
    protected void initView() {
        mContext = this;
        this.title_back.setOnClickListener(this);
        this.titleText.setText(TITLE_NAME);
        this.register_login.setOnClickListener(this);
        this.verifybutton.setOnClickListener(this);


//        this.et_location_button.setOnClickListener(this);
        this.checkboxmale.setOnClickListener(this);
        this.checkboxfemale.setOnClickListener(this);

        uiFlusHandler = new MyDialogHandler(mContext, "正在注册...");

    }

    @Override
    protected void findViewById() {
        this.title_back = $(R.id.title_back);
        this.titleText = $(R.id.titleText);

        et_phonenum = findViewById(R.id.reg_et_phonenum);
        et_verifycode = findViewById(R.id.reg_et_messagecode);
        verifybutton = findViewById(R.id.pla_editbutton);

//        et_location = findViewById(R.id.reg_et_location);
//        et_location_button = findViewById(R.id.reg_rd_location_image);


        et_checkcode = findViewById(R.id.reg_et_check);
        et_nickname = $(R.id.reg_et_nickname);
        et_password = $(R.id.reg_et_password);
        et_repassword = $(R.id.reg_et_repassword);
        checkboxmale = findViewById(R.id.reg_rd_male);
        checkboxfemale = findViewById(R.id.reg_rd_female);
//        et_height = $(R.id.reg_et_height);

        this.radio_sex = (RadioGroup) findViewById(R.id.radio_sex);
        this.register_login = (Button) findViewById(R.id.reg_btn_register);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back: {
                this.finish();
            }
            break;
            case R.id.reg_btn_register:
                register();

                break;
//            case R.id.reg_rd_location_image:
//                Log.i("hello","location start");
//                Intent intent = new Intent(RegisterActivity.this,LocationtActivity.class);
//                startActivityForResult(intent,1);
//                break;
            case R.id.reg_rd_male:
                if(checkboxfemale.isChecked())checkboxfemale.setChecked(!(checkboxfemale.isChecked()));
                break;
            case R.id.reg_rd_female:
                if(checkboxmale.isChecked())checkboxmale.setChecked(!(checkboxmale.isChecked()));
                break;
            case R.id.pla_editbutton:
                if(!TextUtils.isEmpty(et_phonenum.getText())){
                    BmobSMS.requestSMSCode(et_phonenum.getText().toString().trim(),"注册", new QueryListener<Integer>() {
                        @Override
                        public void done(Integer smsId,BmobException ex) {
                            if(ex==null){//验证码发送成功
                                Log.i("smile", "短信id："+smsId);//用于查询本次短信发送详情
                            }else {
                                Log.i("error",ex.toString());
                            }
                        }
                    });
                    break;
                }else {
                    DisplayToast("请输入电话号码");
                }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    String location = data.getStringExtra("check");
                    et_location.setText(location);
                    Log.i("hello",location);
                }
        }
    }

    private void register() {
        String telephone = et_phonenum.getText().toString().trim();
        String messagecode = et_verifycode.getText().toString().trim();
        String nickname = et_nickname.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String repassword = et_repassword.getText().toString().trim();
        String checkcode = et_checkcode.getText().toString().trim();
//        String location = et_location.getText().toString().trim();
        String sex;
        if(checkboxmale.isChecked())sex = "男";
        else sex = "女";

        //d.判断用户名密码是否为空，不为空请求服务器（省略，默认请求成功）i
        if (TextUtils.isEmpty(telephone)||TextUtils.isEmpty(messagecode)||TextUtils.isEmpty(nickname) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword) || TextUtils.isEmpty(checkcode) || TextUtils.isEmpty(sex)) {
            DisplayToast("信息不能为空");
            return;
        }
        // 判断两次密码
        if (!password.equals(repassword)) {
            DisplayToast("两次密码输入不一致");
            return;
        }

        uiFlusHandler.sendEmptyMessage(SHOW_LOADING_DIALOG);

        User p2 = new User();
        p2.setNickName(nickname);
//        p2.setUsername(telephone);
        p2.setMobilePhoneNumber(telephone);
        p2.setPassword(password);
        p2.setImage(null);
        p2.setSex(sex);
        p2.setType(0);
//        BmobUser.signOrLoginByMobilePhone(telephone, messagecode, new LogInListener<User>() {
//
//            @Override
//            public void done(User user, BmobException e) {
//                if(user!=null){
//                    Log.i("smile","用户登陆成功");
//                }
//            }
//        })
        p2.signOrLogin(messagecode, new SaveListener<User>() {

            @Override
            public void done(User user,BmobException e) {
                if(e==null){
                    DisplayToast("注册成功:" + user.getUsername());
                    uiFlusHandler.sendEmptyMessage(DISMISS_LOADING_DIALOG);
                    openActivity(MainActivity.class);
                    AppManager.getInstance().killAllActivity();
//                    Log.i("smile", ""+user.getUsername()+"-"+user.getAge()+"-"+user.getObjectId());
                }else{
                    DisplayToast("注册失败:" + e.getMessage());
                }

            }

        });

    }
}
