package com.example.a33206.wechange.My;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.a33206.wechange.Adapt.MyActionAdapt;
import com.example.a33206.wechange.R;
import com.example.a33206.wechange.db.Action;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyActivity extends AppCompatActivity {
    private String address="http://140.143.224.210:8080/market/activity/my?userid=";
    private String userId;
    private RecyclerView jion;
    private RecyclerView set;
    private List<Action> jionList=new ArrayList<>();
    private List<Action> setList=new ArrayList<>();
    private MyActionAdapt jionadapt;
    private MyActionAdapt setadapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity);
        jion=findViewById(R.id.my_action_jion);
        set=findViewById(R.id.my_action_set);
        SharedPreferences prefs =PreferenceManager.getDefaultSharedPreferences(this);
        userId=prefs.getString("userId","1");

        initList();

    }

    private void initList() {
        OkHttpClient client =new OkHttpClient();
        Request request =new Request.Builder().url(address+userId).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data=response.body().string();
                try {
                    JSONObject jsonObject =new JSONObject(data);
                    final JSONArray jsonArray = (JSONArray) jsonObject.get("data");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i=0;i<jsonArray.length();i++){
                                try {
                                    JSONObject object = (JSONObject) jsonArray.get(i);
                                    Action action =new Action();
                                    try {
                                        action.setActivityId(object.getString("activityId"));
                                        action.setActivityName(object.getString("activityName"));
                                        List<String > strings =new ArrayList<>();
                                        strings.add(object.getString("activityIcon"));
                                        action.setActivityIcon(strings);
                                        action.setActivityAddress(object.getString("activityAddress"));
                                        action.setStartTime(object.getString("startTime"));
                                        action.setEndTime(object.getString("endTime"));
                                        action.setActivityNeedPeopleNumber(object.getInt("needPeople"));
                                        action.setActivityJoinPeoleNumber(object.getInt("joinPeople"));
                                        action.setStatus(object.getInt("activityStatus"));
                                        action.setActivityDetail(object.getString("activityDetail"));
                                        String id=object.getString("userId");
                                        action.setUserId(id);

                                        if (userId.equals(id)){
                                            setList.add(action);
                                        }else {
                                            jionList.add(action);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(MyActivity.this,"失败",Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Toast.makeText(MyActivity.this,jionList.size()+""+setList.size(),Toast.LENGTH_LONG).show();
                            setadapter=new MyActionAdapt(setList,MyActivity.this,true);
                            jionadapt=new MyActionAdapt(jionList,MyActivity.this,false);
                            set.setAdapter(setadapter);
                            jion.setAdapter(jionadapt);
                            RecyclerView.LayoutManager layoutManager2=new LinearLayoutManager(MyActivity.this);
                            RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(MyActivity.this);
                            set.setLayoutManager(layoutManager2);
                            jion.setLayoutManager(layoutManager);

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
