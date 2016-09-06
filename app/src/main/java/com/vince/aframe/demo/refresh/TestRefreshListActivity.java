package com.vince.aframe.demo.refresh;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.vince.aframe.R;
import com.vince.aframe.base.ui.activity.BaseActivity;
import com.vince.aframe.base.ui.widgets.refresh.OnRefreshListener;
import com.vince.aframe.base.ui.widgets.refresh.RefreshListView;

/**
 * Created by tianweixin on 2016-9-2.
 */

public class TestRefreshListActivity extends BaseActivity {

    private RefreshListView listView;
    private Handler hander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_refresh_listview);
        listView = (RefreshListView) findViewById(R.id.lv_refresh);
        listView.setAdapter(new TestRefreshListViewAdapter());
        hander = new Handler();
        listView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                hander.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listView.stopRefresh();
                    }
                }, 3000);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TestRefreshListActivity.this, "list click", Toast.LENGTH_SHORT).show();
            }
        });
        topTitle();
    }

    private void topTitle() {
        setTitle("测试RefreshListView");
        needRight(false);
    }
}
