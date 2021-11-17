package com.example.webtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ParseJsonWithJsonObjectActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tv_response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_json_whith_json_object);

        Button btn_send_request = findViewById(R.id.btn_send_request);
        tv_response = findViewById(R.id.tv_response);
        btn_send_request.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_send_request){
            sendRequestWithOkHttp();
        }
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        //指定访问的服务器地址是电脑本机
                        .url("http://10.0.2.2/get_data.xml")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJsonWithJsonObject(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void parseJsonWithJsonObject(String jsonData){
        try{
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id =jsonObject.getString("id");
                String name =jsonObject.getString("name");
                String version =jsonObject.getString("version");
                Log.d("yuan", "id: " + id);
                Log.d("yuan", "name: " + name);
                Log.d("yuan", "version: " + version);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}