package com.thundersoft.androidtest;


import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    //向List中添加一个活动
    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    //从List中移出一个活动
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    //移出所有活动
    public static void finishAll(){
        for (Activity activity:activities){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
