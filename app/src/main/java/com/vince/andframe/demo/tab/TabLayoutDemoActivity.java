package com.vince.andframe.demo.tab;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vince.aframe.R;
import com.vince.andframe.base.ui.activity.BaseBizActivity;

/**
 * Created by by tianweixin on 2018/11/28.
 */
public class TabLayoutDemoActivity extends BaseBizActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] titles = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_tab_layout);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return TabDemoFragment.newInstance(titles[position]);
            }

            @Override
            public int getCount() {
                return titles.length;
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        initTabLayout();

    }

    private void initTabLayout() {
        for (int i = 0; i < titles.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_tab_item, null);
            TextView tv = (TextView) view.findViewById(R.id.tv_tab_title);
            tv.setText(titles[i]);
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);
            tab.setCustomView(view);
            setTabWidth(i, view);

        }
    }

    //设置tab宽度根据内容，设置tab左右空隙
    private void setTabWidth(int position, View view) {
        ViewGroup slidingTabStrip = (ViewGroup) tabLayout.getChildAt(0);
        ViewGroup tabView = (ViewGroup) slidingTabStrip.getChildAt(position);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        //手动测量一下
        view.measure(w, h);
        params.width = view.getMeasuredWidth() + 50;
        //设置tabView的宽度
        tabView.setLayoutParams(params);
        tabView.setPadding(25, tabView.getPaddingTop(), 25, tabView.getPaddingBottom());
    }
}
