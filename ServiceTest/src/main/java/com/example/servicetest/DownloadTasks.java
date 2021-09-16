package com.example.servicetest;

import android.os.AsyncTask;
import android.os.Environment;

import com.example.servicetest.interfaces.DownloadListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * String：执行AsyncTask时需要传入一个字符串参数给后台任务
 * Integer：使用整型数据来作为显示单位
 * Integer：使用整型数据来反馈执行结果
 */
public class DownloadTasks extends AsyncTask<String,Integer,Integer> {

    public static final int TYPE_SUCCESS = 0;//下载成功
    public static final int TYPE_FAILED = 1;//下载失败
    public static final int TYPE_PAUSED = 2;//下载暂停
    public static final int TYPE_CANCELED = 3;//下载取消

    private DownloadListener listener;

    private boolean isCanceled = false;
    private boolean isPaused = false;
    private int lastProgress;

    public DownloadTasks(DownloadListener listener){
        this.listener = listener;
    }

    //在后台执行具体的下载逻辑
    @Override
    protected Integer doInBackground(String... strings) {
        InputStream is = null;
        RandomAccessFile savedFile = null;
        File file = null;
        try{
            long downloadLength = 0;//记录已下载的文件长度
            String downloadUrl = strings[0];//获取下载地址
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            String directory = Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DOWNLOADS).getPath();//SD卡Download目录下
            file = new File(directory + fileName);
            if (file.exists()){
                downloadLength = file.length();
            }
            long contentLength = getContentLength(downloadUrl);//获取待下载文件字节数
            if (contentLength == 0){
                return TYPE_FAILED;
            }else if (contentLength == downloadLength){
                //已下载字节和文件总字节相等，说明已经下载完成了
                return TYPE_SUCCESS;
            }
            OkHttpClient client = new OkHttpClient();//使用OkHttp发送一条网络请求
            Request request = new Request.Builder()
                    .addHeader("RANGE","bytes="+downloadLength+"-")//告诉服务器从哪个字节开始下载
                    .url(downloadUrl)
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null){
                is = response.body().byteStream();
                savedFile = new RandomAccessFile(file,"rw");
                savedFile.seek(downloadLength);//跳过已下载的字节
                byte[] b = new byte[1024];
                int total = 0;
                int len;
                while ((len = is.read(b)) != -1){
                    if (isCanceled){
                        return TYPE_PAUSED;
                    }else if (isPaused){
                        return TYPE_PAUSED;
                    }else {
                        total += len;
                        savedFile.write(b,0,len);
                        //计算已下载的百分比
                        int progress =(int) ((total +downloadLength) * 100 /
                                contentLength);
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if (is != null){
                    is.close();
                }
                if (savedFile != null){
                    savedFile.close();
                }
                if (isCanceled && file != null){
                    file.delete();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    //在界面上更新当前的下载进度
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int progress = values[0];
        if (progress > lastProgress){
            listener.onProgress(progress);
            lastProgress = progress;
        }
    }

    //通知最终的下载结果
    @Override
    protected void onPostExecute(Integer status) {
        super.onPostExecute(status);
        switch (status){
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_PAUSED:
                listener.onPaused();
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
            default:
                break;
        }
    }

    public void pasueDownload(){//暂停下载
        isPaused = true;
    }

    public void cancelDownload(){//取消下载
        isCanceled = true;
    }

    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                                    .url(downloadUrl)
                                    .build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()){
            long contentLength = response.body().contentLength();
            response.close();
            return contentLength;
        }
        return 0;
    }
}
