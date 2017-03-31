package com.vince.andframe.base.ui.widgets.swipeback;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.vince.aframe.R;
import com.vince.andframe.base.tools.ScreenUtil;

public class SwipeBackLayout extends LinearLayout {

    private final int STATUS_NORMAL = 0x1;
    private final int STATUS_HOLD_SCROLL = 0x2;
    private final int STATUS_FREE_SCROLL = 0x3;
    private int mStatus = STATUS_NORMAL;
    private final int DURATION = 1000;
    private final int TRIGGER_WIDTH = ScreenUtil.dipToPixel(20); // dp
    private Scroller mScroller;
    private float mDownX;
    private int swipeLayoutWidth = -1;
    private boolean mCanScrollClose = false;
    private Drawable mShadowDrawable;
    private boolean canSwipeBack = true;
    private boolean handled = false;
    private long startTime;
    private SwipeBackAdapter mAdapter;

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
        mShadowDrawable = getResources().getDrawable(R.drawable.swipe_back_shadow);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setAdapter(SwipeBackAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        swipeLayoutWidth = getWidth();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!canSwipeBack) {
            return super.dispatchTouchEvent(ev);
        }
        if (mStatus == STATUS_FREE_SCROLL) {
            return true;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getRawX();
                startTime = System.currentTimeMillis();
                resetAllPages();
                handled = false;
                mCanScrollClose = (mDownX < TRIGGER_WIDTH && swipeLayoutWidth != -1 && mAdapter != null);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mCanScrollClose && !handled) {
                    float curX = ev.getRawX();
                    int delta = (int) (curX - mDownX);
                    if (delta > 0) {
                        mStatus = STATUS_HOLD_SCROLL;
                        scrollToAndSendBroadcast(-delta, 0);
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                if (mCanScrollClose && !handled) {
                    mStatus = STATUS_FREE_SCROLL;
                    long useTime = System.currentTimeMillis() - startTime;
                    float swipeLen = ev.getRawX() - mDownX;
                    float maxTime = 540 * swipeLen / swipeLayoutWidth;
                    if ((swipeLen > swipeLayoutWidth / 6 && useTime < maxTime) || swipeLen > swipeLayoutWidth / 2) {
                        scrollRight();
                    } else {
                        scrollLeft();
                    }
                }
                handled = true;
                break;
        }
        if (mCanScrollClose) {
            return true;
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }

    public void resetAllPages() {
        scrollToAndSendBroadcast(0, 0);
        mStatus = STATUS_NORMAL;
        handled = true;
    }

    private void scrollLeft() {
        int delta = this.getScrollX();
        int duration = DURATION * Math.abs(delta) / swipeLayoutWidth;
        mScroller.startScroll(delta, 0, -delta, 0, duration);
        postInvalidate();
    }

    private void scrollRight() {
        int delta = this.getScrollX();
        int duration = DURATION * (swipeLayoutWidth - Math.abs(delta)) / swipeLayoutWidth;
        mScroller.startScroll(delta, 0, -delta - swipeLayoutWidth, 0, duration);
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            Log.d("swipeBack", "cur x = " + mScroller.getCurrX());
            scrollToAndSendBroadcast(mScroller.getCurrX(), 0);
            postInvalidate();

            if (mStatus == STATUS_FREE_SCROLL && mScroller.isFinished()) {
                mStatus = STATUS_NORMAL;
            }
            if (mScroller.isFinished() && mScroller.getCurrX() < -swipeLayoutWidth / 2) {
                if (mAdapter != null) {
                    mAdapter.back();
                }
            }
        }
    }

    private void scrollToAndSendBroadcast(int x, int y) {
        if (mAdapter != null) {
            this.scrollTo(x, y);
            Intent intent = new Intent(SwipeBackConstants.ACTION_ACTIVITY_SWIPE);
            intent.putExtra(SwipeBackConstants.CUR_SWIPE_X, Math.abs(x));
            intent.putExtra(SwipeBackConstants.CUR_SWIPE_ACTIVITY, mAdapter.getActivityClass());
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
            localBroadcastManager.sendBroadcast(intent);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mShadowDrawable != null) {
            int left = -ScreenUtil.dipToPixel(8);
            int right = 0;
            int top = this.getTop();
            int bottom = this.getBottom();
            mShadowDrawable.setBounds(left, top, right, bottom);
            mShadowDrawable.setAlpha(80);
            mShadowDrawable.draw(canvas);
        }
    }

    public void canSwipeBack(boolean canSwipeBack) {
        this.canSwipeBack = canSwipeBack;
    }

}
