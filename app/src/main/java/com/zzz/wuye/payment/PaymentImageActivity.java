package com.zzz.wuye.payment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alipay.sdk.app.EnvUtils;
import com.zzz.wuye.R;
import com.zzz.wuye.view.base.BaseActivity;

public class PaymentImageActivity extends BaseActivity {
    private ImageView bigImage;
    @Override
    protected void findViewById() {
        bigImage = findViewById(R.id.imageView5);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_image);
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);

    }
}
