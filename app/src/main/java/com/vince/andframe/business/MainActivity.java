package com.vince.andframe.business;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vince.aframe.R;
import com.vince.andframe.app.AFConstants;
import com.vince.andframe.base.ui.activity.BaseBizActivity;
import com.vince.andframe.base.ui.prompt.ToastUtils;
import com.vince.andframe.demo.aidl.BookClientActivity;
import com.vince.andframe.demo.network.NetTestActivity;
import com.vince.andframe.demo.recycler.drawer.DrawerViewActivity;
import com.vince.andframe.demo.recycler.pinterest.PinterestDemoActivity;
import com.vince.andframe.demo.refresh.TestRefreshListActivity;
import com.vince.andframe.demo.refresh.TestRefreshScrollActivity;
import com.vince.andframe.demo.tab.TabLayoutDemoActivity;

public class MainActivity extends BaseBizActivity {

    private Button btnNet;
    private Button btnRefreshListView;
    private Button btnRefreshScrollView;
    private Button btnDrawer;
    private Button btnPinterset;
    private Button btnAidl;

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
        btnDrawer = (Button) findViewById(R.id.btn_drawer);
        btnPinterset = (Button) findViewById(R.id.btn_pinterest);
        btnAidl = (Button) findViewById(R.id.btn_aidl);
        initListener();
        topTitle();
    }

    @Override
    protected void onResume() {
        super.onResume();
        clickBackTimes = 0;
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
        ToastUtils.showToast("再按一次退出应用", Toast.LENGTH_SHORT);
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
                        break;
                    case R.id.btn_drawer:
                        toActivity(DrawerViewActivity.class);
                        break;
                    case R.id.btn_pinterest:
                        toActivity(PinterestDemoActivity.class);
                        break;
                    case R.id.btn_aidl:
                        toActivity(BookClientActivity.class);
                        break;
                    case R.id.btn_tab:
                        toActivity(TabLayoutDemoActivity.class);
                        break;
                    default:
                        break;
                }
            }
        };
        btnNet.setOnClickListener(clickListener);
        btnRefreshListView.setOnClickListener(clickListener);
        btnRefreshScrollView.setOnClickListener(clickListener);
        btnDrawer.setOnClickListener(clickListener);
        btnPinterset.setOnClickListener(clickListener);
        btnAidl.setOnClickListener(clickListener);
        findViewById(R.id.btn_tab).setOnClickListener(clickListener);
    }

    private void toActivity(Class activity) {
        Intent it = new Intent(this, activity);
        startActivity(it);
    }

}
