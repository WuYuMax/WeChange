package com.example.a33206.wechange.Action;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a33206.wechange.Adapt.ActivityAdapt;
import com.example.a33206.wechange.Adapt.GlideImageLoader;
import com.example.a33206.wechange.R;
import com.example.a33206.wechange.db.Action;
import com.example.a33206.wechange.db.User;
import com.youth.banner.Banner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActionShowActivity extends AppCompatActivity {
    private CircleImageView user_pic;
    private TextView user_name;
    private TextView activity_name;
    private TextView activity_startdata;
    private TextView activity_needpeople;
    private TextView activity_jionpeople;
    private TextView activity_address;
    private TextView activity_qq;
    private TextView activity_detail;
    private TextView activity_enddata;
    private TextView activitylike_button;
    private TextView activity_jion;
    private ImageView activity_pic;
    private Button backbutton;
    private SwipeRefreshLayout refreshLayout;
    private String ActionId;
    private boolean isJion=false;
    private String userIdl;
    private String partcipateId;
    private String address = "http://140.143.224.210:8080/market/activity/detail?activityid=";
    private String jionaddress1 ="http://140.143.224.210:8080/market/activity/join?userid=";
    private String jionaddress2 ="&activityid=";
    private String closeaddress="http://140.143.224.210:8080/market/activity/cancel?participateid=";
    private User user;
    private Action action;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_show);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ActionShowActivity.this);
        user_name = findViewById(R.id.activity_show_user_name);
        user_pic = findViewById(R.id.activity_show_user_pic);
        activity_address = findViewById(R.id.activity_address);
        activity_detail = findViewById(R.id.activity_show_detail);
        activity_enddata = findViewById(R.id.activity_show_enddata);
        activity_jionpeople = findViewById(R.id.activity_show_jion);
        activity_qq = findViewById(R.id.activity_show_tel);
        activity_startdata = findViewById(R.id.activity_push_data);
        activity_name = findViewById(R.id.activity_show_name);
        activity_needpeople = findViewById(R.id.activity_need_number);
        activity_jion = findViewById(R.id.activity_show_jion_button);
        activity_pic = findViewById(R.id.activity_show_pic);
        backbutton = findViewById(R.id.activity_show_back);
        refreshLayout = findViewById(R.id.action_show_swip);
        userIdl = prefs.getString("userId", "0");

        initLayout();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initIntent();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void initLayout() {
        user = new User();
        user = (User) getIntent().getSerializableExtra("user");
        action = (Action) getIntent().getSerializableExtra("activity");
        Log.e("<----活动iD-->", action.getActivityId() );
        initIntent();
        activity_jion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isJion){
                    getjion();
                }else {
                    closeJion();
                }

            }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refreshLayout.setRefreshing(false);

    }

    private void closeJion() {
        activity_jion.setText("果断白给");
        OkHttpClient client =new OkHttpClient();
        Request request = new Request.Builder().url(closeaddress+partcipateId).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) { e.printStackTrace(); }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                try {
                    JSONObject jsonObject =new JSONObject(data);
                    String status =jsonObject.get("msg").toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ActionShowActivity.this,"成功了老弟",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        isJion=false;
    }

    private void getjion() {
        activity_jion.setText("容我三思");
        OkHttpClient client =new OkHttpClient();
        Request request = new Request.Builder().url(jionaddress1+userIdl+jionaddress2+action.getActivityId()).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) { e.printStackTrace(); }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                try {
                    JSONObject jsonObject =new JSONObject(data);
                    JSONObject object = (JSONObject) jsonObject.get("data");
                    partcipateId =object.getString("participateId");
                    object.getString("activityId");
                    object.getString("userId");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("<----ho-->",partcipateId);
                            Toast.makeText(ActionShowActivity.this,"来了老弟",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        isJion=true;
    }

    private void initIntent() {
        OkHttpClient client =new OkHttpClient();
        Request request =new Request.Builder().url(address+action.getActivityId()).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) { e.printStackTrace(); }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                try {
                    JSONObject jsonObject=new JSONObject(data);
                    final JSONObject jsonObject1 = (JSONObject) jsonObject.get("data");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ActionId=jsonObject1.getString("activityId");
                                user_name.setText(jsonObject1.getString("userName"));
                                Glide.with(ActionShowActivity.this).load(jsonObject1.getString("userIcon")).into(user_pic);
                                activity_address.setText(jsonObject1.getString("activityAddress"));
                                activity_detail.setText(jsonObject1.getString("activityDetail"));
                                activity_enddata.setText(jsonObject1.getString("endTime"));
                                activity_startdata.setText(jsonObject1.getString("startTime"));
                                activity_jionpeople.setText(jsonObject1.getInt("joinPeople")+"");
                                activity_needpeople.setText(jsonObject1.getInt("needPeople")+"");
                                Glide.with(ActionShowActivity.this).load(jsonObject1.getString("activityIcon")).into(activity_pic);
                                activity_name.setText(jsonObject1.getString("activityName"));
                                activity_qq.setText(jsonObject1.getString("userQq"));
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
    }
}

