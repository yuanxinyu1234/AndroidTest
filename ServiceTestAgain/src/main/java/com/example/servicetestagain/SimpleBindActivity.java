package com.example.servicetestagain;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SimpleBindActivity extends AppCompatActivity {

    private ServiceConnection connection;
    private SimpleBinderService mService;

    private Button btn_bind;
    private Button btn_unbind;
    private Button btn_getServiceData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);

        connection = new ServiceConnection() {
            /**
             *与服务器端交互的接口方法，绑定服务的时候被回调，
             * 在这个方法获取绑定Service传递过来的IBinder对象，
             * 通过这个IBinder对象，实现宿主和Service的交互
             */
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //获取Binder

                SimpleBinderService.LocalBinder binder = (SimpleBinderService.LocalBinder) service;
                mService = binder.getService();
            }

            /**
             *当取消绑定的时候被回调。
             * 但正常情况下不会被调用，它的调用时机是当Service服务被意外销毁时，
             * 例如内存的资源不足时这个方法才被自动调用。
             */
            @Override
            public void onServiceDisconnected(ComponentName name) {
                mService = null;
            }
        };

        btn_bind = findViewById(R.id.btn_bind);
        btn_unbind = findViewById(R.id.btn_unbind);
        btn_getServiceData = findViewById(R.id.btn_getServiceData);

        //创建绑定对象
        final Intent intent = new Intent(this,SimpleBinderService.class);

        //开启绑定
        btn_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(intent,connection, Service.BIND_AUTO_CREATE);
            }
        });
        //解除绑定
        btn_unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mService != null){
                    mService = null;
                    unbindService(connection);
                }
            }
        });
        //获取数据
        btn_getServiceData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mService != null){
                    Toast.makeText(SimpleBindActivity.this,"count:"+mService.getCount(),
                            Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SimpleBindActivity.this,"请绑定",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}