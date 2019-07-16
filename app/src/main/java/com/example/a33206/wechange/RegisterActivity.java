package com.example.a33206.wechange;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    //注册界面 上层：登陆界面 下层：主界面
    private EditText name;
    private EditText password;
    private EditText re_password;
    private EditText email;
    private Button register_button;
    private Button back_button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.pre_name);
        password = findViewById(R.id.pre_password);
        re_password = findViewById(R.id.pre_repassword);
        email = findViewById(R.id.pre_email);
        register_button = findViewById(R.id.register_button);
        back_button = findViewById(R.id.back_button);
    }

    @Override
    protected void onStart() {
        super.onStart();
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pre_name =name.getText().toString();
                String pre_password=password.getText().toString();
                String pre_repassword=re_password.getText().toString();
                String pre_emal = email.getText().toString();
                if (pre_password.toString().equals(pre_repassword.toString())){
                    Intent intent = new Intent(RegisterActivity.this,WorkActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
