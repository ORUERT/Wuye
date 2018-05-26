package com.zzz.wuye.report;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;


import com.alipay.api.AlipayApiException;
import com.zzz.wuye.R;
import com.zzz.wuye.register.model.User;
import com.zzz.wuye.report.model.reportModel;
import com.zzz.wuye.view.base.BaseActivity;

import butterknife.InjectView;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class addReportActivity extends BaseActivity implements View.OnClickListener {
    EditText content;
    EditText telephone;
    Button submit;
    ImageView titleback;
    @Override
    protected void findViewById() {
        content = findViewById(R.id.report_content);
        telephone = findViewById(R.id.report_telephone);
        submit = findViewById(R.id.place_add);
        titleback = findViewById(R.id.title_back);
    }

    @Override
    protected void initView() {
        submit.setOnClickListener(this);
        titleback.setOnClickListener(this);
        if(getIntent().getStringExtra("hello")!=null){
            reportModel temp = (reportModel)getIntent().getSerializableExtra("report");
            content.setText(temp.getContent());
            telephone.setText(temp.getTelephone());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);
        findViewById();
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.place_add:
                String content1 = content.getText().toString().trim();
                String username = BmobUser.getCurrentUser(User.class).getNickName();
                String telephone1 = telephone.getText().toString().trim();
//                if()
                for(int i = 0 ; i < 10 ; i ++) {
                    reportModel repair = new reportModel();
                    repair.setContent(content1);
                    repair.setTelephone(telephone1);
                    repair.setUsername(username);
                    repair.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Log.i("successful", "Bmob保修信息上传成功");
                            } else {
                                Log.i("error", "Bmob保修信息保存失败" + e);
                            }
                        }
                    });
                }
                break;
            case R.id.title_back:
                finish();
        }
    }
}
