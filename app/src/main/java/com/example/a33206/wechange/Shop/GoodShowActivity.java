package com.example.a33206.wechange.Shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a33206.wechange.Adapt.GlideImageLoader;
import com.example.a33206.wechange.Adapt.GoodAdapt;
import com.example.a33206.wechange.R;
import com.example.a33206.wechange.db.Goods;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

public class GoodShowActivity extends AppCompatActivity {
    private TextView user_name ;
    private ImageView user_pic;
    private TextView user_level;
    private TextView good_name;
    private TextView good_price;
    private TextView good_know;
    private TextView telButton;
    private RecyclerView recyclerView;
    private GoodAdapt adapt;
    private List<Goods> datalist =new ArrayList<>();
    private Banner good_banner;
    private ScrollView scrollView;
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
        telButton = findViewById(R.id.tel);
        recyclerView =findViewById(R.id.showgood_recycle);
        good_banner =findViewById(R.id.show_good_banner);
        scrollView = findViewById(R.id.show_good_scroll);

        scrollView.scrollTo(0,0);
        initLayout();
        initList();
        if (getIntent().getBooleanExtra("test",true)) {
            initRecycle();
        }
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
        goods= (Goods) getIntent().getSerializableExtra("good");
        good_price.setText("￥ "+goods.getGood_price());
        good_name.setText(goods.getGood_name());
        good_know.setText(goods.getCommit());
        if (goods.getPictures().size()!=0) {
            good_banner.setImageLoader(new GlideImageLoader());
            good_banner.setImages(goods.getPictures());
            good_banner.start();
        }
    }

    private void initList(){
        for (int i=0;i<4;i++){
            Goods goods;
            goods = new Goods();
            goods= (Goods) getIntent().getSerializableExtra("good");
            datalist.add(goods);
        }
    }
}
