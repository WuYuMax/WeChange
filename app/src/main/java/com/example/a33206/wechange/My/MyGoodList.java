package com.example.a33206.wechange.My;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.example.a33206.wechange.Adapt.MyGoodListAdapt;
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

public class MyGoodList extends AppCompatActivity {
    private RecyclerView buylist;
    private RecyclerView shoplist;
    private String useid;
    private String address="http://140.143.224.210:8080/market/order/detail?userid=";
    private List<Goods> thebuylist=new ArrayList<>();
    private List<Goods> theshoplist=new ArrayList<>();
    private MyGoodListAdapt buyadpat;
    private MyGoodListAdapt shopadat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_goodlist);
        buylist=findViewById(R.id.my_goodlist_buylist);
        shoplist=findViewById(R.id.my_goodlist_shoplist);
        useid=getIntent().getStringExtra("UserId");
        initList();
        RecyclerView.LayoutManager layoutManager2=new LinearLayoutManager(this);
        StaggeredGridLayoutManager layoutManager =new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        shoplist.setLayoutManager(layoutManager);
        buylist.setLayoutManager(layoutManager2);
    }

    private void initList() {
        OkHttpClient client =new OkHttpClient();
        Request request =new Request.Builder().url(address+useid).build();
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
                                    Goods goods =new Goods();
                                    try {
                                        goods.setNumber(object.getInt("productAmount"));
//                                        goods.setGood_Id(object.get("productId").toString());
                                        goods.setCreatTime(object.getString("createTime"));
                                        goods.setStatus(object.getInt("orderStatus"));
//                                        goods.setGood_type(object.getString("orderId"));
                                        goods.setGood_name(object.getString("productName"));
                                        goods.setGood_price(object.getString("productPrice"));
                                        String id=object.getString("buyerId");

                                        String theid=object.getString("sellerId");
                                        if (id.equals(useid)){
                                            Log.e("<---sd->", "  " );
                                            goods.setUser_Id(theid);
                                            thebuylist.add(goods);
                                        }else {
                                            goods.setUser_Id(id);
                                            theshoplist.add(goods);
                                        }
                                         } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(MyGoodList.this,"失败",Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Toast.makeText(MyGoodList.this,thebuylist.size()+""+theshoplist.size(),Toast.LENGTH_LONG).show();

                            buyadpat= new MyGoodListAdapt(thebuylist,MyGoodList.this,true);
                            shopadat =new MyGoodListAdapt(theshoplist,MyGoodList.this,false);
                            buylist.setAdapter(buyadpat);
                            shoplist.setAdapter(shopadat);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
