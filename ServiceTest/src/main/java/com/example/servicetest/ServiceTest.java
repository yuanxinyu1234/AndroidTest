package com.example.servicetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

public class ServiceTest extends AppCompatActivity implements View.OnClickListener {

    private Button btn_start_service;
    private Button btn_stop_service;
    private Button btn_bind_service;
    private Button btn_unbind_service;

    /*private MyService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder) service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);
        btn_start_service = findViewById(R.id.btn_start_service);
        btn_stop_service = findViewById(R.id.btn_stop_service);
        btn_bind_service = findViewById(R.id.btn_stop_service);
        btn_unbind_service = findViewById(R.id.btn_stop_service);
        btn_start_service.setOnClickListener(this);
        btn_stop_service.setOnClickListener(this);
        /*btn_bind_service.setOnClickListener(this);
        btn_unbind_service.setOnClickListener(this);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_service:
                Intent startIntent = new Intent(this,MyService.class);
                startService(startIntent);//启动服务
                break;
            case R.id.btn_stop_service:
                Intent stopIntent = new Intent(this,MyService.class);
                stopService(stopIntent);//停止服务
                break;
            /*case R.id.btn_bind_service:
                Intent bindIntent = new Intent(this,MyService.class);
                bindService(bindIntent,connection,BIND_ABOVE_CLIENT);//绑定服务
                break;
            case R.id.btn_unbind_service:
                unbindService(connection);//解绑服务
                break;*/
            default:
                break;
        }
    }
}