package com.zzz.wuye.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayEbppBillAddRequest;
import com.alipay.api.response.AlipayEbppBillAddResponse;
import com.alipay.sdk.app.AuthTask;
import com.zzz.wuye.R;
import com.zzz.wuye.exchange.search;
import com.zzz.wuye.login.LoginActivity;
import com.zzz.wuye.payment.PayResult;
import com.zzz.wuye.register.model.User;
import com.zzz.wuye.utils.Constants;
//import com.zzz.wuye.utils.SharedPreferencesUtils;
import com.zzz.wuye.utils.OrderInfoUtil2_0;
import com.zzz.wuye.view.base.BaseActivity;

import java.util.List;
import java.util.Map;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.InstallationListener;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.zzz.wuye.utils.Constants.fetchUserInfo;

//import cn.bmob.v3.BmobUser;

public class SplashActivity extends BaseActivity implements View.OnClickListener {

    private Button goFitness;
    private ImageView bg;

    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "2016091500516639";

    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String PID = "2088102175733656";
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC6H/dj3d9uebZcljRy1IkW1ob1G6VVZa0rn95GSVUF3sCcp9nvLnAkHlXJbjkoIrUFV08dYELqTb/FEvKRF/HfONZaxRF1Ze8GapSlxBK68Jl7531wPE6E9doKaU3GsBNegonj/DyBMmA8uPonlbbqp754n3WV8BOHkjWuWffFZmY2uq6AMF50Bj4u3tIP/Xf1xJ8eMt20LWIH4eWtwM4t+mhfqlxpDpNhJQpYoIOgzQuaie3ENXjoUzpX9E2PAYCNWz7ZyBo0WTv9BP4UpCYmXbe/+tvMOqghJQ5aNSD6upZ+NxR8+5+FHSZa7sSboOgFSc75l1YksC4PYVk09oEBAgMBAAECggEAD2vSnY2mk4q2F1nCorkFWsUPjYNq4XZ5MKIgUoaE6uWX4+LsZardU8DHrtbZGYA2q2ief3qYUL18ZCG7w1XOjoYxDHhDiAyYD1oJUuq5+o8wyFlXxVLZ1p5gIHi1Hv87u5rpPeCKStIXszBIWbtH2o1ndKsRnQi1NYhhtj5yDwLWv0aKQ0kfWKQO/OQRoIbja1jdEbCTKYCVd8wHcoxXApUpFN5hR0jxZfl2VJJXRAXFiaPSAgnLnilJZMINJZpNYBagQMgWT9vor7Lp1dPmhGfnaAJqeBObXjVVqkN9h+npP6797N96UgG6I8MHCxEcK/NZlVHjm5seyoCA0oQKgQKBgQDmlR/ipS4cRc7FAju2Ufj/CAGGmsYeMtMkUzM4reFdZZNJBDBIzy7S6r3MediCJryKHkC8k/XXiGb9LV4EDzD0Y4iiDHUgLzFegKJJNkgsyvauyM1Y51yyKe4gwvvYnKSPr3KStwXxis7a0AddTdJkxsihiq8xmYAC8d5uXd6EmQKBgQDOpEdGuZCDiQwFz84UpHC4kGaToZ1cr0jAYAhh0odkw64jsX7g42kZ/uVjfaFChTx3VG5cANRX7I/y9Q5S8dahXESwQQDMMn/QHXQT+SZ+q4erJm0KdhJRh+sHokut2ksm4r72UsOmLQg64I80GXfNBiQiXp6NUyUeHvWDQru4qQKBgCufxQONOrcQofj13jQ1OAWQDzHXMGpgNUAnGYa8pumToRTsXPI5eGNhE/2Og97D02HSLW9AEv/vB4UYwzPDaACkoCIAd1xacV2uuoVdZrRKxfb1eJw8UXZHpoy+NwWZRS0GBYgqZk53c4FEYFBPu+FjFmwxn8mjf+uOtQVubv3pAoGBAJeXsKpTy46TZXyF/Drsf70GQhofv9+Uv5xRVxTca3Mikoeg2OGGMz5c3k9khH7WW4t3JxbbjOuLofasi/uRatEpbUKYDUu/6D/2u4dDilf24ipOoEPCZqHlQihxiW9a6zL1uP3fraQk+RqoTqX74QVJcgy8uTgYPOyh7Z6DwE8xAoGBANRLC1V6V7DSGnxKQQOatVNYbt1cK/DtwzJvFbbz778LxAMPzQYa5iUBzXcZ0knzdiAURJLPCQaJXOL9txJH2n1+3t/VyMr+Sig/uSAa9GULQNsV27ksoMLMszVpae+eW1X2ZNnMr4GrU1bzILlskN7ynFWWCsQimZ2qGzfaJMiU";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    private static final int SDK_AUTH_Cacel = -1;
    private static final int SDK_AUTH_Fail = 0;
    private static final int SDK_AUTH_Success = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);




