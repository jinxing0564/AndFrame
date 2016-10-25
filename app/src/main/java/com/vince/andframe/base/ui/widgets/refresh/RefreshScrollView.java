package com.vince.andframe.base.ui.widgets.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * Created by tianweixin on 2016-9-5.
 */

public class RefreshScrollView extends ScrollView implements RefreshHandler.ViewExecutor {
    private IRefreshHandler refreshHandler;
    private Scroller scroller;
    private RefreshHandler.OnScrollListener scrollListener;
    private OnRefreshListener refreshListener;
    private RefreshHeaderView headerView;

    public RefreshScrollView(Context context) {
        super(context);
        init(context);
    }

    public RefreshScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                refreshHandler.onMotionDown(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                if (refreshHandler.onMotionMove(ev)) {
                    return true;
                }
                break;
            default:
                refreshHandler.onMotionDefault(ev);
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.refreshListener = listener;
    }

    public void stopRefresh() {
        refreshHandler.stopRefresh();
    }

    private void init(Context context) {
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        scroller = new Scroller(context);
        refreshHandler = new RefreshHandler(context, this);
        headerView = refreshHandler.getHeaderView();
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                if (getChildCount() > 0 && getChildAt(0) instanceof LinearLayout) {
                    LinearLayout container = (LinearLayout) getChildAt(0);
                    container.addView(headerView, 0, new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                }
                RefreshScrollView.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    @Override
    public boolean firstItemIsVisible() {
        return getScrollY() == 0;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration, RefreshHandler.OnScrollListener scrollListener) {
        this.scrollListener = scrollListener;
        scroller.startScroll(startX, startY, dx, dy, duration);
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        if (scrollListener == null) {
            return;
        }
        if (scroller.computeScrollOffset()) {
            scrollListener.onScroll(scroller.getCurrY(), false);
            if (scroller.isFinished()) {
                scrollListener.onScroll(scroller.getCurrY(), true);
            }
        }
        postInvalidate();
    }

    @Override
    public void onRefresh() {
        if (refreshListener != null) {
            refreshListener.onRefresh();
        }
    }

    @Override
    public void onSelectionFirst() {

    }
}
