package com.tao.demo.tabDemo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.tao.demo.R;

import java.util.ArrayList;
import java.util.List;

public class TabDemoActivity extends AppCompatActivity {
    PagerTabStrip tabStrip;
    ViewPager mViewPager;
    PagerAdapter adapter;
    List<Fragment> fragmentList;
    List<String> titleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_demo);
//        tabStrip = findViewById(R.id.tabstrip);
        mViewPager = findViewById(R.id.vp_pager);
        adapter = new MyAdapter(this.getSupportFragmentManager());

        fragmentList = new ArrayList<>();

        fragmentList.add(BlankFragment.newInstance("111", "222"));
        fragmentList.add(BlankFragment.newInstance("111q", "222q"));
        fragmentList.add(BlankFragment.newInstance("111w", "222w"));
        mViewPager.setAdapter(adapter);
        titleList.add("home");
        titleList.add("scence");
        titleList.add("me");
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}
