package com.example.servicetestagain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener,
        android.content.ServiceConnection {

    private TextView textView;
    private TimerService.Binder binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        Button btnBind = (Button) findViewById(R.id.bindBtn);
        Button btnUnbind = (Button) findViewById(R.id.unbindBtn);
        textView = (TextView) findViewById(R.id.text1);

        btnBind.setOnClickListener(this);
        btnUnbind.setOnClickListener(this);

        String str = "111111";
        String sub = str.substring(0,1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bindBtn:
                startService(new Intent(this,TimerService.class));
                bindService(new Intent(this,TimerService.class),this,
                        Context.BIND_AUTO_CREATE);
                break;
            case R.id.unbindBtn:
                unbindService(this);
                stopService(new Intent(this,TimerService.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        binder = (TimerService.Binder) service;
        binder.getService().setCallback(new TimerService.Callback() {
            @Override
            public void onDataChange(String data) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("data",data);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            textView.setText(msg.getData().getString("data"));
            int i = Integer.parseInt(msg.getData().getString("data"));
            if (i != 0 && i % 3 == 0){
                Toast.makeText(getApplicationContext(),"已过3秒",Toast.LENGTH_SHORT).show();
            }
        }
    };
}