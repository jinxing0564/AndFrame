package com.vince.aframe.base.net;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by tianweixin on 2016-7-26.
 */
public class ResponseFailureListener implements Response.ErrorListener {
    IRequestListener listener;

    public ResponseFailureListener(IRequestListener listener) {
        this.listener = listener;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (listener != null) {
            listener.onFailure();
        }

    }
}
