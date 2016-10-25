package com.vince.andframe.base.ui.widgets.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vince.aframe.R;
import com.vince.andframe.base.tools.ScreenUtil;
import com.vince.andframe.base.tools.ViewShowMutexHandler;

/**
 * Created by tianweixin on 2016-8-29.
 */

public class RefreshHeaderView extends LinearLayout {

    public static final int IMGKEY_NORMAL = 0x1;
    public static final int IMGKEY_LOADING = 0x2;

    private LinearLayout mContainer;
    private ImageView ivNormal;
    private ImageView ivLoading;
    private TextView tvHint;
    private int topMargin;
    private ViewShowMutexHandler mutexHandler;
    private int imgMuteKey;

    private final int ROTATE_ANIM_DURATION = 500;//动画旋转完成时间
    private final int SCALE_MAGNIFY_ANIM_DURATION = 300;  //动画放大完成时间
    private final int SCALE_NARROWING_ANIM_DURATION = 500;  //动画缩小完成时间
    private final float DISTANCE_TO_ANGLE_FACTOR = 4f; //下拉距离转换为loading图片选装角度的因子
    float rotateLastAngle = 0;

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
        setPadding(ScreenUtil.dipToPixel(5), 0, ScreenUtil.dipToPixel(5), 0);

        mutexHandler = new ViewShowMutexHandler();
        imgMuteKey = R.id.imageView;
        mutexHandler.showInLayout(imgMuteKey, ivLoading);
        mutexHandler.showInLayout(imgMuteKey, ivNormal);
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

    public void showImg(int imageKey) {
        switch (imageKey) {
            case IMGKEY_NORMAL:
                mutexHandler.showInLayout(imgMuteKey, ivNormal);
                break;
            case IMGKEY_LOADING:
                mutexHandler.showInLayout(imgMuteKey, ivLoading);
                break;
            default:
                break;
        }
    }

    public void showImgAll() {
        mutexHandler.showAll(imgMuteKey);
    }

    public void animateLoading() {
        clearAnim();
        ivLoading.setAnimation(getAnimationLoading());
    }

    public void animateUporDown(int distance) {
        float targetAngle = getAngleFromDistance(distance);
        RotateAnimation pullRotateAnimation = getPullRotateAnimation(rotateLastAngle, targetAngle);
        clearAnim();
        ivLoading.setAnimation(pullRotateAnimation);
        rotateLastAngle = targetAngle;
    }

    public void animateScale(final ScaleAniListener scaleAniListener) {
        ScaleAnimation aninationMagnify = getAninationMagnify();
        aninationMagnify.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (scaleAniListener != null) {
                    scaleAniListener.onScaleAniFinish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        clearAnim();
        ivNormal.setAnimation(aninationMagnify);
        ivLoading.setAnimation(getAninationNarrowing());
    }

    public void reset() {
        clearAnim();
        rotateLastAngle = 0;
        showImg(IMGKEY_NORMAL);
    }

    private RotateAnimation getAnimationLoading() {
        RotateAnimation animationLoading = new RotateAnimation(0, 359f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationLoading.setDuration(ROTATE_ANIM_DURATION);
        animationLoading.setRepeatCount(-1);
        animationLoading.setInterpolator(new LinearInterpolator());
        animationLoading.setFillAfter(true);
        return animationLoading;
    }

    private ScaleAnimation getAninationMagnify() {
        ScaleAnimation aninationMagnify = new ScaleAnimation(0, 1.0f, 0, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        aninationMagnify.setDuration(SCALE_MAGNIFY_ANIM_DURATION);
        aninationMagnify.setFillAfter(true);
        return aninationMagnify;
    }

    private ScaleAnimation getAninationNarrowing() {
        ScaleAnimation aninationNarrowing = new ScaleAnimation(1.0f, 0, 1.0f, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        aninationNarrowing.setDuration(SCALE_NARROWING_ANIM_DURATION);
        aninationNarrowing.setFillAfter(true);
        return aninationNarrowing;
    }

    private RotateAnimation getPullRotateAnimation(float startAngle, float endAngle) {
        RotateAnimation animationRotate = new RotateAnimation(startAngle, endAngle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationRotate.setDuration(0);
        animationRotate.setFillAfter(true);
        return animationRotate;
    }

    private float getAngleFromDistance(int distance) {
        int distanceDp = ScreenUtil.pixelToDip(distance);
        return distanceDp * DISTANCE_TO_ANGLE_FACTOR;
    }

    private void clearAnim() {
        ivLoading.clearAnimation();
        ivNormal.clearAnimation();
    }

    interface ScaleAniListener {
        void onScaleAniFinish();
    }


}
