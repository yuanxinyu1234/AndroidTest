package com.thundersoft.uitest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class WidgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        Button btn_pb = findViewById(R.id.btn_pb);
        Button btn_pd = findViewById(R.id.btn_pd);
        ProgressBar pb_bar = findViewById(R.id.pb_bar);

        //ProgressBar进度条
        btn_pb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_pb:
                        int progress = pb_bar.getProgress();
                        progress += 10;
                        pb_bar.setProgress(progress);
                        break;
                    default:
                        break;
                }
            }
        });

        //ProgressDialog
        btn_pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_pd:
                        ProgressDialog progressDialog = new ProgressDialog(WidgetActivity.this);
                        progressDialog.setTitle("progressDialog");
                        progressDialog.setTitle("Loading......");
                        progressDialog.setCancelable(true);
                        progressDialog.show();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}