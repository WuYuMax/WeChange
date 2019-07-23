package com.example.a33206.wechange.Shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.TextView;

import com.example.a33206.wechange.Adapt.TheGoodAdapt;
import com.example.a33206.wechange.R;
import com.example.a33206.wechange.db.Goods;

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

public class TheGoodListAcitivity extends AppCompatActivity {
    private List<String> namelist =new ArrayList<>();
    private TextView nametext;
    private List<Goods> goodsList=new ArrayList<>();
    private TheGoodAdapt theGoodAdapt;
    private RecyclerView recyclerView;
    private int type;
    private String address="http://www.codeskystar.cn:8080/market/app/type?producttype=";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thegood);
        nametext =findViewById(R.id.the_good_name);
        recyclerView =findViewById(R.id.the_good_recycler);
        type=getIntent().getIntExtra("type",8);
        initNameList();
        initGoodList();

        StaggeredGridLayoutManager layoutManager =new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        nametext.setText(namelist.get(getIntent().getIntExtra("type",8)));
    }

    private void initGoodList() {
        OkHttpClient client= new OkHttpClient();
        Request request = new Request.Builder().url(address+type).build();
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
                        goodsList.add(goods);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            theGoodAdapt= new TheGoodAdapt(TheGoodListAcitivity.this,goodsList);
                            recyclerView.setAdapter(theGoodAdapt);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initNameList() {
        namelist.add("二手手机");
        namelist.add("书籍");
        namelist.add("游戏");
        namelist.add("服饰");
        namelist.add("二手电脑");
        namelist.add("电子配件");
        namelist.add("家具");
        namelist.add("其他");
        namelist.add("商品收藏");
    }
}
