package com.example.mediatest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class AudioPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer mediaPlayer = new MediaPlayer();

    private Context context;

    private Button btn_play;
    private Button btn_pause;
    private Button btn_stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);

        btn_play = findViewById(R.id.btn_play);
        btn_pause = findViewById(R.id.btn_pause);
        btn_stop = findViewById(R.id.btn_stop);
        btn_play.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_stop.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(AudioPlayerActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AudioPlayerActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else {
            initMediaPlayer();//初始化MediaPlayer
        }

        /*if (Build.VERSION.SDK_INT >= 23){
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
            for (String str : permissions){
                if (ContextCompat.checkSelfPermission(AudioPlayerActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(AudioPlayerActivity.this, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    initMediaPlayer();//初始化MediaPlayer
                }
            }
        }*/
    }


    private void initMediaPlayer(){
        try {
            File file = new File(Environment.getExternalStorageDirectory(),"a1.mp3");
            //File file = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC)+"周杰伦 - 珊瑚海.mp3");
            //File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),"周杰伦 - 珊瑚海.mp3");
            //File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"周杰伦 - 珊瑚海.mp3");
           // File file = new File(Objects.requireNonNull(getExternalFilesDir(null).getParentFile()) +"周杰伦 - 珊瑚海.mp3");
            mediaPlayer.setDataSource(file.getPath());//指定音频文件的路径
            Log.d("TAG", "initMediaPlayer: "+file.getPath());
            mediaPlayer.prepare();//让MediaPlayer进入到准备状态
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    initMediaPlayer();
                }else {
                    Toast.makeText(this,"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_play:
                if (!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }
                break;
            case R.id.btn_pause:
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                break;
            case R.id.btn_stop:
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.reset();
                    initMediaPlayer();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}