//        openActivity(HomepageActivity.class);
        Bmob.initialize(this, Constants.bmobKey);
        BmobInstallationManager.getInstance().initialize(new InstallationListener<BmobInstallation>() {
            @Override
            public void done(BmobInstallation bmobInstallation, BmobException e) {
                if (e == null) {
                    Log.i("bmob",bmobInstallation.getObjectId() + "-" + bmobInstallation.getInstallationId());
                } else {
                    Log.e("bmob",e.getMessage());
                }
            }
        });

// 启动推送服务
        BmobPush.startWork(this);
        findViewById();
        try {
            initView();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void findViewById() {
        goFitness = $(R.id.btn_go_fitness);
        bg = $(R.id.splash_bg);
    }

    private void changeBackgroundImage() {
        switch ((int)(System.currentTimeMillis() % 8)) {
            case 0:
                bg.setImageResource(R.drawable.start1);
                break;
            case 1:
                bg.setImageResource(R.drawable.start2);
                break;
            case 2:
                bg.setImageResource(R.drawable.start3);
                break;
            case 3:
                bg.setImageResource(R.drawable.start4);
                break;
            case 4:
                bg.setImageResource(R.drawable.start5);
                break;
            case 5:
                bg.setImageResource(R.drawable.start6);
                break;
            case 6:
                bg.setImageResource(R.drawable.start7);
                break;
            case 7:
                bg.setImageResource(R.drawable.start8);
                break;
        }
    }

    @Override
    protected void initView() throws AlipayApiException {
//        goFitness.setOnClickListener(this);
//        changeBackgroundImage();
        fetchUserInfo();

        User userInfo = BmobUser.getCurrentUser(User.class);

        if(userInfo != null){
            // 允许用户使用应用
            fetchUserInfo();

        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("objectId", BmobUser.getCurrentUser(User.class).getObjectId());
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object,BmobException e) {
                if(e==null){
                    User newUser = object.get(0);
                    User bmobUser = BmobUser.getCurrentUser(User.class);
                    newUser.update(bmobUser.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Log.i("userinfo", String.valueOf(BmobUser.getCurrentUser(User.class).getPlace().size()));
                            }else{
                                Log.i("userinfo", String.valueOf(e));
                            }
                        }
                    });
                }else{
                }
            }
        });

//            openActivity(search.class);




//处理接口返回消息alipayResponse，具体返回参数可以参考API列表文档
            jumpToMain();
        }else{
//            openActivity(placeActivity.class);

            jumpToLogin();
            //缓存用户对象为空时， 可打开用户注册界面…
        }

    }

    private void jumpToLogin() {
        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                openActivity(LoginActivity.class);
//                openActivity(MainActivity.class);
                finish();
            }
        }.start();
    }

    private void jumpToMain() {
        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
//                List<locationModel> age = (List<locationModel>) BmobUser.getObjectByKey("place");
                fetchUserInfo();
//                User user = BmobUser.getCurrentUser(User.class);
//                Log.i("hello", String.valueOf(user.getPlace().get(0).getConnectname()));
                openActivity(MainActivity.class);
                finish();
            }
        }.start();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go_fitness:
                openActivity(LoginActivity.class);
                this.finish();
                break;
        }
    }
}
