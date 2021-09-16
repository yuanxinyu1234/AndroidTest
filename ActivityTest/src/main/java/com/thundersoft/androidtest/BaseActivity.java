package com.thundersoft.androidtest;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    /**
     * 知晓当前实在哪个活动
     * @param savedInstanceState
     * BaseActivity成为项目中所有活动的父类，所有活动继承BaseActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity", getClass().getSimpleName());
    }
}
