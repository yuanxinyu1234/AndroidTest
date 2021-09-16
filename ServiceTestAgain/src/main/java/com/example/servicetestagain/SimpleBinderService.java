package com.example.servicetestagain;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class SimpleBinderService extends Service {

    private LocalBinder binder = new LocalBinder();
    private Thread thread;
    private int count;
    private boolean quit;

    /**
     * 创建Binder对象，返回给客户端即Activity使用，提供数据交换的接口
     */
    public class LocalBinder extends Binder{
        //声明一个方法（提供给客户端使用）
        SimpleBinderService getService(){
            //返回当前对象SimpleBinderService，这样就可以在客户端调用Service的公共方法
            return SimpleBinderService.this;
        }
    }

    public SimpleBinderService() {
    }

    /**
     * 绑定服务时被调用
     * 必须要实现的方法
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        //把Binder类返回给客户端
        return binder;
    }

    /**
     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用onBind（）或onStartCommand（）之前）
     * 如果服务已在运行，则不会调用此方法。该方法只被调用一次
     */
    @Override
    public void onCreate() {
        super.onCreate();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //每隔一秒count+1，直到quit为true
                while (!quit){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                }
            }
        });
        thread.start();
    }

    /**
     * 公共方法
     * @return
     */
    public int getCount(){
        return count;
    }

    /**
     * 每次通过startService()方法启动Service时都会被回调
     * @param intent 启动时，启动组件传递过来的Intent
     * @param flags 启动请求时是否有额外数据，可选值
     *              0----没有
     *              START_FLAG_REDELIVERY----onStartCommand方法的返回值为START_REDELIVER_INTENT，而且在上一次服务被杀死前会去调用stopSelf方法停止服务。
     *              START_FLAG_RETRY----当onStartCommand调用后一直没有返回值时，会尝试重新去调用onStartCommand()。
     * @param startId  指明当前服务的唯一ID，与stopSelfResult (int startId)配合使用
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 解除绑定时调用
     * @param intent
     * @return
     */
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    /**
     * 服务销毁时的回调
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.quit = true;
    }
}