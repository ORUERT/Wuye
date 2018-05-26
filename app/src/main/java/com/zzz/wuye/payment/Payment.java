package com.zzz.wuye.payment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.zzz.wuye.R;
import com.zzz.wuye.enterPage.adapter.OnRecyclerviewItemClickListener;
import com.zzz.wuye.payment.adapter.paymentAdapter;
import com.zzz.wuye.payment.model.bill;
import com.zzz.wuye.personPage.view.SpaceItemDecoration;
import com.zzz.wuye.register.model.User;
import com.zzz.wuye.utils.Constants;
import com.zzz.wuye.utils.DateUtils;
import com.zzz.wuye.utils.OrderInfoUtil2_0;
import com.zzz.wuye.view.base.BaseActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.zzz.wuye.utils.Constants.fetchUserInfo;
import static org.litepal.LitePalApplication.getContext;

public class Payment extends BaseActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private paymentAdapter adapter;
    private List<bill> dataList = new ArrayList<>();
    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "2016091500516639";

    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String PID = "2088102175733656";
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC6H/dj3d9uebZcljRy1IkW1ob1G6VVZa0rn95GSVUF3sCcp9nvLnAkHlXJbjkoIrUFV08dYELqTb/FEvKRF/HfONZaxRF1Ze8GapSlxBK68Jl7531wPE6E9doKaU3GsBNegonj/DyBMmA8uPonlbbqp754n3WV8BOHkjWuWffFZmY2uq6AMF50Bj4u3tIP/Xf1xJ8eMt20LWIH4eWtwM4t+mhfqlxpDpNhJQpYoIOgzQuaie3ENXjoUzpX9E2PAYCNWz7ZyBo0WTv9BP4UpCYmXbe/+tvMOqghJQ5aNSD6upZ+NxR8+5+FHSZa7sSboOgFSc75l1YksC4PYVk09oEBAgMBAAECggEAD2vSnY2mk4q2F1nCorkFWsUPjYNq4XZ5MKIgUoaE6uWX4+LsZardU8DHrtbZGYA2q2ief3qYUL18ZCG7w1XOjoYxDHhDiAyYD1oJUuq5+o8wyFlXxVLZ1p5gIHi1Hv87u5rpPeCKStIXszBIWbtH2o1ndKsRnQi1NYhhtj5yDwLWv0aKQ0kfWKQO/OQRoIbja1jdEbCTKYCVd8wHcoxXApUpFN5hR0jxZfl2VJJXRAXFiaPSAgnLnilJZMINJZpNYBagQMgWT9vor7Lp1dPmhGfnaAJqeBObXjVVqkN9h+npP6797N96UgG6I8MHCxEcK/NZlVHjm5seyoCA0oQKgQKBgQDmlR/ipS4cRc7FAju2Ufj/CAGGmsYeMtMkUzM4reFdZZNJBDBIzy7S6r3MediCJryKHkC8k/XXiGb9LV4EDzD0Y4iiDHUgLzFegKJJNkgsyvauyM1Y51yyKe4gwvvYnKSPr3KStwXxis7a0AddTdJkxsihiq8xmYAC8d5uXd6EmQKBgQDOpEdGuZCDiQwFz84UpHC4kGaToZ1cr0jAYAhh0odkw64jsX7g42kZ/uVjfaFChTx3VG5cANRX7I/y9Q5S8dahXESwQQDMMn/QHXQT+SZ+q4erJm0KdhJRh+sHokut2ksm4r72UsOmLQg64I80GXfNBiQiXp6NUyUeHvWDQru4qQKBgCufxQONOrcQofj13jQ1OAWQDzHXMGpgNUAnGYa8pumToRTsXPI5eGNhE/2Og97D02HSLW9AEv/vB4UYwzPDaACkoCIAd1xacV2uuoVdZrRKxfb1eJw8UXZHpoy+NwWZRS0GBYgqZk53c4FEYFBPu+FjFmwxn8mjf+uOtQVubv3pAoGBAJeXsKpTy46TZXyF/Drsf70GQhofv9+Uv5xRVxTca3Mikoeg2OGGMz5c3k9khH7WW4t3JxbbjOuLofasi/uRatEpbUKYDUu/6D/2u4dDilf24ipOoEPCZqHlQihxiW9a6zL1uP3fraQk+RqoTqX74QVJcgy8uTgYPOyh7Z6DwE8xAoGBANRLC1V6V7DSGnxKQQOatVNYbt1cK/DtwzJvFbbz778LxAMPzQYa5iUBzXcZ0knzdiAURJLPCQaJXOL9txJH2n1+3t/VyMr+Sig/uSAa9GULQNsV27ksoMLMszVpae+eW1X2ZNnMr4GrU1bzILlskN7ynFWWCsQimZ2qGzfaJMiU";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    private ImageView titleback;

    private OnRecyclerviewItemClickListener onRecyclerviewItemClickListener2 = new OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            Log.i("click","click1");
            switch (v.getId()){
                case R.id.payment_button2:
                    Log.i("click","click2");
                    boolean rsa2 = (RSA2_PRIVATE.length() > 0);
                    Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2, String.valueOf(dataList.get(position).getNumber()),"物业费");
                    String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
                    String privateKey =RSA2_PRIVATE ;
                    String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
                    final String orderInfo = orderParam + "&" + sign;
                    Runnable payRunnable = new Runnable() {
                        @Override
                        public void run() {
                            PayTask alipay = new PayTask(Payment.this);
                            Map<String, String> result = alipay.payV2(orderInfo, true);
                            Log.i("msp", result.toString());

                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    };

                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        User newUser = new User();
                        newUser.setBilldate(DateUtils.getCurrentDatetime());
                        BmobUser bmobUser = BmobUser.getCurrentUser(User.class);
                        newUser.update(bmobUser.getObjectId(),new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    DisplayToast("支付成功");
                                }else{
                                    DisplayToast("支付失败请联系管理员");
                                }
                            }
                        });
                        Toast.makeText(Payment.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(Payment.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);


        findViewById();
        initData();
        initView();
    }

    @Override
    protected void findViewById() {
        recyclerView = (RecyclerView) findViewById(R.id.payment_table);
        titleback = findViewById(R.id.title_back);
    }

    public void initView(){
        titleback.setOnClickListener(this);

        adapter = new paymentAdapter(dataList,onRecyclerviewItemClickListener2);
        fetchUserInfo();
        final GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 1);
