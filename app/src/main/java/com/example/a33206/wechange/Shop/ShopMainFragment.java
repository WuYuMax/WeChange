package com.example.a33206.wechange.Shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.a33206.wechange.Adapt.GoodAdapt;
import com.example.a33206.wechange.MainActivity;
import com.example.a33206.wechange.R;
import com.example.a33206.wechange.db.Goods;

import java.util.ArrayList;
import java.util.List;

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
    private static boolean hasMore = false; // 是否有下一页
    private static int currentPage ;
    // 若是上拉加载更多的网络请求 则不需要删除数据
    private boolean isLoadingMore = false;
    // 最后一个条目位置
    private static int lastVisibleItem = 0;

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

        initGoodList();
        StaggeredGridLayoutManager layoutManager =new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        goodAdapt =new GoodAdapt(getActivity(),goodsList);
        recyclerView.setAdapter(goodAdapt);
        LooadingMore();
        return view;
    }

    private void LooadingMore() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!isLoadingMore){
                    if (newState == RecyclerView.SCROLL_STATE_IDLE){

                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
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

    private void initGoodList() {
        for (int i=0;i<4 ; i++){
            Goods app=new Goods();
            List<Integer> list = new ArrayList<>();
            list.add(R.drawable.logo);
            app.setGood_Id("CN_01");
            app.setGood_name("APP ");
            app.setGood_price("298");
            app.setCommit("帅气的APP\n程序ok");
            app.setPictures(list);
            app.setNumber(5);
            goodsList.add(app);
            Goods xxx =new Goods();
            List<Integer> list1= new ArrayList<>();
            list1.add(R.drawable.test);
            list1.add(R.drawable.logo);
            xxx.setPictures(list1);
            xxx.setGood_Id("CN_00");
            xxx.setNumber(0);
            xxx.setGood_price("300");
            xxx.setGood_name("我也不知道是什么");
            xxx.setCommit("电脑的cpu风扇");
            goodsList.add(xxx);
        }
    }
}
