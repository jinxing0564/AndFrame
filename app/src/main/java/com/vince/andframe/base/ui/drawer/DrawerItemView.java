package com.vince.andframe.base.ui.drawer;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.vince.andframe.base.tools.ScreenUtil;

/**
 * Created by tianweixin on 2017-3-30.
 */

public class DrawerItemView extends FrameLayout {
    private LinearLayout contentGroup;
    private LinearLayout drawerGroup;
    private int DRAWER_WIDTH = ScreenUtil.dipToPixel(100);

    int position;

    public DrawerItemView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public DrawerItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawerItemView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DrawerItemView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        drawerGroup = new LinearLayout(context);
        LayoutParams drawerLp = new LayoutParams(DRAWER_WIDTH, ViewGroup.LayoutParams.MATCH_PARENT);
//        drawerLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        drawerLp.gravity = Gravity.RIGHT;
        drawerGroup.setLayoutParams(drawerLp);
        drawerGroup.setBackgroundColor(Color.RED);
        addView(drawerGroup);

        contentGroup = new LinearLayout(context);
        LayoutParams contentLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contentGroup.setLayoutParams(contentLp);
        contentGroup.setBackgroundColor(Color.TRANSPARENT);
        contentGroup.setClickable(true);
        addView(contentGroup);

    }

    public void setContentClickListener(View.OnClickListener listener) {
        contentGroup.setOnClickListener(listener);

    }

    public void setPosition(int pos) {
        this.position = pos;
    }

    public int getPosition() {
        return position;
    }

    public void setContentView(View view, int bgColorRes) {
        if (view == null) {
            return;
        }
        contentGroup.removeAllViews();
        view.setBackgroundResource(bgColorRes);
        contentGroup.addView(view);
    }

    public void setDrawerView(View view) {
        if (view == null) {
            return;
        }
        drawerGroup.removeAllViews();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lp);
        drawerGroup.addView(view);
    }


    public int getScroll() {
        return -contentGroup.getLeft();
    }

    public void scroll(int x) {
//        contentGroup.scrollTo(x, 0);
        contentGroup.layout(-x, contentGroup.getTop(), contentGroup.getWidth() - x, contentGroup.getBottom());
    }

    public void reset() {
        scroll(0);
    }

    public int getDrawerWidth() {
        return DRAWER_WIDTH;
    }
}
