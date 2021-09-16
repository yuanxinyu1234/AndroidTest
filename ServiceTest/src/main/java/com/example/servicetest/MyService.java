package com.example.servicetest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.concurrent.BlockingDeque;

public class MyService extends Service {

    /*private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder{

        public void startDownload(){
            Log.d("MyService", "startDownload: ");
        }

        public int getProgress(){
            Log.d("MyService", "getProgress: ");
            return 0;
        }

        public IBinder onBind(Intent intent) {
            return mBinder;
        }
    }*/

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //第一次创建时调用
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService", "onCreate: ");
        //获取系统的NotificationManager服务
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //点击通知后将要启动的程序组件对应的PendingIntent
        Intent intent = new Intent(this,ServiceTest.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        //构造Notification
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("冒泡社区")
                .setContentInfo("info")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background))
                .setAutoCancel(true)
                .setTicker("冒泡社区：新消息")
                .setContentText("点击返回")
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent);
        if (android.os.Build.VERSION.SDK_INT>= Build.VERSION_CODES.P){
            NotificationChannel channel = new NotificationChannel("001","my_chanel", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);//是否在桌面icon右上角展示小红点
            channel.setLightColor(Color.GREEN);//红点颜色
            channel.setShowBadge(true);//是否在久按桌面图标时显示此桌面的通知
            manager.createNotificationChannel(channel);
            builder.setChannelId("001");
        }
        /*Notification n = builder.build();
        //通知NotificationManager发送Notification
        manager.notify(999,n);*/
    }

    //启动
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService", "onStartCommand: ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                //处理具体逻辑
                stopSelf();
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    //销毁
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService", "onDestroy: ");
    }
}