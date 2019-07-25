package com.example.a33206.wechange;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a33206.wechange.Util.HttpUtil;
import com.example.a33206.wechange.Util.Utility;
import com.example.a33206.wechange.db.User;

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

public class RegisterActivity extends AppCompatActivity {
    //注册界面 上层：登陆界面 下层：主界面
    private EditText name;
    private EditText user_name;
    private EditText password;
    private EditText re_password;
    private EditText phone;
    private EditText qq;
    private Button register_button;
    private Button back_button;

    private String address1="http://140.143.224.210:8080/market/user/register";
    private String address2="&username=";
    private String address3="&userpassword=";
    private String address4="&userphone=";
    private String address5="&userqq=";
    private String address;
    private User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.pre_name);
        user_name=findViewById(R.id.pre_username);
        password = findViewById(R.id.pre_password);
        re_password = findViewById(R.id.pre_repassword);
        phone = findViewById(R.id.pre_phone);
        qq = findViewById(R.id.pre_qq);
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
                String pre_phone =phone .getText().toString();
                String pre_qq = qq.getText().toString();
                String pre_username=name.getText().toString();
                address =address1+pre_name+address2+user_name+address3+pre_password+address4+pre_phone+address5+pre_qq;

                if (pre_password.toString().equals(pre_repassword.toString())){
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("userAccount",pre_name)
                            .add("userName",pre_username)
                            .add("userPassword",pre_password)
                            .add("userPhone",pre_phone)
                            .add("userQq",pre_qq)
                            .build();
                    final Request request = new Request.Builder().url("http://140.143.224.210:8080/market/user/register")
                            .post(requestBody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String data =response.body().string();
                            try {
                                final JSONObject jsonObject = new JSONObject(data);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Log.e("===========>", jsonObject.getString("msg") );
                                            Log.e("+++++++++++++",jsonObject.getString("data"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
//
//
// HttpUtil.sendOkHttpRequest(address, new Callback() {
//@Override
//public void onFailure(Call call, IOException e) {
//        e.printStackTrace();
//        }
//
//@Override
//public void onResponse(Call call, Response response) throws IOException {
//        String data = response.body().string();
//        try {
//        user=new User();
//        user = Utility.handleUserReponse(data,"data");
//        runOnUiThread(new Runnable() {
//@Override
//public void run() {
//        Toast.makeText(RegisterActivity.this,user.getUserName(),Toast.LENGTH_LONG).show();
//        }
//        });
//        } catch (JSONException e) {
//        e.printStackTrace();
//        }
//        }
//        });