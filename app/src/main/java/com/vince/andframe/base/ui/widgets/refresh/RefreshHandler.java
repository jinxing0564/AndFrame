package com.vince.andframe.base.ui.widgets.refresh;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;

import com.vince.aframe.R;
import com.vince.andframe.base.tools.ScreenUtil;

/**
 * Created by tianweixin on 2016-8-29.
 */

public class RefreshHandler implements IRefreshHandler {
    public static final int STATUS_NORMAL = 0x1;
    public static final int STATUS_PULLING = 0x2;
    public static final int STATUS_REFRESHING = 0x3;
    public static final int STATUS_FINSHING = 0x4;
    private int mStatus = STATUS_NORMAL;
    private Context context;

    private int startX = -1;
    private int startY = -1;
    private int endX = -1;
    private int endY = -1;
    private int startPullY = -1;
    private boolean isPulling = false;
    private final int CRITICALSIZE = ScreenUtil.dipToPixel(30);
    private final float SCROLL_RATIO = 1.8f;
    private final int DURATION = 1000;
    private RefreshHeaderView headerView;
    private int headerHeight = -1;

    private ViewExecutor viewExecutor;

    public RefreshHandler(Context context, ViewExecutor executor) {
        this.context = context;
        this.viewExecutor = executor;
    }

    @Override
    public RefreshHeaderView getHeaderView() {
        if (headerView == null) {
            headerView = new RefreshHeaderView(context);
            headerView.setHintColor(R.color.color_999999);
            headerView.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @SuppressWarnings("deprecation")
                        @Override
                        public void onGlobalLayout() {
                            headerHeight = headerView.getHeight();
                            headerView.updateMargin(-headerHeight);
                            headerView.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                        }
                    });
        }
        return headerView;
    }

    @Override
    public void onMotionDown(MotionEvent ev) {
        if (layouting() || mStatus != STATUS_NORMAL) {
            return;
        }
        startX = (int) ev.getX();
        startY = (int) ev.getY();
        isPulling = false;
    }

    @Override
    public boolean onMotionMove(MotionEvent ev) {
        if (layouting() || mStatus == STATUS_REFRESHING || mStatus == STATUS_FINSHING) {
            return false;
        }
        if (startY == -1) {
            startX = (int) ev.getX();
            startY = (int) ev.getY();
            isPulling = false;
            return false;
        }
        endX = (int) ev.getX();
        endY = (int) ev.getY();
        int deltY = endY - startY - CRITICALSIZE;
        Log.d("RefreshHandler", "isFirst = " + viewExecutor.firstItemIsVisible());
        if ((viewExecutor.firstItemIsVisible() && deltY > 0 && isPullDown())
                || headerView.getTopMargin() > -headerHeight) {
            if (!isPulling) {
                startPullY = endY;
            }
            isPulling = true;
            setStatus(STATUS_PULLING);
            updateHeader((int) ((endY - startPullY) / SCROLL_RATIO));
            return true;
        }
        updateHeader(0);
        headerView.reset();
        startPullY = endY;
        return false;
    }

    @Override
    public void onMotionDefault(MotionEvent ev) {
        if (layouting() || mStatus != STATUS_PULLING) {
            return;
        }
        isPulling = false;
        if (headerView.getTopMargin() > 0) {
            doRefresh();
        } else {
            setStatus(STATUS_FINSHING);
        }
        resetHeaderWhenRelease();
    }

    @Override
    public void stopRefresh() {
        if (mStatus == STATUS_REFRESHING) {
            setStatus(STATUS_FINSHING);
            finishRefreshWithScaleAnimation();
        }
    }

    private boolean layouting() {
        return headerHeight == -1;
    }

    private void setStatus(int status) {
        if (mStatus == status) {
            return;
        }
        mStatus = status;
    }

    private void doRefresh() {
        setStatus(STATUS_REFRESHING);
        viewExecutor.onRefresh();
        headerView.showImg(RefreshHeaderView.IMGKEY_LOADING);
        headerView.animateLoading();
    }

    private boolean isPullDown() {
        return endY - startY > 0;
    }

    private void updateHeader(int margin) {
        if (margin < 0) {
            margin = 0;
        }
        int curMargin = -headerHeight + margin;
        headerView.updateMargin(curMargin);
        if (mStatus == STATUS_PULLING) {
            headerView.showImg(RefreshHeaderView.IMGKEY_LOADING);
            headerView.animateUporDown(margin);
            if (curMargin > 0) {
                headerView.setHintText(R.string.refresh_release_refresh);
            } else {
                headerView.setHintText(R.string.refresh_pulling);
            }
        } else {
            headerView.setHintText(R.string.refresh_normal);
        }
    }

    private void onRefreshFinish() {
        updateHeader(0);
        setStatus(STATUS_NORMAL);
        headerView.reset();
    }

    private void resetHeaderWhenRelease() {
        if (mStatus == STATUS_REFRESHING) {
            updateHeader(headerHeight);
        } else {
            scrollToFinish();
        }
    }

    private void finishRefreshWithScaleAnimation() {
        final int sY = headerView.getTopMargin() + headerHeight;
        if (sY <= 0) {
            onRefreshFinish();
            return;
        }
        headerView.showImgAll();
        headerView.animateScale(new RefreshHeaderView.ScaleAniListener() {
            @Override
            public void onScaleAniFinish() {
                headerView.showImg(RefreshHeaderView.IMGKEY_NORMAL);
                scrollToFinish();
            }
        });
    }

    private void scrollToFinish() {
        final int sY = headerView.getTopMargin() + headerHeight;
        if (sY <= 0) {
            onRefreshFinish();
            return;
        }
        int scaleTime = DURATION * sY / headerHeight;
        viewExecutor.startScroll(0, sY, 0, -sY, scaleTime, new OnScrollListener() {
            @Override
            public void onScroll(int scrollY, boolean isFinish) {
                if (!isFinish) {
                    updateHeader(scrollY);
                } else {
                    onRefreshFinish();
                }
            }
        });
    }

    public interface ViewExecutor {
        boolean firstItemIsVisible();

        void startScroll(int startX, int startY, int dx, int dy, int duration, OnScrollListener scrollListener);

        void onRefresh();

        void onSelectionFirst();
    }

    public interface OnScrollListener {
        void onScroll(int scrollY, boolean isFinish);
    }
}
