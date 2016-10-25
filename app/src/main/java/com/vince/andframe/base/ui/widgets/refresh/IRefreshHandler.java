package com.vince.andframe.base.ui.widgets.refresh;

import android.view.MotionEvent;

/**
 * Created by tianweixin on 2016-9-1.
 */

public interface IRefreshHandler {
    RefreshHeaderView getHeaderView();

    void onMotionDown(MotionEvent ev);

    boolean onMotionMove(MotionEvent ev);

    void onMotionDefault(MotionEvent ev);

    void stopRefresh();
}
