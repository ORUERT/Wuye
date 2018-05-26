package com.zzz.wuye.repairOnline.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zzz.wuye.R;
import com.zzz.wuye.repairOnline.model.repairCard;
import com.zzz.wuye.view.base.BaseActivity;

import org.w3c.dom.Text;

import cn.bmob.v3.datatype.BmobFile;

public class repairDetail extends BaseActivity implements View.OnClickListener {

    private TextView repairtitle;
    private TextView repaircreate;
    private TextView repairconnect;
    private TextView repairhandle;
    private TextView repairtelenum;
    private TextView repaircheck;
    private TextView repairfinish;
    private TextView repaircontent;

    private ImageView image1;
    private ImageView image2;
    private ImageView image3;

    private ImageView titleback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_detail);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        repairtitle = findViewById(R.id.repair_title);
        repaircreate = findViewById(R.id.repair_createdate);
        repairconnect = findViewById(R.id.repair_connectname);
        repairhandle = findViewById(R.id.repair_handlename);
        repairtelenum = findViewById(R.id.repair_telephone);
        repaircheck = findViewById(R.id.repair_check);
        repairfinish = findViewById(R.id.repair_finishdate);
        repaircontent = findViewById(R.id.repair_content);

        image1 = findViewById(R.id.repair_image1);
        image2 = findViewById(R.id.repair_image2);
        image3 = findViewById(R.id.repair_image3);

        titleback = findViewById(R.id.title_back);
    }

    @Override
    protected void initView() {

        titleback.setOnClickListener(this);

        repairCard repairCard1 = (com.zzz.wuye.repairOnline.model.repairCard) getIntent().getSerializableExtra("repairCard");
        repairtitle.setText(repairCard1.getProjecttitle());
        repaircreate.setText(repairCard1.getCreatedAt());
        repairconnect.setText(repairCard1.getConnectname());
        repairhandle.setText(repairCard1.getHandlename());
        repaircheck.setText((repairCard1.isCheck())?"已检查":"未检查");
        repairtelenum.setText(repairCard1.getPhonenum());
        repairfinish.setText((repairCard1.getCreatedAt().equals(repairCard1.getUpdatedAt()))?"未完成":repairCard1.getUpdatedAt());
        repaircontent.setText(repairCard1.getProjectcontent());

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
