package com.vince.aframe.base.ui.activity;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tianweixin on 2016-7-26.
 */
public class BaseActivity extends SwipeBackActivity {

    private View getContentView() {
        try {
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            ViewGroup group = (ViewGroup) decorView.getChildAt(0);
            if (group.getChildCount() > 0) {
                ViewGroup group2 = (ViewGroup) group.getChildAt(group.getChildCount() - 1);
                View content = null;
                if (group2.getChildCount() > 0) {
                    content = group2.getChildAt(0);
                }
                return content;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

}
