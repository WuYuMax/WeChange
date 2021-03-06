package com.example.a33206.wechange;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.a33206.wechange.Action.ActionFragment;
import com.example.a33206.wechange.Adapt.ViewPagerAdapt;
import com.example.a33206.wechange.My.MyFragment;
import com.example.a33206.wechange.Shop.ShopFragment;

import java.util.ArrayList;
import java.util.List;

public class WorkActivity extends AppCompatActivity {
    //注册界面 上层：登陆界面 注册界面 等待界面 下层：
    private BottomNavigationBar navigationBar ;
    private ShopFragment shopFragment ;
    private ActionFragment actionFragment;
    private CommitFragment commitFragment;
    private MyFragment myFragment;
    private List<Fragment> fragmentList=new ArrayList<Fragment>();
    private ViewPager viewPager;
    private PagerAdapter viewpager_adapt;
    private String userId;
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        //实例化
        userId = getIntent().getStringExtra("userId");

        shopFragment = new ShopFragment();
        actionFragment = new ActionFragment();
        myFragment = new MyFragment();
        //添加页面
        fragmentList.add(shopFragment);
        fragmentList.add(actionFragment);
        fragmentList.add(myFragment);

        //Viewpager
        viewPager = findViewById(R.id.Work_PagerView);
        viewpager_adapt = new ViewPagerAdapt(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(viewpager_adapt);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                navigationBar.selectTab(i);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //底部导航栏
        navigationBar=findViewById(R.id.nagavition);
        navigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            FragmentManager fragmentManager = WorkActivity.this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            @Override
            public void onTabSelected(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        })
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT)
                .setActiveColor("#1e34d6")
                .setInActiveColor("#ffffff")
                .setBarBackgroundColor("#4a90d2")
                .addItem(new BottomNavigationItem(R.drawable.shoppic,"商品市场"))
                .addItem(new BottomNavigationItem(R.drawable.activitypic,"活动广场"))
                .addItem(new BottomNavigationItem(R.drawable.memessage,"个人中心"))
                .setFirstSelectedPosition(0)
                .initialise();

    }

}
