package com.vince.aframe.base.net;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.vince.aframe.app.AFApp;

/**
 * Created by tianweixin on 2016-7-26.
 */
public class VolleyManager {
    private static VolleyManager volleyManager;
    private RequestQueue mRequestQueue;

    private VolleyManager(){
        mRequestQueue = Volley.newRequestQueue(AFApp.getAppContext());
    }

    public static VolleyManager getInstance(){
        if(volleyManager == null){
            volleyManager = new VolleyManager();
        }
        return volleyManager;
    }

    public void addRequest(Request request){
        mRequestQueue.add(request);
    }
}
