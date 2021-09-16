package com.example.servicetest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.servicetest.interfaces.DownloadListener;

import java.io.File;
import java.io.FileInputStream;

public class DownloadService extends Service {

    private DownloadTasks downloadTasks;
    private String downloadUrl;

    private DownloadListener listener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1,getNotification("Downloading...",
                    progress));
        }

        @Override
        public void onSuccess() {
            downloadTasks = null;
            //下载成功时将前台服务通知关闭，并创建一个下载成功的通知
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("Download success",
                    -1));
            Toast.makeText(DownloadService.this,"Download success",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            downloadTasks = null;
            //下载失败时将前台服务通知关闭，并创建一个下载失败的通知
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("Download Failed",
                    -1));
            Toast.makeText(DownloadService.this,"Download Failed",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            downloadTasks = null;
            Toast.makeText(DownloadService.this,"Download Paused",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            downloadTasks = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this,"Download Canceled",
                    Toast.LENGTH_SHORT).show();
        }
    };

    public DownloadService() {
    }

    private DownloadBinder mBinder = new DownloadBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class DownloadBinder extends Binder{
        public void startDownload(String url){
            if (downloadTasks == null){
                downloadUrl = url;
                downloadTasks = new DownloadTasks(listener);
                downloadTasks.execute(downloadUrl);
                startForeground(1,getNotification("Downloading...",0));
                Toast.makeText(DownloadService.this,"Downloading...",Toast.LENGTH_SHORT).show();
            }
        }

        public void pauseDownload(){
            if (downloadTasks != null){
                downloadTasks.pasueDownload();
            }
        }

        public void cancelDownload(){
            if (downloadTasks != null){
                downloadTasks.cancelDownload();
            }else {
                if (downloadUrl != null){
                    //取消下载时需要将文件删除，并将通知关闭
                    String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String directory = Environment.getExternalStoragePublicDirectory
                            (Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory + fileName);
                    if (file.exists()){
                        file.delete();
                    }
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownloadService.this,"Canceled",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private NotificationManager getNotificationManager(){
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title,int progress){
        String ID = "com.example.servicetest";	//这里的id里面输入自己的项目的包的路径
        String NAME = "Channel One";
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder builder;
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(ID, NAME, manager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            manager.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(DownloadService.this).setChannelId(ID);
        } else {
            builder = new NotificationCompat.Builder(DownloadService.this);
        }
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher));
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle(title);
        if (progress > 0){
            //当progress大于或等于0时才需显示下载进度
            builder.setContentTitle(progress + "%");
            builder.setProgress(100,progress,false);
        }
        return builder.build();
    }
}