package com.example.mediatest;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class PhoneActivity extends AppCompatActivity{
    private AudioManager mAudioManager;
    private Context mContext;
    private TelephonyManager mTelephonyManager;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int current = mAudioManager.getStreamVolume( AudioManager.STREAM_SYSTEM );
        int max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_SYSTEM );
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SYSTEM", " current : " + current);
                Log.d("SYSTEM", " max : " + max);
                //mAudioManager.adjustSuggestedStreamVolume(AudioManager.ADJUST_UNMUTE, AudioManager.STREAM_RING, 7);
                //mAudioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_UNMUTE, 0);
                int now = mAudioManager.getStreamVolume( AudioManager.STREAM_SYSTEM );
                Log.d("yuanxinyu", "onClick: "+now);
            }
        });
    }

    PhoneStateListener listener=new PhoneStateListener(){
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
//注意，方法必须写在super方法后面，否则incomingNumber无法获取到值。
            super.onCallStateChanged(state, incomingNumber);
            switch(state){
                case TelephonyManager.CALL_STATE_IDLE:
                    System.out.println("挂断");
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    System.out.println("接听");
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    System.out.println("响铃:来电号码"+incomingNumber);
                    mAudioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_TOGGLE_MUTE, 0);
                    setVolumeControlStream(AudioManager.STREAM_RING);
                    break;
            }
        }
    };

}