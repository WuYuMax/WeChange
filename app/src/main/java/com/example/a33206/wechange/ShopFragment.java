package com.example.a33206.wechange;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a33206.wechange.Adapt.ViewPagerTabAdpater;

import java.util.ArrayList;
import java.util.List;

// 二手商店市场fragment 上层：Work界面 下层：商品查询界面
public class ShopFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    ShopMainFragment mainFragment ;
    ShopGooditemFragment gooditemFragment;
    ShopGooditemFragment gooditemFragment2;
    ShopGooditemFragment gooditemFragment3;
    ViewPagerTabAdpater viewPagerTabAdapt =null;
    List<Fragment> fragmentList = new ArrayList<>();
    List<String> TabList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop,container,false);
        viewPager = view.findViewById(R.id.shopfragment_viewpager);
        tabLayout = view.findViewById(R.id.shopfragment_tab);
        mainFragment = new ShopMainFragment();
        gooditemFragment = new ShopGooditemFragment();
        gooditemFragment2=new ShopGooditemFragment();
        gooditemFragment3 = new ShopGooditemFragment();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (TabList.size()==0) {
            TabList.add("首页");
            TabList.add("服饰");
            TabList.add("电器");
            TabList.add("摆件");
            fragmentList.add(mainFragment);
            fragmentList.add(gooditemFragment);
            fragmentList.add(gooditemFragment2);
            fragmentList.add(gooditemFragment3);
        }
        viewPagerTabAdapt = new ViewPagerTabAdpater(getChildFragmentManager(),fragmentList,TabList);
        viewPager.setAdapter(viewPagerTabAdapt);
        tabLayout.setupWithViewPager(viewPager);
    }
}
