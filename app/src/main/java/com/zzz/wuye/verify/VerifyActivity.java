package com.zzz.wuye.verify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzz.wuye.R;
import com.zzz.wuye.location.model.locationModel;
import com.zzz.wuye.register.model.User;
import com.zzz.wuye.utils.DateUtils;
import com.zzz.wuye.utils.MyDialogHandler;
import com.zzz.wuye.verify.model.VerifyCode;
import com.zzz.wuye.view.base.BaseActivity;

import org.w3c.dom.Text;

import java.util.List;

import cn.aigestudio.datepicker.utils.DataUtils;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class VerifyActivity extends BaseActivity implements View.OnClickListener {
    private TextView welcome;
    private EditText verifycode;
    private Button submit;
    private ImageView person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        findViewById();
        initView();
    }
    @Override
    protected void initView() {
        submit.setOnClickListener(this);
//        uiFlusHandler = new MyDialogHandler(this, "正在...");
        User currentUser = BmobUser.getCurrentUser(User.class);
        welcome.setText(currentUser.getNickName()+"请输入验证码");
    }

    @Override
    protected void findViewById() {
        welcome = findViewById(R.id.verify_welcome);
        verifycode = findViewById(R.id.verify_code);
        submit = findViewById(R.id.verify_submit);
        person = findViewById(R.id.verify_image);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.verify_submit:
                String code = verifycode.getText().toString();
                if(TextUtils.isEmpty(code)){
                    DisplayToast("请输入确认码");
                }else {
                    BmobQuery<VerifyCode> query = new BmobQuery<VerifyCode>();
                    query.addWhereEqualTo("key",code);
                    query.findObjects(new FindListener<VerifyCode>() {
                        @Override
                        public void done(List<VerifyCode> object, BmobException e) {
                            if(e==null){
                                switch(object.get(0).getCheck()){
                                    case 1:
                                        //保存验证时间以及用户类型
                                        person.setImageResource(R.drawable.ic_person_black_24dp);
                                        User user = new User();
                                        user.setType(object.get(0).getCheck());
                                        user.setBilldate(DateUtils.getCurrentDatetime());
                                        user.update(BmobUser.getCurrentUser(User.class).getObjectId(), new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if(e == null){
                                                    DisplayToast("恭喜您验证成功");
                                                }
                                            }

                                        });
                                        //保存地址信息
                                        User p = new User();
                                        p.add("place",new locationModel(object.get(0).getMain(),object.get(0).getFloor(),object.get(0).getDoorNum(),user.getNickName(),user.getUsername(),false));   //添加单个Object
                                        p.update(BmobUser.getCurrentUser(User.class).getObjectId(),new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if(e==null){
                                                    Log.i("bmob","更新成功");
                                                }else{
                                                    Log.i("bmob","更新失败："+e.getMessage());
                                                }
                                            }
                                        });
                                        //废弃掉这个验证码
                                        VerifyCode code = new VerifyCode();
                                        code.setCheck(0);
                                        code.update(object.get(0).getObjectId(), new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                            }
                                        });
                                        break;
                                    case 2:
                                        break;
                                    case 0:
                                        break;
                                    default:
                                        DisplayToast("验证失败");
                                        break;
                                }
                            }else{
                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });

                }
                break;
        }
    }
}
