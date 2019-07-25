package com.example.a33206.wechange.Shop;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.Toast;

import com.example.a33206.wechange.Adapt.GoodAdapt;
import com.example.a33206.wechange.MainActivity;
import com.example.a33206.wechange.R;
import com.example.a33206.wechange.Util.HttpUtil;
import com.example.a33206.wechange.Util.Utility;
import com.example.a33206.wechange.db.Goods;
import com.google.gson.Gson;

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
import okhttp3.internal.Util;

public class ShopMainFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Goods> goodsList = new ArrayList<>();
    private GoodAdapt goodAdapt;
    private Button phonebutton;
    private Button bookbutton;
    private Button gamebutton;
    private Button clothbutton;
    private Button computerbutton;
    private Button peijianbutton;
    private Button jiajvbutton;
    private Button otherbutton;
    private String responseDate;
    private String loadaddress="http://140.143.224.210:8080/market/app/home";
    private static boolean hasMore = false; // 是否有下一页
    private static int currentPage ;
    private SwipeRefreshLayout swipeRefreshLayout;
    // 若是上拉加载更多的网络请求 则不需要删除数据
    private boolean isLoadingMore = false;
    // 最后一个条目位置
    private static int lastVisibleItem = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_main,container,false);
        phonebutton=view.findViewById(R.id.phone_button);
        bookbutton=view.findViewById(R.id.book_button);
        gamebutton=view.findViewById(R.id.game_button);
        clothbutton=view.findViewById(R.id.cloth_button);
        computerbutton=view.findViewById(R.id.computer_button);
        peijianbutton=view.findViewById(R.id.peijian_button);
        jiajvbutton=view.findViewById(R.id.jiajv_button);
        otherbutton=view.findViewById(R.id.other_button);
        recyclerView=view.findViewById(R.id.shopfragment_main_recyle);
        swipeRefreshLayout=view.findViewById(R.id.shop_main_swip);
        GoodAdapt adapt = new GoodAdapt(getActivity(),goodsList);

        recyclerView.setAdapter(adapt);
        if (goodsList.size()==0){
            initGoodList(loadaddress,true);
        }

        StaggeredGridLayoutManager layoutManager =new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.canScrollVertically(1)){
                    initGoodList(loadaddress,false);
                }
            }
        });
        initSwip();
        return view;
    }

    private void initSwip() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                initGoodList(loadaddress,true);
                Log.e("-------------->", goodsList.size()+"  " );
            }
        });

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initButton(phonebutton,0);
        initButton(bookbutton,1);
        initButton(gamebutton,2);
        initButton(clothbutton,3);
        initButton(computerbutton,4);
        initButton(peijianbutton,5);
        initButton(jiajvbutton,6);
        initButton(otherbutton,7);
    }

    private void initButton(Button button, final int i) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),TheGoodListAcitivity.class);
                intent.putExtra("type",i);
                startActivity(intent);
            }
        });
    }

    private void initGoodList(String address,final boolean isFresh) {


        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText=response.body().string();
                try {
                    if (isFresh) {
                        goodsList.clear();
                    }else{
                        Log.e("--------------->>", goodsList.size()+"" );
                    }

                    JSONObject jsonObject =new JSONObject(responseText);
                 JSONArray jsonArray =jsonObject.getJSONArray("data");
                 Log.e("--------------->>", jsonArray.length()+"" );
                    for (int i=0;i<jsonArray.length();i++){
                    JSONObject content= (JSONObject) jsonArray.get(i);
                    Goods good = new Goods();
                    good.setGood_Id(content.get("productId").toString());
                    good.setGood_name(content.get("productName").toString());
                    good.setGood_price(content.get("productPrice").toString());
                    good.setNumber(content.getInt("productStock"));
                    good.setPictures(content.get("productIcon").toString());
                    good.setLikenumber(content.getInt("productWant"));
                    goodsList.add(good);
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        goodAdapt =new GoodAdapt(getActivity(),goodsList);
                        recyclerView.setAdapter(goodAdapt);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
