package com.example.a33206.wechange.Shop;

import android.annotation.SuppressLint;
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
import com.example.a33206.wechange.R;
import com.example.a33206.wechange.db.Goods;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class ShopGooditemFragment extends Fragment {

    private RecyclerView recyclerView;
    private GoodAdapt adapt;
    private List<Goods> goodsList= new ArrayList<>();
    private String type;
    @SuppressLint("ValidFragment")
    public ShopGooditemFragment(String position) {
        type = position;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_gooditems,container,false);
        recyclerView = view.findViewById(R.id.show_good_item_recycle);
        initGood();
        adapt = new GoodAdapt(getActivity(),goodsList);
        StaggeredGridLayoutManager layoutManager =new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapt);
        return view ;
    }

    private void initGood() {
        for (int i=0;i<4 ; i++){
            Goods app=new Goods();
            List<Integer> list = new ArrayList<>();
            list.add(R.drawable.logo);
            app.setGood_Id("CN_01");
            app.setGood_name("APP ");
            app.setUser_ID("123456789");
            app.setGood_price("298");
            app.setPictures(list);
            app.setNumber(5);
            goodsList.add(app);
            Goods xxx =new Goods();
            List<Integer> list1= new ArrayList<>();
            list1.add(R.drawable.test);
            list1.add(R.drawable.logo);
            xxx.setPictures(list1);
            xxx.setGood_Id("CN_00");
            xxx.setUser_ID("2005");
            xxx.setNumber(0);
            xxx.setGood_price("300");
            xxx.setGood_name(type);
            goodsList.add(xxx);
        }
    }
}
