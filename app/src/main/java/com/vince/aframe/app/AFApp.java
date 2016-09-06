package com.vince.aframe.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import java.lang.ref.WeakReference;

/**
 * Appliciton
 * <p>
 * Created by tianweixin on 2016-7-26.
 */
public class AFApp extends Application implements Application.ActivityLifecycleCallbacks {
    private static AFApp mApplication;
    private WeakReference<Activity> currentActivityReference;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mApplication == null) {
            mApplication = this;
        }
    }

    public static AFApp getInstance() {
        return mApplication;
    }

    public Activity getCurrentActivity() {
        if (currentActivityReference != null && currentActivityReference.get() != null) {
            return currentActivityReference.get();
        }
        return null;
    }

    public static Context getAppContext() {
        return mApplication;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivityReference = new WeakReference<>(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
