package com.vince.andframe.base.ui.widgets.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Scroller;

import com.vince.aframe.R;

/**
 * Created by tianweixin on 2016-8-29.
 */

public class RefreshListView extends ListView implements RefreshHandler.ViewExecutor {

    private Scroller scroller;
    private OnRefreshListener refreshListener;
    private IRefreshHandler refreshHandler;
    private RefreshHandler.OnScrollListener scrollListener;


    public RefreshListView(Context context) {
        super(context);
        init(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        setCacheColorHint(context.getResources().getColor(R.color.transparent));
        scroller = new Scroller(context);
        refreshHandler = new RefreshHandler(context, this);
        RefreshHeaderView headerView = refreshHandler.getHeaderView();
        addHeaderView(headerView, null, false);
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.refreshListener = listener;
    }

    public void stopRefresh() {
        refreshHandler.stopRefresh();
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

    @Override
    public boolean firstItemIsVisible() {
        return getFirstVisiblePosition() == 0;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration, RefreshHandler.OnScrollListener scrollListener) {
        this.scrollListener = scrollListener;
        scroller.startScroll(startX, startY, dx, dy, duration);
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
        setSelection(0);
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
}
