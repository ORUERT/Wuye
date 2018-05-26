package com.zzz.wuye.location.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rey.material.widget.CheckBox;
import com.rey.material.widget.RadioButton;
import com.zzz.wuye.R;
import com.zzz.wuye.enterPage.view.normalListActivity;
import com.zzz.wuye.location.LocationtActivity;
import com.zzz.wuye.location.model.locationModel;
import com.zzz.wuye.register.model.User;
import com.zzz.wuye.view.base.BaseActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static com.zzz.wuye.utils.Constants.fetchUserInfo;

public class addLocationActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private TextView maintext;
    private View locationmain;
    private View locationfloor;
    private View locationdoor;
    private TextView floortext;
    private TextView doortext;
    private TextView connectname;
    private TextView telephone;
    private ImageView titleback;
    private CheckBox defaultbutton;
    private Button addbutton;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    private static final int PERMISSON_REQUESTCODE = 0;


    public ArrayList<String> floor = new ArrayList<>(Arrays.asList("第一层","第二层","第三层"));
    public ArrayList<String> doornum = new ArrayList<>(Arrays.asList("001","002","003"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        title = findViewById(R.id.titleText);
        locationmain = findViewById(R.id.addlocation_main);

        floortext = findViewById(R.id.addlocation_floortext);
        doortext = findViewById(R.id.addlocation_doornumtext);

        maintext = findViewById(R.id.addlocation_maintext);
        locationfloor = findViewById(R.id.addlocation_floor);
        locationdoor = findViewById(R.id.addlocation_doornum);
        connectname = findViewById(R.id.addlocation_connectname);
        telephone = findViewById(R.id.addlocation_telephone);
        defaultbutton = findViewById(R.id.addlocation_default);
        addbutton = findViewById(R.id.place_add3);
        titleback = findViewById(R.id.title_back);
    }

    public void initView(){
        title.setText("添加地址");
        addbutton.setOnClickListener(this);
        locationmain.setOnClickListener(this);
        locationfloor.setOnClickListener(this);
        locationdoor.setOnClickListener(this);
        titleback.setOnClickListener(this);
    }

    /**
     * 检查权限
     *
     * @param
     * @since 2.5.0
     */
    private void checkPermissions(String... permissions) {
        //获取权限列表
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            //list.toarray将集合转化为数组
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }else {
            Intent intent = new Intent(addLocationActivity.this,LocationtActivity.class);
            startActivityForResult(intent,1);
        }
    }


    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        //for (循环变量类型 循环变量名称 : 要被遍历的对象)
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {      //没有授权
                showMissingPermissionDialog();              //显示提示信息
//                isNeedCheck = false;
            }
        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("title");
        builder.setMessage("msg");

        // 拒绝, 退出应用
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
                    }
                });

        builder.setPositiveButton("设置",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        startAppSettings();
                        Intent intent = new Intent(addLocationActivity.this,LocationtActivity.class);
                        startActivityForResult(intent,1);
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addlocation_main:
                checkPermissions(PERMISSIONS_STORAGE);
                break;
            case R.id.addlocation_floor:
                Intent intent2 = new Intent(this,normalListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("num",2);
                bundle.putStringArrayList("floor",floor);
                //把bundle对象封装至intent对象中
                intent2.putExtras(bundle);
                startActivityForResult(intent2,2);
                break;
            case R.id.addlocation_doornum:
                Intent intent3 = new Intent(this,normalListActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putInt("num",3);
                bundle2.putStringArrayList("door",doornum);
                //把bundle对象封装至intent对象中
                intent3.putExtras(bundle2);
                startActivityForResult(intent3,3);
                break;
            case R.id.place_add3:
                Log.i("asdasdasdasd","asdasd");
                String main = maintext.getText().toString().trim();
                String floor = floortext.getText().toString().trim();
                String doornum = doortext.getText().toString().trim();
                String connect = connectname.getText().toString().trim();
                String telephonenum = telephone.getText().toString().trim();
                final boolean check = defaultbutton.isChecked();

                if(TextUtils.isEmpty(main)||TextUtils.isEmpty(floor)||TextUtils.isEmpty(doornum)||TextUtils.isEmpty(connect)||TextUtils.isEmpty(telephonenum)){
                    DisplayToast("请输入完整信息");
                }else{
                    if(check){
                        User user = new User();
                        fetchUserInfo();
                        List<locationModel> place = BmobUser.getCurrentUser(User.class).getPlace();
                        if(place != null){
                            for(int i = 0 ; i < place.size() ;  i ++ ){
                                locationModel temp = place.get(i);
                                temp.setAutolocation(false);
                                user.setValue("place."+i,temp);
                            }
//                            Log.i("zxc","asdasd");
                            user.update(BmobUser.getCurrentUser(User.class).getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){
                                        Log.i("bmob","地址设置成功");
                                    }else{
                                        Log.i("bmob","地址设置失败："+e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                    User p = new User();
                    p.setObjectId(BmobUser.getCurrentUser(User.class).getObjectId());
                    p.add("place",new locationModel(main,floor,doornum,connect,telephonenum,check));   //添加单个Object=
                    p.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Log.i("zxc","zxc");
                                Intent intent = new Intent();
                                setResult(RESULT_OK,intent);
                                finish();
                            }else{
                                Log.i("bmob","添加失败："+e.getMessage());
                            }
                        }

                    });
                }
                break;
            case R.id.title_back:
                finish();

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    String location = data.getStringExtra("data");
                    maintext.setText(location);
                    Log.i("hello",location);
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    String floor = data.getStringExtra("data");
                    floortext.setText("楼层:"+floor);
                }
                break;
            case 3:
                if(resultCode == RESULT_OK){
                    String doornum = data.getStringExtra("data");
                    doortext.setText("门牌号:"+doornum);
                }
        }
    }
}
