package com.vince.aframe.base.ui.widgets.refresh;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;

import com.vince.aframe.R;
import com.vince.aframe.base.tools.ScreenUtil;

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
//          headerView.setOnDataRefreshFinishListener(this);
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
            headerView.setHintColor(R.color.color_999999);
        }
        return headerView;
    }

    private void setStatus(int status) {
        if (mStatus == status) {
            return;
        }
//        switch (status) {
//            case STATUS_NORMAL:
//                break;
//            case STATUS_PULLING:
//                break;
//            case STATUS_REFRESHING:
//                break;
//            case STATUS_FINSHING:
//                break;
//        }
        mStatus = status;
    }

    @Override
    public void onMotionDown(MotionEvent ev) {
        if (mStatus != STATUS_NORMAL) {
            return;
        }
        startX = (int) ev.getX();
        startY = (int) ev.getY();
    }

    @Override
    public boolean onMotionMove(MotionEvent ev) {
        if (mStatus == STATUS_REFRESHING || mStatus == STATUS_FINSHING) {
            return false;
        }
        if (startY == -1) {
            startX = (int) ev.getX();
            startY = (int) ev.getY();
            return false;
        }
        endX = (int) ev.getX();
        endY = (int) ev.getY();
        int deltY = endY - startY - CRITICALSIZE;
        if ((deltY <= 0 || !isPullDown()) && headerView.getTopMargin() <= -headerHeight) {
            return false;
        }
        if (viewExecutor.firstItemIsVisible()) {
            setStatus(STATUS_PULLING);
            updateHeader((int) (deltY / SCROLL_RATIO));
            return true;
        }
        return false;
    }

    @Override
    public void onMotionDefault(MotionEvent ev) {
        if (mStatus != STATUS_PULLING) {
            return;
        }
        if (mStatus == STATUS_PULLING && headerView.getTopMargin() > 0) {
            setStatus(STATUS_REFRESHING);
            viewExecutor.onRefresh();
        } else {
            setStatus(STATUS_FINSHING);
        }
        resetHeaderWhenRelease();
    }

    private boolean isScrolledOnY() {
        return Math.abs(endY - startY) > Math.abs(endX - startX);
    }

    private boolean isPullDown() {
        return endY - startY > 0;
    }

    private void updateHeader(int margin) {
        int curMargin = -headerHeight + margin;
        headerView.updateMargin(curMargin);
        if (mStatus == STATUS_PULLING) {
            if (curMargin > 0) {
                headerView.setHintText(R.string.refresh_release_refresh);
            } else {
                headerView.setHintText(R.string.refresh_pulling);
            }
        } else {
            headerView.setHintText(R.string.refresh_normal);
        }
//        viewExecutor.onSelectionFirst();
    }

    private void onRefreshFinish() {
        updateHeader(0);
        setStatus(STATUS_NORMAL);
    }

    private void resetHeaderWhenRelease() {
        if (mStatus == STATUS_REFRESHING) {
            updateHeader(headerHeight);
        } else {
            hideHeader();
        }
    }

    private void hideHeader() {
        int sY = headerView.getTopMargin() + headerHeight;
        if (sY <= 0) {
            onRefreshFinish();
        }
        viewExecutor.startScroll(0, sY, 0, -sY, DURATION, new OnScrollListener() {
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

    @Override
    public void stopRefresh() {
        if (mStatus == STATUS_REFRESHING) {
            setStatus(STATUS_FINSHING);
            hideHeader();
        }
    }

    public interface ViewExecutor {
        //        void init(Context context);
//        void enableLoad(Context context);
        boolean firstItemIsVisible();

        //        boolean lastItemIsVisible();
        void startScroll(int startX, int startY, int dx, int dy, int duration, OnScrollListener scrollListener);

        void onRefresh();

        //        void onLoad();
//        void onInvalidate();
        void onSelectionFirst();
//        void setScrolling(boolean scrolling);
    }

    public interface OnScrollListener {
        void onScroll(int scrollY, boolean isFinish);
    }
}
