package com.vince.andframe.base.ui.drawer;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

/**
 * Created by tianweixin on 2017-3-29.
 */

public class DrawerRecyclerView extends RecyclerView {
    private int touchSlop;
    private static final int MOVE_UNKONWN = 0;
    private static final int MOVE_HORIZONTAL = 1;
    private static final int MOVE_VERTICAL = 2;
    private int direction = MOVE_UNKONWN;
    private static final float MOVE_RATIO = 0.7f;
    private final int DURATION = 300;
    private Scroller scroller;
    private boolean isScrolling = false;


    private DrawerItemView mFocusView;
    private int mFocusPosition = -1;
    private int startX = -1;
    private int startY = -1;
    private int lastX = -1;
    private int lastY = -1;
    private MotionEvent startEvent;

    private static final int TAN = 2;

    public DrawerRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public DrawerRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        scroller = new Scroller(context);

    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (mFocusView != null && startX == -1) { //当前有没有关闭的drawer
            autoScrollBack();
            return true;
        }

        int action = e.getAction();
        int x = (int) e.getX();
        int y = (int) e.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                focusTarget(e);
                direction = MOVE_UNKONWN;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mFocusView == null) {
                    focusTarget(e);
                }
                if (direction == MOVE_UNKONWN) {
                    int len = (int) Math.sqrt((x - startX) * (x - startX) + (y - startY) * (y - startY));
                    if (len < touchSlop) {
                        break;
                    } else if (Math.abs(x - startX) > Math.abs(y - startY) * TAN) {
                        direction = MOVE_HORIZONTAL;
                    } else {
                        direction = MOVE_VERTICAL;
                    }
                }
                if (mFocusView != null && direction == MOVE_HORIZONTAL && !isScrolling) {
                    int deltaX = lastX - x;
                    if (lastX == -1 || lastY == -1 || deltaX == 0) {
                        break;
                    }
                    int scrollX = mFocusView.getScroll();
                    int newScroll = (int) (scrollX + deltaX * MOVE_RATIO);
                    if (newScroll < 0) {
                        newScroll = 0;
                    } else if (newScroll > mFocusView.getDrawerWidth()) {
                        newScroll = mFocusView.getDrawerWidth();
                    }
                    mFocusView.scroll(newScroll);
                }
                break;
        }
        lastX = x;
        lastY = y;

        if (direction == MOVE_HORIZONTAL) {
            if (reset(action)) {
                autoScroll();
                if (startEvent != null) {
                    startEvent.setAction(action);
                    boolean ret =  super.onTouchEvent(startEvent);
                    startEvent.recycle();
                    startEvent = null;
                    return ret;
                }
            } else {
                if (startEvent == null) {
                    startEvent = MotionEvent.obtain(e);
                }
            }
            return true;
        } else {
            if (reset(action)) {
                mFocusView = null;
                mFocusPosition = -1;
            }
            return super.onTouchEvent(e);
        }
    }

    private boolean reset(int action) {
        if (action != MotionEvent.ACTION_DOWN && action != MotionEvent.ACTION_MOVE) {
            direction = MOVE_UNKONWN;
            lastX = -1;
            lastY = -1;
            startX = -1;
            startY = -1;
            return true;
        }
        return false;
    }

    private void focusTarget(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        View focusView = null;
        int pos = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child != null && child.getVisibility() == VISIBLE) {
                Rect cRect = new Rect();
                child.getHitRect(cRect);
                if (cRect.contains(x, y)) {
                    focusView = child;
                    pos = i;
                    break;
                }
            }
        }
        if (focusView != null) {
            ViewHolder viewHolder = getChildViewHolder(focusView);
            mFocusView = (DrawerItemView) viewHolder.itemView;
            mFocusPosition = pos + ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
            mFocusView.setPosition(mFocusPosition);
            startX = x;
            startY = y;
        }
    }

    private void autoScroll() {
        if (mFocusView == null || isScrolling) {
            return;
        }
        int pos = mFocusView.getScroll();
        int drawerWidth = mFocusView.getDrawerWidth();
        if (pos < drawerWidth / 2) {
            int duration = pos * DURATION / (drawerWidth / 2);
            scroller.startScroll(pos, 0, -pos, 0, duration);
        } else {
            int duration = (drawerWidth - pos) * DURATION / (drawerWidth / 2);
            scroller.startScroll(pos, 0, drawerWidth - pos, 0, duration);
        }
        isScrolling = true;
        postInvalidate();

    }

    private void autoScrollBack() {
        if (mFocusView == null || isScrolling) {
            return;
        }
        int pos = mFocusView.getScroll();
        int drawerWidth = mFocusView.getDrawerWidth();
        int duration = pos * DURATION / drawerWidth;
        scroller.startScroll(pos, 0, -pos, 0, duration);
        isScrolling = true;
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset() && mFocusView != null) {
            mFocusView.scroll(scroller.getCurrX());
            if (scroller.isFinished()) {
                isScrolling = false;
                if (scroller.getCurrX() == 0) {
                    mFocusView = null;
                    mFocusPosition = -1;
                }
            }
            postInvalidate();
        }
    }
}
