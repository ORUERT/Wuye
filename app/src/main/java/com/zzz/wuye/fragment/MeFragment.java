package com.zzz.wuye.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.rey.material.widget.Switch;
import com.zzz.wuye.R;
import com.zzz.wuye.exchange.myexchange;
import com.zzz.wuye.location.placeActivity;
import com.zzz.wuye.myFavorActivity.myFavor;
import com.zzz.wuye.payment.Payment;
import com.zzz.wuye.register.model.User;
import com.zzz.wuye.sellhouse.view.mysellhouse;
import com.zzz.wuye.utils.AppManager;
import com.zzz.wuye.utils.Constants;
import com.zzz.wuye.verify.VerifyActivity;
import com.zzz.wuye.login.LoginActivity;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;
import static com.zzz.wuye.utils.Constants.fetchUserInfo;
import static com.zzz.wuye.utils.Constants.sms;

/**
 * Created by djzhao on 17/04/30.
 */

public class MeFragment extends Fragment implements View.OnClickListener {


    private TextView exit;
    private TextView nickname;
    private SelectPicPopupWindow menuWindow;
    private View v;
    private View location;
    private View verify;
    private View favor;
    private View payment;
    private View exchange;
    private View sellhouse;

    private Switch tuisong;
    private CircleImageView res_head;
    private static final int REQUEST_CODE_PICK_IMAGE = 0;
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 1;
    private static final int RESIZE_REQUEST_CODE = 2;
    private static final String TAG = "ChooseImageMainActivity";
    private Uri userImageUri;//保存用户头像的uri
    private Context context;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE"
    };



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_me, null);
        context = v.getContext();

        findViewById1(v);
        initView();
        return v;
    }

    public void findViewById1(View v) {
        nickname = (TextView)v.findViewById(R.id.me_homepage_username);
        res_head = (CircleImageView) v.findViewById(R.id.item_userimage);
        location = (View) v.findViewById(R.id.me_item_location);
        verify = (View) v.findViewById(R.id.me_item_verify);
        favor = (View) v.findViewById(R.id.me_item_favor);
        payment = (View) v.findViewById(R.id.me_item_payment);
        exit = (TextView) v.findViewById(R.id.me_item_exit);
        tuisong = (Switch) v.findViewById(R.id.me_tuisong);
        exchange = (View) v.findViewById(R.id.me_item_exchange);
        sellhouse = (View) v.findViewById(R.id.me_item_housesell);
    }

    public void initView() {
        res_head.setOnClickListener(this);
        payment.setOnClickListener(this);
        location.setOnClickListener(this);
        exit.setOnClickListener(this);
        favor.setOnClickListener(this);
        verify.setOnClickListener(this);
        tuisong.setOnClickListener(this);
        exchange.setOnClickListener(this);
        sellhouse.setOnClickListener(this);
        menuWindow = new SelectPicPopupWindow(context, new
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
                                    Toast.makeText(getContext(),
                                            "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
                                }
                                break;
                            }
                            case R.id.pickPhotoBtn:
//                                Intent intent = new Intent(Intent.ACTION_PICK);//从相册中选取图片
//                                Intent intent = new Intent("android.intent.action.GET_CONTENT");//从相册/文件管理中选取图片
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
        initialization();

    }
    public void initialization() {
        fetchUserInfo();
        // 根据ID查询单条数据
        User current_user = BmobUser.getCurrentUser(User.class);
        // 获得图片地址url
        BmobFile now_user_img = current_user.getImage();
        if(now_user_img == null){
            res_head.setImageResource(R.drawable.calendar);
        }else {
            String now_user_img_url = now_user_img.getFileUrl();
            // 用户头像实例化
            ImageLoader.getInstance().displayImage((String) now_user_img_url, res_head);
        }
        // 获得信息
        String name = current_user.getNickName();
        nickname.setText(name);



    }
    /**
     * 将图片上传到 Bmob
     */
    public void uploadImage(String uri) throws URISyntaxException {
        final BmobFile file = new BmobFile(new File(new URI(userImageUri.toString())));
        file.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    User newUser = new User();
                    newUser.setImage(file);
                    BmobUser bmobUser = BmobUser.getCurrentUser(User.class);
                    newUser.update(bmobUser.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                showImage(userImageUri);
                                Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, e.getErrorCode()+e.toString(), Toast.LENGTH_SHORT).show();
                                Log.i("error",e.getErrorCode()+e.toString());
                            }
                        }
                    });
                    Log.d(TAG, "done() called with: " + "true");
                } else {
                    Log.d(TAG, "done() called with: " + "false"+e.getErrorCode()+e.toString());

                }
            }
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri imageUri = null;
        if(resultCode == Activity.RESULT_CANCELED){
//            Toast.makeText(context,"获取失败",Toast.LENGTH_SHORT).show();
            res_head.setImageBitmap(null);//预设的图片
        }else if(resultCode == Activity.RESULT_OK) {//选取成功后进行裁剪
            if (requestCode == REQUEST_CODE_PICK_IMAGE) {
                //从图库中选择图片作为头像
                imageUri = data.getData();
//                Log.d(TAG, "onActivityResult: " + imageUri);
                reSizeImage(imageUri);
            } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
                //使用相机获取头像
//                Log.d(TAG, "onActivityResult: from photo");
                imageUri = data.getData();
//                Log.d(TAG, "onActivityResult: " + imageUri);
                if (imageUri == null) {
                    //use bundle to get data
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap bitMap = (Bitmap) bundle.get("data"); //get bitmap
                        imageUri = Uri.parse(MediaStore.Images.Media.
                                insertImage(getContext().getContentResolver(), bitMap, null,null));
//                        Log.d(TAG, "onActivityResult: bndle != null" + imageUri);
                        reSizeImage(imageUri);
                    } else {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }else if(requestCode == RESIZE_REQUEST_CODE){
                Log.d(TAG, "onActivityResult: " + userImageUri);

                try {
                    uploadImage(userImageUri.toString());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }


            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_userimage:
                verifyStoragePermissions(getActivity());
                break;
            case R.id.me_item_location:
                Intent intent = new Intent(getActivity(),placeActivity.class);
                startActivity(intent);
                break;
            case R.id.me_item_payment:
                Intent intent2 = new Intent(getActivity(),Payment.class);
                startActivity(intent2);
                break;
            case R.id.me_item_favor:
                Intent intent4 = new Intent(getActivity(),myFavor.class);
                startActivity(intent4);
                break;


            case R.id.me_tuisong:
                sms = tuisong.isChecked();
                break;
            case R.id.me_item_exchange:
                Intent intent5 = new Intent(getActivity(),myexchange.class);
                startActivity(intent5);
                break;
            case R.id.me_item_housesell:
                Intent intent6 = new Intent(getActivity(),mysellhouse.class);
                intent6.putExtra("hello","hello");
                startActivity(intent6);
                break;

            case R.id.me_item_verify:
                fetchUserInfo();
                if(BmobUser.getCurrentUser(User.class).getType()==0){
                    Intent intent3 = new Intent(getActivity(), VerifyActivity.class);
                    startActivity(intent3);
                }else {
                    Toast.makeText(getContext(),"已认证",Toast.LENGTH_SHORT);
                }
                break;

            case R.id.me_item_exit:
                SystemClock.sleep(500);
                BmobUser.logOut();   //清除缓存用户对象
                AppManager.getInstance().killAllActivity();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }
    private void showImage(Uri uri) {
        Log.d(TAG, "showImage: ");
        try {
            Log.d(TAG, "showImage: " + uri.toString());
            SharedPreferences.Editor editor = getActivity().getSharedPreferences("image",MODE_PRIVATE).edit();
            editor.putString("imageUri",uri.toString());
            editor.commit();//保存头像的uri

            Bitmap photo = MediaStore.Images.Media.getBitmap(context
                    .getContentResolver(), uri);
            res_head.setImageDrawable(new BitmapDrawable(photo));
            Toast.makeText(getContext(),"头像设置成功",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getContext(),"头像设置失败",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    //针对安卓6.0以上机型
    public void verifyStoragePermissions(Activity activity) {
        // 检查是否有手机sd卡读取权限
        try {
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.READ_EXTERNAL_STORAGE");

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 如果没有权限，则申请，如下
                requestPermissions(PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE);
            }else{
                menuWindow.showAtLocation(v,
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
            menuWindow.showAtLocation(v,
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else {
            //用户拒绝权限
//            System.exit(0);
        }
    }

}
