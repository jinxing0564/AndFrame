package com.vince.aframe.business;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vince.aframe.R;
import com.vince.aframe.base.ui.activity.BaseActivity;
import com.vince.aframe.demo.network.NetTestActivity;
import com.vince.aframe.demo.refresh.TestRefreshListActivity;
import com.vince.aframe.demo.refresh.TestRefreshScrollActivity;

public class MainActivity extends BaseActivity {

    private Button btnNet;
    private Button btnRefreshListView;
    private Button btnRefreshScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        disableSwipeBack();

        btnNet = (Button) findViewById(R.id.btn_net);
        btnRefreshListView = (Button) findViewById(R.id.btn_refresh_list);
        btnRefreshScrollView = (Button) findViewById(R.id.btn_refresh_scroll);
        initListener();
        topTitle();
    }

    private void topTitle() {
        setTitle("首页");
        needRight(false);
        needBack(false);
    }

    private void initListener() {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_net:
                        toActivity(NetTestActivity.class);
                        break;
                    case R.id.btn_refresh_list:
                        toActivity(TestRefreshListActivity.class);
                        break;
                    case R.id.btn_refresh_scroll:
                        toActivity(TestRefreshScrollActivity.class);
                    default:
                        break;
                }
            }
        };
        btnNet.setOnClickListener(clickListener);
        btnRefreshListView.setOnClickListener(clickListener);
        btnRefreshScrollView.setOnClickListener(clickListener);
    }

    private void toActivity(Class activity) {
        Intent it = new Intent(this, activity);
        startActivity(it);
    }

}
