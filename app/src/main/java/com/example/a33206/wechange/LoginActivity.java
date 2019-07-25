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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.example.a33206.wechange.Util.HttpUtil;
import com.example.a33206.wechange.Util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    private String status;
    private String userId;
    private String addressone="http://140.143.224.210:8080/market/user/login?useraccount=";
    private  String addressother ="&userpassword=";
    private String address;
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
                final String name = name_text.getText().toString();
                final String password = password_text.getText().toString();
                address=addressone+name+addressother+password;
                HttpUtil.sendOkHttpRequest(address, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("-------------->","problem" );
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String Data = response.body().string();
                        try {
                            JSONObject  object= new JSONObject(Data);
                            status=object.get("msg").toString();
                            userId=object.get("data").toString();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (status.equals("成功")){
                                        Intent intent = new Intent(LoginActivity.this, WorkActivity.class);
                                        intent.putExtra("userId",userId);
                                        startActivity(intent);
                                        SharedPreferences.Editor editor = PreferenceManager
                                                .getDefaultSharedPreferences(LoginActivity.this)
                                                .edit();
                                        editor.putBoolean("status", true);
                                        editor.putString("userId",userId);
                                        editor.apply();
                                        finish();
                                    }else{
                                        Log.e("--------------->", "555");
                                        Toast.makeText(LoginActivity.this,status,Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

//                }
//                }
            }
        });
    }
}
