package com.zzz.wuye.exchange;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zzz.wuye.R;
import com.zzz.wuye.exchange.model.stuff;
import com.zzz.wuye.sellhouse.model.houseCard;
import com.zzz.wuye.view.base.BaseActivity;

import cn.bmob.v3.datatype.BmobFile;

public class exchangeDetail extends BaseActivity implements View.OnClickListener {
    private TextView stufftitle;
    private TextView stuffcreate;
    private TextView stuffconnect;
    private TextView stuffpercent;
    private TextView stuffcheck;
    private TextView stufftele;
    private TextView stuffcontent;
    private TextView stuffprice;

    private ImageView image1;
    private ImageView image2;
    private ImageView image3;

    private ImageView titleback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_detail);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        stufftitle = findViewById(R.id.stuff_title);
        stuffcreate = findViewById(R.id.stuff_createdate);
        stuffconnect = findViewById(R.id.stuff_connectname);
        stuffpercent = findViewById(R.id.stuff_percent);
        stuffcheck = findViewById(R.id.stuff_check);
        stufftele = findViewById(R.id.stuff_telephone);
        stuffcontent = findViewById(R.id.repair_content);
        stuffprice = findViewById(R.id.stuff_price);


        image1 = findViewById(R.id.repair_image1);
        image2 = findViewById(R.id.repair_image2);
        image3 = findViewById(R.id.repair_image3);

        titleback = findViewById(R.id.title_back);
    }

    @Override
    protected void initView() {

        titleback.setOnClickListener(this);

        stuff repairCard1 = (stuff) getIntent().getSerializableExtra("stuff");
        stufftitle.setText(repairCard1.getTitle());
        stuffcreate.setText(repairCard1.getCreatedAt());
        stuffconnect.setText(repairCard1.getConnect());
        stuffpercent.setText(repairCard1.getPercent());
        stuffcheck.setText((repairCard1.isCheck())?"已交易":"未交易");
        stufftele.setText(repairCard1.getTelephone());
        stuffcontent.setText(repairCard1.getContent());
        stuffprice.setText(repairCard1.getPrice());

        // 获得图片地址url
        BmobFile image_1 = repairCard1.getImage().get(0);
        if(image_1 == null){
            image1.setImageResource(R.drawable.calendar);
        }else {
            String now_user_img_url = image_1.getFileUrl();
            ImageLoader.getInstance().displayImage((String) now_user_img_url, image1);
        }
        BmobFile image_2 = repairCard1.getImage().get(1);
        if(image_2 == null){
            image1.setImageResource(R.drawable.calendar);
        }else {
            String now_user_img_url = image_2.getFileUrl();
            ImageLoader.getInstance().displayImage((String) now_user_img_url, image2);
        }
        BmobFile image_3 = repairCard1.getImage().get(2);
        if(image_3 == null){
            image1.setImageResource(R.drawable.calendar);
        }else {
            String now_user_img_url = image_3.getFileUrl();
            ImageLoader.getInstance().displayImage((String) now_user_img_url, image3);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
        }
    }
}
