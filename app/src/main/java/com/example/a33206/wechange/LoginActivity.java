package com.example.a33206.wechange;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
//登陆界面 上层：导入界面 下层 主界面 注册界面
public class LoginActivity extends AppCompatActivity {
    //声明
    private Button login_button;
    private EditText name_text;
    private EditText password_text;
    private CheckBox remind;
    private CheckBox look;
    private TextView register_text;
    private TextView text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //加载
        setContentView(R.layout.activity_login);
        login_button = findViewById(R.id.Login_button);
        name_text = findViewById(R.id.name);
        remind = findViewById(R.id.remind_password);
        look = findViewById(R.id.look_password);
        password_text = findViewById(R.id.password);
        register_text = findViewById(R.id.register);
//      状态初始化
        password_text.setTransformationMethod(PasswordTransformationMethod.getInstance());
//      显示/隐藏密码
        look.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    password_text.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    password_text.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

//        跳转
        register_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name_text.getText().toString();
                String password = password_text.getText().toString();
//                if(name_text.equals("admin")&&password.equals("38")){
                    Intent intent = new Intent(LoginActivity.this,WorkActivity.class);
                    startActivity(intent);
//                    SharedPreferences.Editor editor= PreferenceManager
//                            .getDefaultSharedPreferences(LoginActivity.this)
//                            .edit();
//                    editor.putBoolean("status",true);
//                    editor.apply();
//                    finish();
//                }
            }
        });
    }
}
