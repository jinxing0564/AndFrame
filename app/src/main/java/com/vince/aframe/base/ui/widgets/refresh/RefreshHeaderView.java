package com.vince.aframe.base.ui.widgets.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vince.aframe.R;

/**
 * Created by tianweixin on 2016-8-29.
 */

public class RefreshHeaderView extends LinearLayout {

    private LinearLayout mContainer;
    private ImageView ivNormal;
    private ImageView ivLoading;
    private TextView tvHint;
    private int topMargin;

    public RefreshHeaderView(Context context) {
        super(context);
        init(context);
    }

    public RefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.refresh_header_view, this, true);
        mContainer = (LinearLayout) view.findViewById(R.id.listview_header_content);
        ivNormal = (ImageView) view.findViewById(R.id.img_nomal);
        ivLoading = (ImageView) view.findViewById(R.id.img_loading);
        tvHint = (TextView) view.findViewById(R.id.text_hint);
        setPadding(10, 0, 10, 0);
    }

    public void setHintText(int txtRes) {
        tvHint.setText(txtRes);
    }

    public void setHintColor(int colorRes) {
        tvHint.setTextColor(getContext().getResources().getColor(colorRes));
    }

    public void updateMargin(int margin) {
        topMargin = margin;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mContainer.getLayoutParams();
        params.topMargin = topMargin;
        mContainer.setLayoutParams(params);
    }

    public int getTopMargin() {
        return topMargin;
    }
}
