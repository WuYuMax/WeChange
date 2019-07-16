package com.example.a33206.wechange;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.lang.reflect.Array;
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
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        //实例化
        shopFragment = new ShopFragment();
        actionFragment = new ActionFragment();
        commitFragment = new CommitFragment();
        myFragment = new MyFragment();
        //添加页面
        fragmentList.add(shopFragment);
        fragmentList.add(actionFragment);
        fragmentList.add(commitFragment);
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
                .addItem(new BottomNavigationItem(R.drawable.ic_arrow_back_black_24dp,"Shop"))
                .addItem(new BottomNavigationItem(R.drawable.ic_search_black_24dp,"Activity"))
                .addItem(new BottomNavigationItem(R.drawable.ic_remove_red_eye_black_24dp,"commit"))
                .addItem(new BottomNavigationItem(R.drawable.ic_remove_red_eye_white_24dp,"my"))
                .setFirstSelectedPosition(0)
                .initialise();
    }
}
