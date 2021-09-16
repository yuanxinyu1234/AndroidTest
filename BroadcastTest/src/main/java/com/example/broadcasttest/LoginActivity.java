package com.example.broadcasttest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends BaseActivity {

    private EditText ev_login_username;
    private EditText ev_login_password;
    private Button btn_login_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ev_login_username = findViewById(R.id.ev_login_username);
        ev_login_password = findViewById(R.id.ev_login_password);
        btn_login_submit = findViewById(R.id.btn_login_submit);
        btn_login_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = ev_login_username.getText().toString();
                String upass = ev_login_password.getText().toString();
                if (uname.equals("yuan") && upass.equals("123")){
                    Intent intent = new Intent(LoginActivity.this,OfflineActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}