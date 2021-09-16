package com.thundersoft.androidtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class IntentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        Button btn_implicit = findViewById(R.id.btn_implicit);
        btn_implicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐式intent（不好用）
                /*Intent intent = new Intent("com.thundersoft.androidtest.IMPLICIT");
                intent.addCategory("com.thundersoft.androidtest.MY_CATEGORY");
                startActivity(intent);*/

                //Uri.parse() 将一个网址字符串解析成一个Uri对象
                /*Intent intent = new Intent();
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);*/

                //指定Intent的action是Intent.ACTION_DIAL
                /*Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:10086"));
                startActivity(intent);*/

                Intent intent = new Intent(IntentActivity.this, IntentReturnActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    /**
     *
     * @param requestCode  1
     * @param resultCode  RESULT_OK或RESULT_CANCELED
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                //接收上一个活动发来的数据
                if (resultCode == RESULT_OK){
                    String returnData = data.getStringExtra("data_return");
                    Log.d("TAG", "onActivityResult: "+returnData);
                }
                break;
            default:
        }
    }
}