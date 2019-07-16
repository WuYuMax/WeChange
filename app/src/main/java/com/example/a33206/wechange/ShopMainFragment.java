package com.example.a33206.wechange;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a33206.wechange.Adapt.GoodAdapt;
import com.example.a33206.wechange.db.Goods;

import java.util.ArrayList;
import java.util.List;

public class ShopMainFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Goods> goodsList = new ArrayList<>();
    private GoodAdapt goodAdapt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_main,container,false);
        recyclerView=view.findViewById(R.id.shopfragment_main_recyle);
        initGoodList();
        StaggeredGridLayoutManager layoutManager =new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        goodAdapt =new GoodAdapt(getActivity(),goodsList);
        recyclerView.setAdapter(goodAdapt);
        return view;
    }

    private void initGoodList() {
        for (int i=0;i<4 ; i++){
            Goods app=new Goods();
            List<Integer> list = new ArrayList<>();
            list.add(R.drawable.logo);
            app.setGood_Id("CN_01");
            app.setGood_name("APP ");
            app.setGood_price("298");
            app.setPictures(list);
            app.setNumber(5);
            goodsList.add(app);
            Goods xxx =new Goods();
            List<Integer> list1= new ArrayList<>();
            list1.add(R.drawable.test);
            xxx.setPictures(list1);
            xxx.setGood_Id("CN_00");
            xxx.setNumber(0);
            xxx.setGood_price("300");
            xxx.setGood_name("我也不知道是什么");
            goodsList.add(xxx);
        }
    }
}
