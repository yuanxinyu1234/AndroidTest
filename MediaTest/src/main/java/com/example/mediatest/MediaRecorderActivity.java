package com.example.mediatest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;

import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class MediaRecorderActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaRecorder mMediaRecorder;

    private String filePath;

    private Button btn_start;
    private Button btn_stop;
    private Button btn_start_play;
    private Button btn_stop_play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_recorder);

        btn_start = findViewById(R.id.btn_start);
        btn_stop = findViewById(R.id.btn_stop);
        btn_start_play = findViewById(R.id.btn_start_play);
        btn_stop_play = findViewById(R.id.btn_stop_play);
        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_start_play.setOnClickListener(this);
        btn_stop_play.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(MediaRecorderActivity.this, Manifest.permission.
                WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MediaRecorderActivity.this,new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else {
            startRecord();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
                startRecord();
                break;
            case R.id.btn_stop:
                stopRecord();
                break;
            /*case R.id.btn_start_play:
                stopRecord();
                break;
            case R.id.btn_stop_play:
                stopRecord();
                break;*/
        }
    }

    public void startRecord() {
        // 开始录音
        /* ①Initial：实例化MediaRecorder对象 */
        if (mMediaRecorder == null)
            mMediaRecorder = new MediaRecorder();
        try {
            /* ②setAudioSource/setVedioSource */
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
            /*
             * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式
             * ，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
             */
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样 */
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            String fileName = DateFormat.format("yyyyMMdd_HHmmss", Calendar.getInstance(Locale.CHINA)) + ".m4a";
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC),"");
            String audioSaveDir = file.getPath();
            /*if (!FileUtils.isFolderExist(FileUtils.getFolderName(audioSaveDir))) {
                FileUtils.makeFolders(audioSaveDir);
            }*/
            filePath = audioSaveDir + fileName;
            /* ③准备 */
            mMediaRecorder.setOutputFile(filePath);
            mMediaRecorder.prepare();
            /* ④开始 */
            mMediaRecorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            //LogUtil.i("call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            //LogUtil.i("call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        }
    }

    public void stopRecord() {
        try {
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
            filePath = "";
        } catch (RuntimeException e) {
            e.printStackTrace();
            //LogUtil.e(e.toString());
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;

            File file = new File(filePath);
            if (file.exists())
                file.delete();

            filePath = "";
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startRecord();
                }else {
                    Toast.makeText(this,"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

}