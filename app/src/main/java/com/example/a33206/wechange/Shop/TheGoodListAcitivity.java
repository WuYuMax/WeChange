package com.example.a33206.wechange.Shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;

import com.example.a33206.wechange.Adapt.TheGoodAdapt;
import com.example.a33206.wechange.R;
import com.example.a33206.wechange.db.Goods;

import java.util.ArrayList;
import java.util.List;

public class TheGoodListAcitivity extends AppCompatActivity {
    private List<String> namelist =new ArrayList<>();
    private TextView nametext;
    private List<Goods> goodsList=new ArrayList<>();
    private TheGoodAdapt theGoodAdapt;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thegood);
        nametext =findViewById(R.id.the_good_name);
        recyclerView =findViewById(R.id.the_good_recycler);
        initNameList();
        initGoodList();
        theGoodAdapt= new TheGoodAdapt(TheGoodListAcitivity.this,goodsList);
        StaggeredGridLayoutManager layoutManager =new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(theGoodAdapt);
        nametext.setText(namelist.get(getIntent().getIntExtra("type",8)));
    }

    private void initGoodList() {
        for (int i=0;i<4 ; i++){
            Goods app=new Goods();
            List<Integer> list = new ArrayList<>();
            list.add(R.drawable.logo);
            app.setGood_Id("CN_01");
            app.setGood_name("APP ");
            app.setGood_price("298");
            app.setCommit("帅气的APP\n程序ok\ndslkfjlsdkjf\nfldskfjlskd");
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

    private void initNameList() {
        namelist.add("二手手机");
        namelist.add("书籍");
        namelist.add("游戏");
        namelist.add("服饰");
        namelist.add("二手电脑");
        namelist.add("电子配件");
        namelist.add("家具");
        namelist.add("其他");
        namelist.add("null");
    }
}
