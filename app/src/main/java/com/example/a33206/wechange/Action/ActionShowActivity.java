package com.example.a33206.wechange.Action;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a33206.wechange.Adapt.GlideImageLoader;
import com.example.a33206.wechange.R;
import com.example.a33206.wechange.db.Action;
import com.example.a33206.wechange.db.User;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

public class ActionShowActivity extends AppCompatActivity {
    private ImageView user_pic;
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

    private User user;
    private Action action;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_show);
        user_name= findViewById(R.id.activity_show_user_name);
        user_pic= findViewById(R.id.activity_show_user_pic);
        activity_address= findViewById(R.id.activity_address);
        activity_detail= findViewById(R.id.activity_show_detail);
        activity_enddata= findViewById(R.id.activity_show_enddata);
        activity_jionpeople= findViewById(R.id.activity_show_jion);
        activity_qq= findViewById(R.id.activity_show_tel);
        activity_startdata= findViewById(R.id.activity_push_data);
        activity_name= findViewById(R.id.activity_show_name);
        activity_needpeople= findViewById(R.id.activity_need_number);
        activitylike_button= findViewById(R.id.activity_show_like_button);
        activity_jion= findViewById(R.id.activity_show_jion_button);
        activity_pic= findViewById(R.id.activity_show_pic);
        backbutton=findViewById(R.id.activity_show_back);
        refreshLayout=findViewById(R.id.action_show_swip);
        initLayout();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initLayout();
            }
        });
    }

    private void initLayout() {
        user = new User();
        user = (User) getIntent().getSerializableExtra("user");
        action = (Action) getIntent().getSerializableExtra("activity");
        user_name.setText(user.getUserName());
        Glide.with(ActionShowActivity.this).load(R.drawable.logo).into(user_pic);
        activity_name.setText(action.getActivityName());
        activity_startdata.setText(action.getStartTime());
        activity_enddata.setText(action.getEndTime());
        activity_jionpeople.setText(""+action.getActivityJoinPeoleNumber());
        activity_needpeople.setText(""+action.getActivityNeedPeopleNumber());
        activity_detail.setText(action.getActivityDetail());
        activity_address.setText(action.getActivityAddress());
        activity_qq.setText(user.getQQ());
        List<Integer> imagelist = new ArrayList<>();
        imagelist.add(R.drawable.logo);
        Glide.with(ActionShowActivity.this).load(action.getTextPic()).into(activity_pic);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refreshLayout.setRefreshing(false);
    }
}
