package com.zzz.wuye.exchange;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.Button;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zzz.wuye.R;
import com.zzz.wuye.exchange.model.stuff;
import com.zzz.wuye.fragment.SelectPicPopupWindow;
import com.zzz.wuye.register.model.User;
import com.zzz.wuye.view.base.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;

import static com.zzz.wuye.utils.Constants.getRealPathFromURI;
import static com.zzz.wuye.utils.Constants.handleTextDisplay;

public class exchangeadd extends BaseActivity implements View.OnClickListener {
    private SelectPicPopupWindow menuWindow;

    private TextView stufftitle;
    private TextView stuffcontent;
    private TextView stufftele;
    private TextView stuffconnect;
    private TextView stuffpercent;
    private TextView stuffprice;
    private TextView stuffcheck;
    private boolean flag = false;

    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private int imageselete = 0 ;
    private Uri userImageUri;
    private static final int REQUEST_CODE_PICK_IMAGE = 0;
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 1;
    private static final int RESIZE_REQUEST_CODE = 2;
    private List<String> imageList = new ArrayList<>();
    //    private List<Uri> imageList = new ArrayList<>();
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE"
    };
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchangeadd);
        findViewById();
        initView();
    }
    @Override
    protected void findViewById() {
        stufftitle = findViewById(R.id.stuff_projectname);
        stuffcontent = findViewById(R.id.stuff_projectdetail);
        stufftele = findViewById(R.id.stuff_telephone);
        stuffconnect = findViewById(R.id.stuff_user);

        submit = findViewById(R.id.stuff_submit);
        image1 = findViewById(R.id.stuff_image1);
        image2 = findViewById(R.id.stuff_image2);
        image3 = findViewById(R.id.stuff_image3);
    }

    @Override
    protected void initView() {
        submit.setOnClickListener(this);
        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        menuWindow = new SelectPicPopupWindow(exchangeadd.this, new
                View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menuWindow.dismiss();
                        switch (v.getId()){
                            case R.id.takePhotoBtn: {
                                String state = Environment.getExternalStorageState();

                                if (state.equals(Environment.MEDIA_MOUNTED)) {
                                    Intent getImageByCamera = new
                                            Intent("android.media.action.IMAGE_CAPTURE");
                                    startActivityForResult(getImageByCamera,
                                            REQUEST_CODE_CAPTURE_CAMEIA);
                                }
                                else {
                                    Toast.makeText(exchangeadd.this,"请确认已经插入SD卡", Toast.LENGTH_LONG).show();
                                }
                                break;
                            }
                            case R.id.pickPhotoBtn:
//                                Intent intent = new Intent(Intent.ACTION_PICK);//从相册中选取图片
                                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                intent.setType("image/*");//相片类型
                                startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
                                break;
                            case R.id.cancelBtn:{
                                break;
                            }
                        }
                    }
                });
        stuff repairCard1 = (stuff) getIntent().getSerializableExtra("stuff");
        if(repairCard1!=null){
            flag = true;
            stufftitle.setText(repairCard1.getTitle());
            stuffcontent.setText(repairCard1.getContent());
            stuffconnect.setText(repairCard1.getConnect());
            stufftele.setText(repairCard1.getTelephone());
            // 获得图片地址url
            BmobFile image_1 = repairCard1.getImage().get(0);
            if(image_1 == null){
                image1.setImageResource(R.drawable.calendar);
            }else {
                String now_user_img_url = image_1.getFileUrl();
                imageList.add(now_user_img_url);
                ImageLoader.getInstance().displayImage((String) now_user_img_url, image1);
            }
            BmobFile image_2 = repairCard1.getImage().get(1);
            if(image_2 == null){
                image1.setImageResource(R.drawable.calendar);
            }else {
                String now_user_img_url = image_2.getFileUrl();
                imageList.add(now_user_img_url);

                ImageLoader.getInstance().displayImage((String) now_user_img_url, image2);
            }
            BmobFile image_3 = repairCard1.getImage().get(2);
            if(image_3 == null){
                image1.setImageResource(R.drawable.calendar);
            }else {
                String now_user_img_url = image_3.getFileUrl();
                imageList.add(now_user_img_url);

                ImageLoader.getInstance().displayImage((String) now_user_img_url, image3);
            }
//            stuffcheck.setText(repairCard1.isCheck()?"已解决":"未解决");

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.stuff_image1:
                imageselete = 1;
                verifyStoragePermissions(this);
                break;
            case R.id.stuff_image2:
                imageselete = 2;
                verifyStoragePermissions(this);
                break;
            case R.id.stuff_image3:
                imageselete = 3;
                verifyStoragePermissions(this);
                break;
            case R.id.stuff_submit:
                Log.i("sutff_submit","123123123");
                final String title1 = stufftitle.getText().toString().trim();
                final String content1 = stuffcontent.getText().toString().trim();
                final String telephone1 = stufftele.getText().toString().trim();
                final String connectname1 = stuffconnect.getText().toString().trim();
                if(TextUtils.isEmpty(title1)||TextUtils.isEmpty(content1)||TextUtils.isEmpty(telephone1)||TextUtils.isEmpty(connectname1)){
                    Toast.makeText(this,"请输入完整信息",Toast.LENGTH_SHORT);
                }else {
                    //将list转成String 数组

                    stuff repairCard1 = (stuff) getIntent().getSerializableExtra("stuff");
                    if(flag) {
                        Log.i("asdasd", "asd123");
                        stuff house = new stuff();
                        house.setTitle(title1);
                        house.setContent(content1);
                        house.setConnect(connectname1);
                        house.setTelephone(telephone1);
                        house.update(repairCard1.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    DisplayToast("更新成功");
                                    Log.i("bmob", "更新成功");
                                } else {
                                    Log.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                                }
                            }
                        });
                    }else {
                        final String [] imageArray = new String[imageList.size()];
                        for(int i = 0 ;i < 3;i ++){
                            imageArray[i] = imageList.get(i);
                        }
                        BmobFile.uploadBatch(imageArray, new UploadBatchListener() {

                            @Override
                            public void onSuccess(List<BmobFile> files, List<String> urls) {
                                //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                                //2、urls-上传文件的完整url地址
                                if(urls.size()==imageList.size()){//如果数量相等，则代表文件全部上传完成
                                    //do something
                                    Log.i("image1234", String.valueOf(flag));

                                    for(int i = 10000 ; i < 10009 ; i ++){
                                        stuff house = new stuff();
                                        house.setTitle(i+title1);
                                        for(int j = 1000000000 ; j <=i+1000000000 ;j  ++ )house.setTitle(title1+j);
                                        house.setContent(content1);
                                        house.setConnect(connectname1);
                                        house.setTelephone(telephone1);
                                        house.setUploadId(BmobUser.getCurrentUser(User.class).getObjectId());
                                        house.setImage(files);
                                        house.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String s, BmobException e) {
                                                if(e == null){
                                                    DisplayToast("房屋信息上传成功");
                                                    finish();
                                                    Log.i("successful","Bmob房屋信息上传成功");
                                                }else{
                                                    Log.i("error","Bmob保修信息保存失败"+e);
                                                }
                                            }
                                        });
                                    }

                                }
                            }

                            @Override
                            public void onError(int statuscode, String errormsg) {
                                DisplayToast("错误码"+statuscode +",错误描述："+errormsg);
                            }

                            @Override
                            public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
                                //1、curIndex--表示当前第几个文件正在上传
                                //2、curPercent--表示当前上传文件的进度值（百分比）
                                //3、total--表示总的上传文件数
                                //4、totalPercent--表示总的上传进度（百分比）
                            }
                        });
                    }
                }
                break;
        }
    }
    private void reSizeImage(Uri uri) {//重新剪裁图片的大小
        File outputImage = new File(Environment.getExternalStorageDirectory(), "crop.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }//删除输出路径的文件，保证输出的Uri是一个具有路径的空文件，可以解决某些机型截取后返回为null
        userImageUri = Uri.fromFile(outputImage);

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", true);//允许剪切
        intent.putExtra("scale", true);//支持缩放
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);//输出是X方向的比例
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高，切忌不要再改动下列数字，会卡死
        intent.putExtra("outputX", 500);//输出X方向的像素
        intent.putExtra("outputY", 500);//
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", false);//设置为不返回数据
        /**
         * 此方法返回的图片只能是小图片（测试为高宽160px的图片）
         * 故将图片保存在Uri中，调用时将Uri转换为Bitmap，此方法还可解决miui系统不能return data的问题
         */
//        intent.putExtra("return-data", true);
//        intent.putExtra("output", Uri.fromFile(new File("/mnt/sdcard/temp")));//保存路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, userImageUri);//输出的Uri
//        Log.d(TAG, "reSizeImage() called with: " + "uri = [" + userImageUri + "]");
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }

    private void showImage(Uri uri) {
        try {
//            Log.d(TAG, "showImage: " + getRealPathFromURI(this,uri));
//            Log.d(TAG, "showImage: " + uri.toString());

            SharedPreferences.Editor editor = this.getSharedPreferences("image",MODE_PRIVATE).edit();
            editor.putString("imageUri",uri.toString());
            editor.commit();//保存头像的uri

            Bitmap photo = MediaStore.Images.Media.getBitmap(this
                    .getContentResolver(), uri);
            imageList.add(imageselete-1,getRealPathFromURI(this,uri));
            switch (imageselete){
                case 1:
                    image1.setImageDrawable(new BitmapDrawable(photo));
                    break;
                case 2:
                    image2.setImageDrawable(new BitmapDrawable(photo));
                    break;
                case 3:
                    image3.setImageDrawable(new BitmapDrawable(photo));
                    break;
            }
//            res_head.setImageDrawable(new BitmapDrawable(photo));
            Toast.makeText(this,"头像设置成功",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this,"头像设置失败",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    //针对安卓6.0以上机型
    public void verifyStoragePermissions(Activity activity) {
        // 检查是否有手机sd卡读取权限
        try {
            int permission1 = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.READ_EXTERNAL_STORAGE");
            int permission2 = ActivityCompat.checkSelfPermission(activity,"android.permission.WRITE_EXTERNAL_STORAGE");

            if (permission1 != PackageManager.PERMISSION_GRANTED||permission2!=PackageManager.PERMISSION_GRANTED) {
                // 如果没有权限，则申请，如下
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(PERMISSIONS_STORAGE,
                            REQUEST_EXTERNAL_STORAGE);
                }
            }else {
                menuWindow.showAtLocation(getWindow().getDecorView(),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (grantResults.length > 0 &&  grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //用户同意权限
            Log.i("permission","complex");
            menuWindow.showAtLocation(getWindow().getDecorView(),
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else {
            //用户拒绝权限
//            System.exit(0);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri imageUri = null;
        if(resultCode == Activity.RESULT_CANCELED){
//            res_head.setImageBitmap(null);//预设的图片
        }else if(resultCode == Activity.RESULT_OK) {//选取成功后进行裁剪
            if (requestCode == REQUEST_CODE_PICK_IMAGE) {
                //从图库中选择图片作为头像
                imageUri = data.getData();
                reSizeImage(imageUri);
            } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
                //使用相机获取头像
                imageUri = data.getData();
                if (imageUri == null) {
                    //use bundle to get data
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap bitMap = (Bitmap) bundle.get("data"); //get bitmap
                        imageUri = Uri.parse(MediaStore.Images.Media.
                                insertImage(this.getContentResolver(), bitMap, null,null));
//                        Log.d(TAG, "onActivityResult: bndle != null" + imageUri);
                        reSizeImage(imageUri);
                    } else {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }else if(requestCode == RESIZE_REQUEST_CODE){
                Log.d(TAG, "onActivityResult: " + userImageUri);
                showImage(userImageUri);
            }
        }
    }
}