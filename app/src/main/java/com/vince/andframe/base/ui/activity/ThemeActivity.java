package com.vince.andframe.base.ui.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vince.aframe.R;

/**
 * Created by tianweixin on 2016-9-5.
 */

public class ThemeActivity extends Activity {
    private View titleView;
    private TextView title;
    private TextView tvRight;
    private ImageView ivRight;
    private LinearLayout back;
    private LinearLayout right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void setContentView(int layoutResID) {
        this.setContentView(LayoutInflater.from(this).inflate(layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        this.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        initTopTitle(view);
    }

    private void initTopTitle(View contentView) {
        if (titleView != null || contentView == null) {
            return;
        }
        if (contentView instanceof LinearLayout) {
            titleView = LayoutInflater.from(this).inflate(R.layout.common_layout_top_title, null);
            titleView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.top_title_height)));
            ((LinearLayout) contentView).addView(titleView, 0);
            back = (LinearLayout) titleView.findViewById(R.id.lyt_back);
            title = (TextView) titleView.findViewById(R.id.tv_top_title);
            tvRight = (TextView) titleView.findViewById(R.id.tv_right);
            ivRight = (ImageView) titleView.findViewById(R.id.iv_right);
            right = (LinearLayout) titleView.findViewById(R.id.llyt_right);
            View.OnClickListener titleClickListener = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.lyt_back:
                            onBackClick();
                            break;
                        case R.id.llyt_right:
                            onRightClick();
                            break;
                    }
                }
            };
            back.setOnClickListener(titleClickListener);
            right.setOnClickListener(titleClickListener);
        }
    }

    public void onBackClick() {
        this.onBackPressed();
    }

    public void onRightClick() {

    }

    public void needBack(boolean need) {
        needView(need, back);
    }

    public void noTitle() {
        needView(false, titleView);
    }

    public void setTitle(String t) {
        setText(title, t);
    }

    public void needRight(boolean need) {
        needView(need, ivRight);
    }

    public void needRightText(boolean need) {
        needView(need, tvRight);
    }

    public void needRightIcon(boolean need) {
        needView(need, ivRight);
    }

    public void setRightText(String t) {
        setText(tvRight, t);
    }

    public void setRightIcon(int resId) {
        if (ivRight == null) {
            return;
        }
        ivRight.setImageResource(resId);
    }

    private void needView(boolean need, View view) {
        if (view == null) {
            return;
        }
        if (need) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    private void setText(TextView tv, String t) {
        if (tv == null) {
            return;
        }
        tv.setText(t);
    }

    @Override
    public Resources getResources() {
        try {
            Resources res = super.getResources();
            Configuration config = new Configuration();
            config.setToDefaults();
            res.updateConfiguration(config, res.getDisplayMetrics());
            return res;
        } catch (Exception e) {
            return super.getResources();
        }
    }

}
