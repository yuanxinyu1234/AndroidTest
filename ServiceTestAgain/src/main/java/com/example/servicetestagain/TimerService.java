package com.example.servicetestagain;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Locale;

import javax.security.auth.callback.Callback;

public class TimerService extends Service {

    private String data;
    private int count;
    private boolean running = false;

    public TimerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    public class Binder extends android.os.Binder{
        public void setData(String data){
            TimerService.this.data = data;
        }

        public TimerService getService(){
            return TimerService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        data = intent.getStringExtra("data");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        running = true;
        new Thread(){
            @Override
            public void run() {
                while (running){
                    String str = getStringTime(count++);

                    if (callback != null){
                        callback.onDataChange(str);
                    }

                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private String getStringTime(int cnt){
        int second = cnt % 60;
        return String.format(Locale.CHINA,"%02d",second);
    }

    @Override
    public void onDestroy() {
        running = false;
    }

    private Callback callback = null;

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public static interface Callback{
        void onDataChange(String data);
    }
}