package com.example.broadcasttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OfflineActivity extends BaseActivity {

    private Button btn_force_offline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);

        btn_force_offline = findViewById(R.id.btn_force_offline);
        btn_force_offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.broadcasttest.FORCE_OFFLINE");
                sendBroadcast(intent);
            }
        });
    }
}