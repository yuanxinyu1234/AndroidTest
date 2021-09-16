package com.example.servicetest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class DownloadTask extends AsyncTask<Void,Integer,Boolean> {

    ProgressDialog progressDialog;
    Context context;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try{
            while (true){
                int downloadPercent = doDownload();//这是一个虚构的方法
                publishProgress(downloadPercent);
                if (downloadPercent >= 100){
                    break;
                }
            }
        }catch (Exception e){
            return false;
        }
        return null;
    }

    private int doDownload() {
        return 80;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        //在这里更新下载进度
        progressDialog.setMessage("Download" + values[0] + "%");
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        progressDialog.dismiss();//关闭进度对话框
        if (aBoolean){
            Toast.makeText(context,"Download success",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"Download failed",Toast.LENGTH_SHORT).show();
        }
    }
}
