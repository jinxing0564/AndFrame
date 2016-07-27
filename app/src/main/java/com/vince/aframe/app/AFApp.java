package com.vince.aframe.app;

import android.app.Application;
import android.content.Context;

/**
 * Appliciton
 * 
 * Created by tianweixin on 2016-7-26.
 */
public class AFApp extends Application {
    private static AFApp mApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        if(mApplication == null){
            mApplication = this;
        }
    }

    public static AFApp getInstance(){
        return mApplication;
    }

    public static Context getAppContext(){
        return mApplication;
    }
}
