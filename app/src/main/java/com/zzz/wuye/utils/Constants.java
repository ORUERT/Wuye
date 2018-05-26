package com.zzz.wuye.utils;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.zzz.wuye.register.model.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;

//import cn.bmob.v3.BmobUser;


public class Constants {
	/**
	 ******************************************* 参数设置信息开始 ******************************************
	 */

	// 用户对象
	public User userInfo = BmobUser.getCurrentUser(User.class);
	//Bmobkey
	public static String bmobKey = "0a35b82e0151b139e941a090e9b8015f";

	//是否推送消息
	public static boolean sms = false;
	//User ObjectId
//	public static String ObjectId =  BmobUser.getCurrentUser(User.class).getObjectId();

	public static List<String> DAILYCHECKEDLIST;



	// 应用名称
	public static String APP_NAME = "";

	// 服务器地址
	public static String BASE_URL = "http://172.16.17.117:8080/FitnessServer/";

	// 图片路径
	public static final String IMAGE_URL = "http://58.211.5.34:8080/studioms/staticmedia/images/#";

	// 视频路径
	public static final String VIDEO_URL = "http://58.211.5.34:8080/studioms/staticmedia/video/#";

	// 保存参数文件夹名称
	public static final String SHARED_PREFERENCE_NAME = "fitness_prefs";

	// SDCard路径
	public static final String SD_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ File.separator
			+ "com.lilei.fitness";

	// 图片存储路径
	public static final String BASE_PATH = SD_PATH + "/images";


	// 缓存图片路径
	public static final String BASE_IMAGE_CACHE = BASE_PATH + "/cache";

	// 需要分享的图片
	public static final String SHARE_FILE = BASE_PATH + "/QrShareImage.png";

	// 手机IMEI号码
	public static String IMEI = "";

	// 手机号码
	public static String TEL = "";

	// 屏幕高度
	public static int SCREEN_HEIGHT = 800;

	// 屏幕宽度
	public static int SCREEN_WIDTH = 480;

	// 屏幕密度
	public static float SCREEN_DENSITY = 1.5f;

	// 分享成功
	public static final int SHARE_SUCCESS = 0X1000;

	// 分享取消
	public static final int SHARE_CANCEL = 0X2000;

	// 分享失败
	public static final int SHARE_ERROR = 0X3000;

	// 开始执行
	public static final int EXECUTE_LOADING = 0X4000;

	// 正在执行
	public static final int EXECUTE_SUCCESS = 0X5000;

	// 执行完成
	public static final int EXECUTE_FAILED = 0X6000;

	// 加载数据成功
	public static final int LOAD_DATA_SUCCESS = 0X7000;

	// 加载数据失败
	public static final int LOAD_DATA_ERROR = 0X8000;

	// 动态加载数据
	public static final int SET_DATA = 0X9000;

	// 未登录
	public static final int NONE_LOGIN = 0X10000;

	/**
	 * 更新本地用户信息
	 * 注意：需要先登录，否则会报9024错误
	 *
	 * @see cn.bmob.v3.helper.ErrorCode#E9024S
	 */
	public static void fetchUserInfo() {
		User.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
			@Override
			public void done(String s, BmobException e) {
				if (e == null) {
					Log.i("hello","Newest UserInfo is " + s);
				} else {
					Log.i("error", String.valueOf(e));
				}
			}
		});
	}
	/**
	处理字符串过长显示超过控件边界
	 **/
	public static String handleTextDisplay(String str,int sizeOfText){
	    if(sizeOfText<str.length())return str.substring(0,sizeOfText)+"...";
		return str;
	}

	//根据Uri获取图片真实路径
	//简易处理板  （实际本没有发现什么问题，可以直接使用）
	public static String getRealPathFromURI(Context context, Uri contentURI) {
		String result;
		Cursor cursor = context.getContentResolver().query(contentURI,
				new String[]{MediaStore.Images.ImageColumns.DATA},
				null, null, null);
		if (cursor == null) result = contentURI.getPath();
		else {
			cursor.moveToFirst();
			int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			result = cursor.getString(index);
			cursor.close();
		}
		return result;
	}
//	public static void setStatusBarColor(Activity activity, int statusColor) {
//		Window window = activity.getWindow();
//		//取消状态栏透明
//		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//		//添加Flag把状态栏设为可绘制模式
//		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//		//设置状态栏颜色
//		window.setStatusBarColor(statusColor);
//		//设置系统状态栏处于可见状态
//		window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//		//让view不根据系统窗口来调整自己的布局
//		ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
//		View mChildView = mContentView.getChildAt(0);
//		if (mChildView != null) {
//			ViewCompat.setFitsSystemWindows(mChildView, false);
//			ViewCompat.requestApplyInsets(mChildView);
//		}
//	}
	/**
	 ******************************************* 参数设置信息结束 ******************************************
	 */
}
