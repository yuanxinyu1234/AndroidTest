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
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MessengerActivity extends AppCompatActivity {

    Messenger mService = null;//与服务端交互的Messenger

    private Button btn_bind;
    private Button btn_unbind;
    private Button btn_sendMsg;

    boolean mBound;//Flag indicating whether we have called bind on the service.

    /**
     * 实现与服务端连接的对象
     */
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            /**
             * 通过服务端传递的IBinder对象，创建相应的Messenger
             * 通过giantMessenger对象与服务端进行交互
             */
            mService = new Messenger(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };

    /**
     * 用于接收服务器返回的信息
     * @param v
     */
    private Messenger mReceiverReplyMsg = new Messenger(new ReceiverReplyMsgHandler());

    private static class ReceiverReplyMsgHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case MessengerService.MSG_SAY_HELLO:
                    Log.d("TAG", "handleMessage: "+msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    public void sayHello(View v){
        if (!mBound) return;
        //创建与服务交互的消息实体Message
        Message msg = Message.obtain(null,MessengerService.MSG_SAY_HELLO,0,0);
        //把接收服务器端的回复的Messenger通过Message的replyTo参数传给服务端
        msg.replyTo = mReceiverReplyMsg;
        try {
            //发送消息
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        btn_bind = findViewById(R.id.btn_bind);
        btn_unbind = findViewById(R.id.btn_unbind);
        btn_sendMsg = findViewById(R.id.btn_sendMsg);

        btn_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //当前Activity绑定服务端
                bindService(new Intent(MessengerActivity.this,MessengerService.class),
                        connection, Context.BIND_AUTO_CREATE);
            }
        });

        //发消息给服务端
        btn_sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sayHello(v);
            }
        });

        btn_unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBound){
                    unbindService(connection);
                    mBound =false;
                }
            }
        });
    }
}