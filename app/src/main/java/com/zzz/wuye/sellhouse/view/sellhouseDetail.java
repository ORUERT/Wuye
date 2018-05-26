package com.zzz.wuye.sellhouse.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zzz.wuye.R;
import com.zzz.wuye.repairOnline.model.repairCard;
import com.zzz.wuye.sellhouse.model.houseCard;
import com.zzz.wuye.view.base.BaseActivity;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by oruret on 2018/5/5.
 */

public class sellhouseDetail extends BaseActivity implements View.OnClickListener {

    private TextView title;
    private TextView create;
    private TextView connect;
    private TextView price;
    private TextView telenum;
    private TextView main;
    private TextView floordoor;

    private TextView content;

    private ImageView image1;
    private ImageView image2;
    private ImageView image3;

    private ImageView titleback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellhouse_detail);
//        Log.i("Asdasd","asdasd");
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        title = findViewById(R.id.house_title);
        create = findViewById(R.id.house_createdate);
        connect = findViewById(R.id.house_connectname);
        price = findViewById(R.id.house_price);
        telenum = findViewById(R.id.house_telephone);
        main = findViewById(R.id.house_main);
        floordoor = findViewById(R.id.house_floordoor);

        content = findViewById(R.id.repair_content);

        image1 = findViewById(R.id.repair_image1);
        image2 = findViewById(R.id.repair_image2);
        image3 = findViewById(R.id.repair_image3);

        titleback = findViewById(R.id.title_back);
    }

    @Override
    protected void initView() {

        titleback.setOnClickListener(this);

        houseCard repairCard1 = (houseCard) getIntent().getSerializableExtra("houseCard");
        title.setText(repairCard1.getHousetitle());
        create.setText(repairCard1.getCreatedAt());
        connect.setText(repairCard1.getHouseconnect());
        telenum.setText(repairCard1.getHousetele());
        price.setText(repairCard1.getHouseprice());
        main.setText(repairCard1.getPlace().getMainlocal());
        floordoor.setText(repairCard1.getPlace().getFloor()+repairCard1.getPlace().getHousenum());
        content.setText(repairCard1.getHousecontent());

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