//        layoutManager2.setAutoMeasureEnabled(true);
        recyclerView.addItemDecoration(new SpaceItemDecoration(60,false,false,true,false));
        recyclerView.setLayoutManager(layoutManager2);

        recyclerView.setAdapter(adapter);
    }

    private void refresh(){
        User user = BmobUser.getCurrentUser(User.class);
//        dataList.addAll()
    }

    public void initData(){


        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("objectId", BmobUser.getCurrentUser(User.class).getObjectId());
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object,BmobException e) {
                if(e==null){

                    User currentUser = object.get(0);
                    if(currentUser.getType() == 1){
                        String billDate = BmobUser.getCurrentUser(User.class).getBilldate();
                        String currentDate = DateUtils.getCurrentDatetime();
                        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        Date dt1 = null;
                        try {
                            dt1 = sdf.parse(billDate);
                            Date dt2 = sdf.parse(currentDate);
                            int mouth = (dt2.getYear() - dt1.getYear())*12 + dt2.getMonth()-dt1.getMonth();
                            Log.i("bill", String.valueOf(mouth)+billDate+currentDate);
                            if(mouth > 0){
                                bill bill = new bill(currentUser.getNickName(), currentUser.getObjectId(),mouth*10,false, currentUser.getPlace().get(0).getMainlocal(),dt1);
                                dataList.add(0,bill);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }else if(currentUser.getType() == 2){
                        DisplayToast("您登陆的是管理员账号");
                    }else {
                        DisplayToast("请验证后再使用该功能");
                    }


                }else{
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
        }
    }
}
