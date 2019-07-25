package com.example.a33206.wechange.Action;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a33206.wechange.Adapt.ActivityAdapt;
import com.example.a33206.wechange.R;
import com.example.a33206.wechange.Shop.GoodShowActivity;
import com.example.a33206.wechange.db.Action;
import com.example.a33206.wechange.db.User;

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

public class ActionFragment extends Fragment {
    private RecyclerView recyclerView;
    private Button addbutton;
    private ActivityAdapt activityAdapt;
    private List<User> userList=new ArrayList<>();
    private List<Action> actionList= new ArrayList<>();
    private Button drawerbutton;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String useId;
    private String nowId;
    private User user=new User();
    private List<String> userIdlist=new ArrayList<>();
    private String Actionaddress="http://140.143.224.210:8080/market/activity/home";
    private String useraddress="http://140.143.224.210:8080/market/user/info?userid=";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_action,container,false);
        recyclerView=view.findViewById(R.id.action_reyecler);
        addbutton = view.findViewById(R.id.action_Add);
        drawerbutton=view.findViewById(R.id.drawer_button);
        swipeRefreshLayout=view.findViewById(R.id.action_swip);
        initList();
        Toast.makeText(getActivity(),userList.size()+""+actionList.size(),Toast.LENGTH_LONG).show();
//        activityAdapt=new ActivityAdapt(userList,actionList,getActivity(),R.drawable.logo);
        StaggeredGridLayoutManager layoutManager =new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        activityAdapt.FreshHeaderItem();
                    }
                },30);
                initList();
            }
        });

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(getActivity());
                nowId=prefs.getString("userId","null");
                Intent intent = new Intent(getActivity(),ActionReleaseActivity.class);
                intent.putExtra("userId",nowId);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void initList() {
        if (userIdlist.size()!=0){
            userIdlist.clear();
            userList.clear();
            actionList.clear();
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Actionaddress).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) { e.printStackTrace(); }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    final JSONArray jsonArray = (JSONArray) jsonObject.get("data");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                for(int i=0;i<jsonArray.length();i++){
                                    try {
                                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                                        Action action =new Action();
                                        User user =new User();
                                        useId=jsonObject1.get("userId").toString();
                                        userIdlist.add(useId);
                                        action.setActivityId(jsonObject1.getString("activityId"));
                                        action.setActivityName(jsonObject1.getString("activityName"));
                                        List<String> url = new ArrayList<>();
                                        url.add(jsonObject1.getString("activityIcon"));
                                        action.setActivityIcon(url);
                                        action.setActivityAddress(jsonObject1.getString("activityAddress"));
                                        action.setStartTime(jsonObject1.getString("startTime"));
                                        action.setEndTime(jsonObject1.getString("endTime"));
                                        action.setActivityNeedPeopleNumber(jsonObject1.getInt("needPeople"));
                                        action.setActivityJoinPeoleNumber(jsonObject1.getInt("joinPeople"));
                                        action.setActivityDetail(jsonObject1.getString("activityDetail"));
                                        user.setUseIconUrl(jsonObject1.get("userIcon").toString());
                                        user.setQQ(jsonObject1.getString("userQq").toString());
                                        user.setUserName(jsonObject1.get("userName").toString());
                                        userList.add(user);
                                        actionList.add(action);
                                        Log.e("<------对比----------->", userIdlist.size()+"" );
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getActivity(),"接受失败",Toast.LENGTH_LONG).show();
                                    }
                                    activityAdapt = new ActivityAdapt(userList, actionList,getActivity(),R.drawable.logo);
                                    recyclerView.setAdapter(activityAdapt);
                                    swipeRefreshLayout.setRefreshing(false);
                            }
//                            userList.clear();
//                            for (int i=0;i<userIdlist.size();i++) {
//
//                                initMessage(userIdlist.get(i));
//                            }
                        }

                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
//        User user = new User();
//        user.setUserName("帅逼");
//        user.setUserId("2019-7-20");
////        user.setUseIconUrl();
//
//        Action action = new Action();
//        action.setActivityName("学生会部长换届");
//        action.setActivityDetail("1、选拔结束后将最终确定的名单进行公示，公示期不少于三个工作日，期间对名单有任何异议，可以进行投诉（投诉电话：18571805459，投诉邮箱：2274019789@qq.com）。\n" +
//                "2、遵循考察任用制度：选拔结束后由院内老师及主席团成员对拟任用人选进行考察，考察期至同年九月底，如合格则正式录用，如不合格则另行安排。\n" +
//                "3、完成工作交接：上届部长级成员传授工作中的经验教训及工作方法并在实际工作开展中给予新一届部长级成员支持与帮助。\n" +
//                "4、笔试主要测试报名同学对竞职岗位和学生组织章程的了解程度、统筹能力、细节思考能力等。\n" +
//                "5、答辩主要体现竞选同学对于竞选职位的理解以及工作规划，同时还考验竞选同学的语言表达能力和应变能力。演讲内容应至少包括自我介绍、职务理解、个人工作陈述等方面，评委将主要从演讲态度、演讲内容、演讲能力和问题答辩情况这四个方面进行打分。");
//        action.setActivityNeedPeopleNumber(10);
//        action.setActivityJoinPeoleNumber(9);
//        action.setStartTime("2019-3-11");
//        action.setEndTime("3-15");
//        for (int i=0;i<4;i++) {
//            userList.add(user);
//            actionList.add(action);
//        }




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    private void initMessage(String useIdl){

        OkHttpClient client = new OkHttpClient();
        Request request =new Request.Builder().url(useraddress+useIdl).build();
        Log.e("<------fan比----------->",useIdl);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String date = response.body().string();
                try {
                    final JSONObject object = new JSONObject(date);
                    final JSONObject object1 = (JSONObject) object.get("data");
                    User user = new User();
                    user.setUserName(object1.get("userName").toString());
                    Log.e("<------反比----------->",user.getUserName());
                    user.setUseraccount(object1.get("userAccount").toString());
                    user.setPhone(object1.get("userPhone").toString());
                    user.setUseIconUrl(object1.get("userIcon").toString());
                    user.setSuccessAmount(object1.getInt("userSuccessAmount"));
                    user.setQQ(object1.get("userQq").toString());
                    user.setPassword(object1.get("userPassword").toString());
                    userList.add(user);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        });
    }
}
