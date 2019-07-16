package com.example.a33206.wechange;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
//导入界面 本界面主要进行权限申请 自动登录 导入界面使用
// 下层 登陆界面 主界面
public class MainActivity extends AppCompatActivity {
    private int Time_Limit=5000 ;
    private Timer time = new Timer();
    private TimerTask timerTask;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //配件设定部分
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Time_Limit--;
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };

        SharedPreferences prefs =PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        //如果状态现实未登录则进入登录界面;
        if (!prefs.getBoolean("status",false)){
            time.schedule(task,2000);
        }

    }

}
