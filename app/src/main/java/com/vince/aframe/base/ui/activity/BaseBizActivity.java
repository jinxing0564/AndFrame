package com.vince.aframe.base.ui.activity;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tianweixin on 2016-7-26.
 */
public class BaseBizActivity extends SwipeBackActivity {

    /**
     * 三星s5，5.0系统可以用此方法获得ContentView
     * 其他手机和系统需要继续调试，不一定能够获得正确结果
     */
    private View getContentView() {
        try {
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            ViewGroup group = (ViewGroup) decorView.getChildAt(0);
            if (group.getChildCount() > 0) {
                ViewGroup group2 = (ViewGroup) group.getChildAt(group.getChildCount() - 1);
                View contentView = null;
                if (group2.getChildCount() > 0) {
                    contentView = group2.getChildAt(0);
                }
                return contentView;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

}
