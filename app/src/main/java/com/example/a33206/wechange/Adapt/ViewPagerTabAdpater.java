package com.example.a33206.wechange.Adapt;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

public class ViewPagerTabAdpater extends FragmentPagerAdapter {
    private List<Fragment> list;
    private List<String> Strinlist;
    public ViewPagerTabAdpater(FragmentManager fm, List<Fragment> fragments,List<String> Tab) {
        super(fm);
        this.list=fragments;
        this.Strinlist=Tab;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return Strinlist.get(position);
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
