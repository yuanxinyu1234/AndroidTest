package com.example.mediatest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class VideoPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    private VideoView video_view;
    private Button btn_play;
    private Button btn_pause;
    private Button btn_replay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        video_view = findViewById(R.id.video_view);
        btn_play = findViewById(R.id.btn_play);
        btn_pause = findViewById(R.id.btn_pause);
        btn_replay = findViewById(R.id.btn_replay);
        btn_play.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_replay.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(VideoPlayerActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(VideoPlayerActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else {
            initVideoPath();//初始化MediaPlayer
        }
    }

    private void initVideoPath() {
        File file = new File(Environment.getExternalStorageDirectory(),"aa.mp4");
        video_view.setVideoPath(file.getPath());//指定视频文件的路径
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    initVideoPath();
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
                if (!video_view.isPlaying()){
                    video_view.start();
                }
                break;
            case R.id.btn_pause:
                if (video_view.isPlaying()){
                    video_view.pause();
                }
                break;
            case R.id.btn_replay:
                if (video_view.isPlaying()){
                    video_view.resume();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (video_view != null){
            video_view.suspend();
        }
    }
}