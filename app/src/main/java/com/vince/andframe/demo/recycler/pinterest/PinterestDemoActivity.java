package com.vince.andframe.demo.recycler.pinterest;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.vince.aframe.R;
import com.vince.andframe.base.ui.activity.BaseBizActivity;

/**
 * Created by tianweixin on 2017-4-1.
 */

public class PinterestDemoActivity extends BaseBizActivity {
    private RecyclerView recyclerView;
    private PinterestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinterset_demo);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        adapter = new PinterestAdapter(this);
        recyclerView.setAdapter(adapter);

        setTitle("测试瀑布流");
    }
}







