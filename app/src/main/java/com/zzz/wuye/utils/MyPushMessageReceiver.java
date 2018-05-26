package com.zzz.wuye.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.zzz.wuye.Message.model.msg;
import com.zzz.wuye.R;
import com.zzz.wuye.view.MainActivity;

import cn.bmob.push.PushConstants;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.zzz.wuye.utils.Constants.sms;

/**
 * Created by oruret on 2018/4/18.
 */

//TODO 集成：1.3、创建自定义的推送消息接收器，并在清单文件中注册
public class MyPushMessageReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)||sms){
            Gson gson = new Gson();
            notify account1 = gson.fromJson(intent.getStringExtra("msg"), notify.class);
            msg message = new msg();
            message.setContent(account1.getAlert());
            message.setUsername("系统消息");
            message.setUserid("9527");
            message.save(new SaveListener<String>() {
                @Override
                public void done(String objectId,BmobException e) {
                    if(e==null){

                        Log.i("hello","添加数据成功，返回objectId为："+objectId);
                    }else{
//                        toast("创建数据失败：" + e.getMessage());
                    }
                }
            });

            Log.d("bmob", "客户端收到推送内容："+account1.getAlert());

            //判断是否是8.0上设备
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_LOW;
                NotificationChannel mChannel = null;
                if (mChannel == null) {
                    mChannel = new NotificationChannel("12345","消息推送",NotificationManager.IMPORTANCE_DEFAULT);
                }
                NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
                PendingIntent intent2 = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
                Notification notify = new Notification.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("收到一条推送")
                        .setContentIntent(intent2)
                        .setContentText(account1.getAlert())
                        .setChannelId("12345")
                        .setOngoing(true)
                        .build();
                manager.notify(1, notify);
            }else{
                NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
                PendingIntent intent2 = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
                Notification notify = new Notification.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("收到一条推送")
                        .setContentIntent(intent2)
                        .setContentText(account1.getAlert())
                        .setOngoing(true)
                        .build();
                manager.notify(1, notify);

            }

        }
    }

}