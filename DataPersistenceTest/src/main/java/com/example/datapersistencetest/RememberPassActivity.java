package com.example.datapersistencetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class RememberPassActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText ev_login_username;
    private EditText ev_login_password;
    private Button btn_login;
    private CheckBox cb_rp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember_pass);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        ev_login_username = findViewById(R.id.ev_login_username);
        ev_login_password = findViewById(R.id.ev_login_password);
        btn_login = findViewById(R.id.btn_login);
        cb_rp = findViewById(R.id.cb_rp);

        boolean isRemember = pref.getBoolean("remember_password",false);
        if (isRemember){
            //将账号和密码都设置到文本框中
            String account = pref.getString("account","");
            String password = pref.getString("password","");
            ev_login_username.setText(account);
            ev_login_password.setText(password);
            cb_rp.setChecked(true);
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = ev_login_username.getText().toString();
                String password = ev_login_password.getText().toString();
                if (account.equals("admin") && password.equals("123")){
                    editor = pref.edit();
                    if (cb_rp.isChecked()){
                        editor.putBoolean("remember_password",true);//检查复选框是否被选中
                        editor.putString("account",account);
                        editor.putString("password",password);
                    }else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent = new Intent(RememberPassActivity.this,SharedPreferencesActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(RememberPassActivity.this,"invalid",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}