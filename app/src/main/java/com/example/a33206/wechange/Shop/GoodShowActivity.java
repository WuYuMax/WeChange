package com.example.a33206.wechange.Shop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a33206.wechange.Adapt.GlideImageLoader;
import com.example.a33206.wechange.Adapt.GoodAdapt;
import com.example.a33206.wechange.Adapt.TheGoodAdapt;
import com.example.a33206.wechange.My.MyMessageActivity;
import com.example.a33206.wechange.R;
import com.example.a33206.wechange.db.Goods;
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

public class GoodShowActivity extends AppCompatActivity {
    private RelativeLayout good_use;
    private TextView user_name ;
    private CircleImageView user_pic;
    private TextView user_level;
    private TextView good_number;
    private TextView good_name;
    private TextView good_price;
    private TextView good_know;
    private TextView user_tel;
    private TextView good_likenumber;
    private TextView telButton;
    private TextView buy;
    private RecyclerView recyclerView;
    private GoodAdapt adapt;
    private List<Goods> datalist =new ArrayList<>();
    private ImageView good_banner;
    private ScrollView scrollView;
    private boolean isLike=false;
    private boolean istest=false;
    private String goodId;
    private String buyerId;
    private SwipeRefreshLayout refreshLayout;
    private String useId="null";
    private String orderId;
    private int type;
    private String QQ="null";
    private boolean isbuy=false;
    private String cancealadress="http://140.143.224.210:8080/market/order/cancel?orderid=";
    private String address="http://140.143.224.210:8080/market/product/detail?productid=";
    private String useraddress="http://140.143.224.210:8080/market/user/info?userid=";
    private String goodaddress="http://140.143.224.210:8080/market/app/type?producttype=";
    private String buyaddress1="http://140.143.224.210:8080/market/order/buy?buyerid=";
    private String buyaddress2="&productid=";
    private String buyaddress3="&productamount=";
    private String Buystatus;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodshow);
        user_name =findViewById(R.id.user_name);
        user_pic = findViewById(R.id.user_pic);
        user_level = findViewById(R.id.user_level);
        good_name = findViewById(R.id.show_good_name);
        good_price= findViewById(R.id.show_good_price);
        good_know = findViewById(R.id.show_good_know);
        good_likenumber = findViewById(R.id.show_good_likenumber);
        telButton = findViewById(R.id.like);
        recyclerView =findViewById(R.id.showgood_recycle);
        good_banner =findViewById(R.id.show_good_image);
        scrollView = findViewById(R.id.show_good_scroll);
        good_use = findViewById(R.id.good_user);
        buy=findViewById(R.id.buy);
        user_tel=findViewById(R.id.tel);
        good_number=findViewById(R.id.show_good_number);
        scrollView.scrollTo(0,0);
        refreshLayout = findViewById(R.id.good_show_swip);
        istest=getIntent().getBooleanExtra("test",false);
        initLayout();

        if (!istest) {
            initRecycle();
        }
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapt.FreshHeaderItem();
                initLayout();
                refreshLayout.setRefreshing(false);
            }
        });
    }


    private void initRecycle() {
        StaggeredGridLayoutManager layoutManager =new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapt =new GoodAdapt(getBaseContext(),datalist);
        recyclerView.setAdapter(adapt);
    }

    private void initLayout() {
        Goods goods;
        goods = new Goods();
        SharedPreferences prefs =PreferenceManager.getDefaultSharedPreferences(GoodShowActivity.this);
        buyerId=prefs.getString("userId","1");
        goods= (Goods) getIntent().getSerializableExtra("good");
        goodId=goods.getGood_Id();
        UseIntent();
        final Goods finalGoods = goods;
        final Goods finalGoods1 = goods;
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isbuy){
                    isbuy=true;
                    buy.setText("取消订单");
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(buyaddress1+buyerId+buyaddress2+goodId+buyaddress3+1).build();
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
                                Buystatus =jsonObject.get("msg").toString();
                                orderId=jsonObject.getString("daata");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(GoodShowActivity.this,Buystatus,Toast.LENGTH_LONG).show();
                                    adapt.FreshHeaderItem();
                                    initLayout();
                                }
                            });
                        }
                    });
                }else {
                    buy.setText("给我来一个");
                    isbuy=false;
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(cancealadress+orderId).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String data=response.body().string();
                            try {
                                JSONObject object = new JSONObject(data);
                                Buystatus=object.get("msg").toString();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }

            }
        });
        good_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodShowActivity.this,MyMessageActivity.class);
                intent.putExtra("UserId",useId);
                intent.putExtra("isme",false);
                startActivity(intent);
            }
        });
        telButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLike){
                    Toast.makeText(GoodShowActivity.this,"已加入想要列表",Toast.LENGTH_SHORT).show();
                    if (istest){
                        user_tel.setText(QQ);
                    }
                    telButton.setText("取消");
                    isLike=true;
                }else {
                    Toast.makeText(GoodShowActivity.this,"已退出想要列表",Toast.LENGTH_SHORT).show();
                    telButton.setText("想要");
                    user_tel.setText("（联系方式(qq)）");
                    isLike=false;

                }

            }
        });

        if (!istest) {
            Glide.with(GoodShowActivity.this).load(goods.getPictures()).into(good_banner);
        }else {
            byte[] bytes = goods.getTextPic();
            Glide.with(GoodShowActivity.this).load(bytes).into(good_banner);
        }
    }

    private void UseIntent() {
        OkHttpClient client =new OkHttpClient();
        Request request= new Request.Builder().url(address+goodId).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                try {
                    JSONObject jsonObject1 =new JSONObject(data);
                    final JSONObject object = (JSONObject) jsonObject1.get("data");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                useId=object.get("userId").toString();
                                initMessage();
                                good_name.setText(object.get("productName").toString());
                                good_price.setText("￥"+object.get("productPrice").toString());
                                good_number.setText("库存："+object.get("productStock").toString());
                                Glide.with(GoodShowActivity.this).load(object.get("productIcon")).into(good_banner);
                                good_know.setText(object.get("productDetail").toString());
                                good_likenumber.setText(object.get("productWant").toString()+" 人喜欢");
                                type=object.getInt("productType");
                                initGoodList();
                                Toast.makeText(GoodShowActivity.this,useId,Toast.LENGTH_LONG).show();
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


    private void initMessage(){
        OkHttpClient client1 = new OkHttpClient();
        final Request request1 =new Request.Builder().url(useraddress+useId).build();
        Log.e("--------->",useId);
        client1.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(GoodShowActivity.this,"加载用户信息失败",Toast.LENGTH_LONG).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data1=response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(data1);
                    final JSONObject object1 = (JSONObject) jsonObject.get("data");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                object1.get("userAccount");
                                object1.get("userPassword");
                                user_name.setText(object1.get("userName").toString());
                                QQ=(object1.get("userQq").toString());
                                user_level.setText("成交数："+object1.get("userSuccessAmount").toString());
                                Glide.with(GoodShowActivity.this).load(object1.get("userIcon").toString()).into(user_pic);
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

    private void initGoodList() {
        OkHttpClient client= new OkHttpClient();
        Request request = new Request.Builder().url(goodaddress+type).build();
        Log.e("<-->", ""+type);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = (JSONArray) jsonObject.get("data");
                    for (int i=0;i<jsonArray.length();i++){
                        Goods goods =new Goods();

                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        Log.e("--->" ,jsonObject1.getString("productId"));
                        goods.setGood_Id(jsonObject1.get("productId").toString());
                        goods.setGood_name(jsonObject1.get("productName").toString());
                        goods.setGood_price(jsonObject1.get("productPrice").toString());
                        goods.setNumber(jsonObject1.getInt("productStock"));
                        goods.setPictures(jsonObject1.get("productIcon").toString());
                        jsonObject1.get("productWant");
                        datalist.add(goods);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initRecycle();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
