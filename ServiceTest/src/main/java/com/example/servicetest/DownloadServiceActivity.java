package com.example.servicetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DownloadServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private DownloadService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_service);

        Button btn_start_download = findViewById(R.id.btn_start_download);
        Button btn_pause_download = findViewById(R.id.btn_pause_download);
        Button btn_cancel_download = findViewById(R.id.btn_cancel_download);
        btn_start_download.setOnClickListener(this);
        btn_pause_download.setOnClickListener(this);
        btn_cancel_download.setOnClickListener(this);

        Intent intent = new Intent(this,DownloadService.class);
        startService(intent);//启动服务
        bindService(intent,connection,BIND_AUTO_CREATE);//绑定服务
        if (ContextCompat.checkSelfPermission(DownloadServiceActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DownloadServiceActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    @Override
    public void onClick(View v) {
        if (downloadBinder == null){
            return;
        }
        switch (v.getId()){
            case R.id.btn_start_download:
                String url = "https://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=png&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&hd=undefined&latest=undefined&copyright=undefined&cs=3091388583,377222551&os=1766878774,1267218495&simid=3469084136,328259719&pn=13&rn=1&di=52910&ln=1963&fr=&fmq=1629090376284_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=https%3A%2F%2Fgimg2.baidu.com%2Fimage_search%2Fsrc%3Dhttp%253A%252F%252Fpngimg.com%252Fuploads%252Ftom_and_jerry%252Ftom_and_jerry_PNG16.png%26refer%3Dhttp%253A%252F%252Fpngimg.com%26app%3D2002%26size%3Df9999%2C10000%26q%3Da80%26n%3D0%26g%3D0n%26fmt%3Djpeg%3Fsec%3D1631682375%26t%3D0b34adc91166fa75cd4c4a539437fb05&rpstart=0&rpnum=0&adpicid=0&nojc=undefined&ctd=1629090384511^3_1419X918%1";
                downloadBinder.startDownload(url);
                break;
            case R.id.btn_pause_download:
                downloadBinder.pauseDownload();
                break;
            case R.id.btn_cancel_download:
                downloadBinder.cancelDownload();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.
                        PERMISSION_GRANTED){
                    Toast.makeText(this,"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}