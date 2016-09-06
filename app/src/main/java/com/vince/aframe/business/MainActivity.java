package com.vince.aframe.business;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vince.aframe.R;
import com.vince.aframe.app.AFConstants;
import com.vince.aframe.base.ui.activity.BaseBizActivity;
import com.vince.aframe.demo.network.NetTestActivity;
import com.vince.aframe.demo.refresh.TestRefreshListActivity;
import com.vince.aframe.demo.refresh.TestRefreshScrollActivity;

public class MainActivity extends BaseBizActivity {

    private Button btnNet;
    private Button btnRefreshListView;
    private Button btnRefreshScrollView;

    private int clickBackTimes = 0;
    private Handler handler = new Handler();

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

    @Override
    public void onBackPressed() {
        clickBackTimes++;
        if (clickBackTimes >= 2) {
            super.onBackPressed();
            return;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                clickBackTimes = 0;
            }
        }, AFConstants.BACK_INTERVAL);
        Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
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
