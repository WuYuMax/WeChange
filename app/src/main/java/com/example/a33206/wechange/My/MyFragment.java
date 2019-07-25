package com.example.a33206.wechange.My;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a33206.wechange.LoginActivity;
import com.example.a33206.wechange.R;
import com.example.a33206.wechange.Shop.TheGoodListAcitivity;
import com.example.a33206.wechange.Util.HttpUtil;
import com.example.a33206.wechange.Util.Utility;
import com.example.a33206.wechange.db.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyFragment extends Fragment {
    private TextView user_name;
    private Button degister_button;
    private TextView my_change;
    private TextView my_collect;
    private TextView my_message;
    private TextView my_name;
    private TextView my_action;
    private CircleImageView circleImageView;
    private String address="http://140.143.224.210:8080/market/user/my?userid=";
    private String logoutaddress="http://140.143.224.210:8080/market/user/logout";
    private String userId;
    private String data;
    private String Myname;
    private String Mypic="null";
    User user = new User();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_my,container,false);

        user_name=view.findViewById(R.id.my_name);
        degister_button=view.findViewById(R.id.my_degister_button);
        my_collect=view.findViewById(R.id.my_collect);
        my_message=view.findViewById(R.id.my_message);
        my_change=view.findViewById(R.id.my_change);
        my_name=view.findViewById(R.id.my_name);
        my_action=view.findViewById(R.id.my_action);
        circleImageView = view.findViewById(R.id.my_user_pic);
        initLayout();

        if (Mypic=="null"){
            getByIntent();
        }
        else{
            Glide.with(getActivity()).load(Mypic).into(circleImageView);
            my_name.setText(Myname);
        }

//        Toast.makeText(getActivity(),Mypic.toString(),Toast.LENGTH_LONG).show();
        return view;
    }

    private void initLayout() {
         SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (!prefs.getBoolean("status",false)){
            user_name.setText("请优先登录您的账号");
            degister_button.setText("登录");
            degister_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }
            });

            }else {
            userId=prefs.getString("userId","0");
            my_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(getActivity(),MyActivity.class);
                    startActivity(intent);
                }
            });
            my_change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(getActivity(),MyGoodList.class);
                    intent.putExtra("UserId",userId);
                    startActivity(intent);
                }
            });
            my_message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),MyMessageActivity.class);
                    intent.putExtra("UserId",userId);
                    intent.putExtra("isme",true);
                    startActivity(intent);
                }
            });
            circleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),MyMessageActivity.class);
                    intent.putExtra("UserId",userId);
                    intent.putExtra("isme",true);
                    startActivity(intent);

                }
            });
            my_collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentToTarget(getActivity(),TheGoodListAcitivity.class,8);
                }
            });
            degister_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor =PreferenceManager.getDefaultSharedPreferences(getActivity())
                            .edit();
                    editor.putBoolean("status",false);
                    editor.apply();
                    user_name.setText("请优先登录您的账号");
                    Glide.with(getActivity()).load(R.drawable.logo).into(circleImageView);
                    degister_button.setText("登录");
                    Mypic=null;

                    degister_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(),LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    });
                    OkHttpClient client =new OkHttpClient();
                    Request request = new Request.Builder().url(logoutaddress).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(),"您已安全注销",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            });
        }

    }

    private void getByIntent() {
        address=address+userId;
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                data=response.body().string();
                try {
                    JSONObject jsonObject =new JSONObject(data);
                    final JSONObject jsonObject1 = (JSONObject) jsonObject.get("data");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            Log.e("----------------->",user.getUserName().toString() );
                            try {
                                Myname="您好！"+jsonObject1.get("userName").toString();
                                Log.e("-------------------->",Myname);
                                my_name.setText(Myname);
                                Mypic=jsonObject1.getString("userIcon");
                                Log.e("-------------------->",jsonObject1.get("userIcon").toString());
                                Glide.with(getActivity()).load(Mypic.getBytes()).into(circleImageView);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });//[B@5ef3a37
                    //[B@5ef3a37
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Mypic=null;
        Myname="sdf";
    }

    private void IntentToTarget(FragmentActivity activity, Class<TheGoodListAcitivity> theGoodListAcitivityClass, int i){
        Intent intent = new Intent(activity,theGoodListAcitivityClass);
        intent.putExtra("type",i);
        startActivity(intent);
    }
}
