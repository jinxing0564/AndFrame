package com.vince.aframe.base.ui.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.vince.aframe.R;
import com.vince.aframe.base.ui.widgets.swipeback.SwipeBackAdapter;
import com.vince.aframe.base.ui.widgets.swipeback.SwipeBackConstants;
import com.vince.aframe.base.ui.widgets.swipeback.SwipeBackLayout;

/**
 * Created by tianweixin on 2016-9-5.
 */

public class SwipeBackActivity extends ThemeActivity implements SwipeBackAdapter {

    public SwipeBackLayout swipeBackLayout;

    private int swipeLayoutWidth = -1;
    private BroadcastReceiver swipeReceiver = null;
    private boolean isRegister = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver();
    }

    @Override
    public void setContentView(int layoutResID) {
        this.setContentView(LayoutInflater.from(this).inflate(layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        this.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        swipeBackLayout = (SwipeBackLayout) LayoutInflater.from(this).inflate(R.layout.swipe_back_layout, null);
        FrameLayout flytContent = (FrameLayout) swipeBackLayout.findViewById(R.id.flyt_content);
        flytContent.addView(view);
        super.setContentView(swipeBackLayout, params);
        initSwipeLayout();
    }

    private void initSwipeLayout() {
        if (swipeBackLayout == null) {
            return;
        }
        swipeBackLayout.setAdapter(this);
        swipeBackLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {
                swipeLayoutWidth = swipeBackLayout.getWidth();
                swipeBackLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        swipeBackLayout.resetAllPages();
        if (swipeReceiver != null && isRegister) {
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
            localBroadcastManager.unregisterReceiver(swipeReceiver);
            isRegister = false;
        }
        super.onDestroy();
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter(SwipeBackConstants.ACTION_ACTIVITY_SWIPE);
        swipeReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(SwipeBackConstants.ACTION_ACTIVITY_SWIPE) && swipeBackLayout != null && swipeLayoutWidth != -1) {
                    Class currActivity = (Class) intent.getSerializableExtra(SwipeBackConstants.CUR_SWIPE_ACTIVITY);
                    int curScrollX = intent.getIntExtra(SwipeBackConstants.CUR_SWIPE_X, 0);
                    if (!SwipeBackActivity.this.getClass().equals(currActivity)) {
                        int swipeX;
                        if (curScrollX == 0) {
                            swipeX = 0;
                        } else {
                            swipeX = (swipeLayoutWidth - curScrollX) / 4;
                        }
                        swipeBackLayout.scrollTo(swipeX, 0);
                    }
                }
            }
        };
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(swipeReceiver, filter);
        isRegister = true;
    }

    public void disableSwipeBack() {
        swipeBackLayout.canSwipeBack(false);
    }

    @Override
    public void back() {
        this.onBackPressed();
    }

    @Override
    public Class getActivityClass() {
        return this.getClass();
    }
}
