package com.example.a33206.wechange.Shop;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a33206.wechange.Adapt.GoodAdapt;
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

@SuppressLint("ValidFragment")
public class ShopGooditemFragment extends Fragment {
    private String address="http://140.143.224.210:8080/market/app/type?producttype=";
    private RecyclerView recyclerView;
    private GoodAdapt adapt;
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<Goods> goodsList= new ArrayList<>();
    private int type;
    @SuppressLint("ValidFragment")
    public ShopGooditemFragment(int position) {
        type = position;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_gooditems,container,false);
        recyclerView = view.findViewById(R.id.show_good_item_recycle);
        swipeRefreshLayout=view.findViewById(R.id.item_swip);
        initGood();
        adapt = new GoodAdapt(getActivity(),goodsList);
        StaggeredGridLayoutManager layoutManager =new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        initintent();
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               new  Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       adapt.FreshHeaderItem();
                       initintent();
                       swipeRefreshLayout.setRefreshing(false);
                   }
               },10);
            }
        });

        return view ;
    }

    private void initGood() {
       initintent();
    }

    private void initintent() {
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
                    if (goodsList.size()!=0){
                        goodsList.clear();
                    }
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

//                            if (!isfresh) {
                            adapt = new GoodAdapt(getActivity(), goodsList);
                            recyclerView.setAdapter(adapt);
//                            }
                            Log.e("====>", goodsList.size()+"" );

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
