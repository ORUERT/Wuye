package com.zzz.wuye.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.gson.Gson;
//import com.google.gson.JsonSyntaxException;
import com.zzz.wuye.R;
//import com.zzz.wuye.entity.User;
import com.zzz.wuye.register.model.User;
import com.zzz.wuye.utils.Constants;
import com.zzz.wuye.utils.MyDialogHandler;
//import com.zzz.wuye.utils.SharedPreferencesUtils;
import com.zzz.wuye.view.base.BaseActivity;
//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.callback.StringCallback;

//import okhttp3.Call;

/**
 * Created by djzhao on 17/05/01.
 */

public class HomepageActivity extends BaseActivity implements View.OnClickListener {

    private String TITLE_NAME = "关于我";
    private View title_back;
    private TextView titleText;

    private Context mContext;
    private TextView username;
    private RadioGroup sexGroup;
    private RadioButton maleRadio;
    private RadioButton femaleRadio;

    private EditText check;
    private EditText location;

    private Button update;

    private String checkStr;
    private String locationStr;
    private String sex;

    private MyDialogHandler uiFlusHandler;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_homepage);
//        findViewById();
//        initView();
    }

    @Override
    protected void findViewById() {
        this.title_back = $(R.id.title_back);
        this.titleText = $(R.id.titleText);
        sexGroup = $(R.id.homepage_radio_sex);
        update = $(R.id.homepage_btn_update);
        location = $(R.id.homepage_et_location);
        username = $(R.id.homepage_tv_username);

        maleRadio = $(R.id.homepage_rd_male);
        femaleRadio = $(R.id.homepage_rd_female);
    }

    @Override
    protected void initView() {
        mContext = this;
        this.titleText.setText(TITLE_NAME);

        this.title_back.setOnClickListener(this);
        update.setOnClickListener(this);
        uiFlusHandler = new MyDialogHandler(mContext, "更新中...");
        echo();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back: {
                this.finish();
            }
            break;
            case R.id.homepage_btn_update:
                checkInfo();
                break;
        }
    }

    private void echo() {
//        username.setText(Constants.USER.getName());
////        height.setText(Constants.USER.getCheck() + "");
//        location.setText(Constants.USER.getLocation() + "");
//        if (Constants.USER.getSex().equals("女")) {
//            femaleRadio.setChecked(true);
//        } else {
//            maleRadio.setChecked(true);
//        }
    }

    private void checkInfo() {
        locationStr = location.getText().toString().trim();
//        weightStr = weight.getText().toString().trim();
        sex = "男";
        if (sexGroup.getCheckedRadioButtonId() == R.id.homepage_rd_female) {
            sex = "女";
        }

        if (TextUtils.isEmpty(locationStr) ) {
            DisplayToast("不可留空！");
            return;
        }

        update();

    }

    private void update() {
//        uiFlusHandler.sendEmptyMessage(SHOW_LOADING_DIALOG);
//        String url = Constants.BASE_URL + "User?method=update";
//        OkHttpUtils
//                .post()
//                .url(url)
//                .addParams("username", Constants.USER.getName())
//                .addParams("location",locationStr)
////                .addParams("weight", weightStr)
//                .addParams("sex", sex)
//                .id(1)
//                .build()
//                .execute(new MyStringCallback());
    }

//    public class MyStringCallback extends StringCallback {
//
//        @Override
//        public void onResponse(String response, int id) {
////            Gson gson = new Gson();
////            switch (id) {
////                case 1:
////                    uiFlusHandler.sendEmptyMessage(DISMISS_LOADING_DIALOG);
////                    User user = null;
////                    try {
////                        user = gson.fromJson(response, User.class);
////                    } catch (JsonSyntaxException e) {
////                        user = null;
////                    }
////                    if (user == null) {
////                        DisplayToast(response);
////                        return;
////                    } else {
////                        // 存储用户
//////                        Constants.USER.setLocation(user.getLocation());
////////                        Constants.USER.setWeight(user.getWeight());
//////                        Constants.USER.setSex(user.getSex());
//////                        user.setPassword(Constants.USER.getPassword());
//////                        user.setUserId(Constants.USER.getUserId());
////////                        boolean result = SharedPreferencesUtils.saveUserInfo(mContext, user);
//////                        if (result) {
//////                            Toast.makeText(mContext, "更新成功！", Toast.LENGTH_SHORT).show();
//////                        } else {
//////                            Toast.makeText(mContext, "用户信息存储失败", Toast.LENGTH_SHORT).show();
//////                        }
////                    }
////                    finish();
////                    break;
////                case 2:
////
////                    break;
////                default:
////                    DisplayToast("what?");
////                    break;
////            }
//        }
//
//        @Override
//        public void onError(Call arg0, Exception arg1, int arg2) {
//            DisplayToast("网络链接出错！");
//        }
//    }
}
