package com.example.servicetestagain;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.example.servicetestagain.R;

public class ForegroundService extends Service {

    //ID 不可设置为0，否则不能设置为前台service
    private static final int NOTIFICATION_DOWNLOAD_PROGRESS_ID = 0X0001;

    private boolean isRemove = false;//是否需要移除

    public void createNotfication(){
        /*//使用兼容版本
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //设置状态栏通知的图标
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //设置通知栏横条的图标
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background));
        //禁止用户点击删除按钮删除
        builder.setAutoCancel(false);
        //禁止滑动删除
        builder.setOngoing(true);
        //右上角的时间提示
        builder.setShowWhen(true);
        //通知栏的标题内容
        builder.setContentTitle("I am Forground Service!!!");
        //创建通知
        Notification notification = builder.build();
        //设置为前台服务
        startForeground(NOTIFICATION_DOWNLOAD_PROGRESS_ID,notification);
        //startForeground(NOTIFICATION_DOWNLOAD_PROGRESS_ID, builder.getNotification());*/
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //点击通知后将要启动的程序组件对应的PendingIntent
        //构造Notification
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("冒泡社区")
                .setContentInfo("info")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.p1))
                .setAutoCancel(true)
                .setTicker("冒泡社区：新消息")
                .setContentText("点击返回")
                .setSmallIcon(R.drawable.p1)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)
                .setWhen(System.currentTimeMillis());
        if (android.os.Build.VERSION.SDK_INT>= Build.VERSION_CODES.P){
            NotificationChannel channel = new NotificationChannel("001","my_chanel",NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);//是否在桌面icon右上角展示小红点
            channel.setLightColor(Color.GREEN);//红点颜色
            channel.setShowBadge(true);//是否在久按桌面图标时显示此桌面的通知
            manager.createNotificationChannel(channel);
            builder.setChannelId("001");
        }
        Notification n = builder.build();
        //通知NotificationManager发送Notification
        startForeground(NOTIFICATION_DOWNLOAD_PROGRESS_ID,n);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int i = intent.getExtras().getInt("cmd");
        if (i == 0){
            if (!isRemove){
                createNotfication();
            }
            isRemove = true;
        }else {
            //移除前台服务
            if (isRemove){
                stopForeground(true);
            }
            isRemove = false;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        //移除前台服务
        if (isRemove){
            stopForeground(true);
        }
        isRemove = false;
        super.onDestroy();
    }

    public ForegroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}