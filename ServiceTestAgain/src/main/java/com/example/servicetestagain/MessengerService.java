package com.example.servicetestagain;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

public class MessengerService extends Service {

    static final int MSG_SAY_HELLO = 1;

    /**
     * 用于接收从客户端传递过来的数据
     */
    class IncomingHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_SAY_HELLO:
                    Toast.makeText(MessengerService.this,"had receiver message",Toast.LENGTH_SHORT).show();
                    //回复客户端信息，该对象由客户端传进来
                    Messenger client = msg.replyTo;
                    //获取回复信息的消息实体
                    Message replyMsg = Message.obtain(null,MessengerService.MSG_SAY_HELLO);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply","had receiver");
                    replyMsg.setData(bundle);
                    //向客户端发送消息
                    try {
                        client.send(replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    /**
     * 创建Messenger并传入Handler实例对象
     */
    final Messenger messenger = new Messenger(new IncomingHandler());

    public MessengerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}