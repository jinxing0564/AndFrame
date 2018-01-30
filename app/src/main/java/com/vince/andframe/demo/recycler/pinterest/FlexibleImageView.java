package com.vince.andframe.demo.recycler.pinterest;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by tianweixin on 2017-4-6.
 */

public class FlexibleImageView extends LinearLayout {
    ImageView image;

    private int downX = -1;
    private int downY = -1;

    private int touchSlop;
    private boolean flexible;
    private static final int TAN = 2;
    private int bitmapWidth;
    private int bitmapHeight;

    public FlexibleImageView(Context context) {
        super(context);
        init(context);
    }

    public FlexibleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FlexibleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.setGravity(Gravity.CENTER);
        this.setOrientation(VERTICAL);
        image = new ImageView(context);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        image.setLayoutParams(lp);
        removeAllViews();
        addView(image);

        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setImage(Bitmap bitmap) {
        image.setImageBitmap(bitmap);
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = x;
                downY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (downX <= 0) {
                    downX = x;
                    downY = y;
                    break;
                }
                int dX = x - downX;
                int dY = y - downY;
                if (!flexible && dY > touchSlop && dY > Math.abs(dX) * TAN) {
                    flexible = true;
                }
                if (flexible) {
                    //do work

                }

                break;
            default:
        }
        return super.onTouchEvent(event);
    }
}
