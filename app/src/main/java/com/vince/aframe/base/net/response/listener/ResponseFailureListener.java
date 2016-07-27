package com.vince.aframe.base.net.response.listener;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.vince.aframe.base.net.protocol.listener.IRequestListener;

/**
 * Created by tianweixin on 2016-7-26.
 */
public class ResponseFailureListener extends ResponseCancelable implements Response.ErrorListener {
    IRequestListener listener;

    public ResponseFailureListener(IRequestListener listener) {
        this.listener = listener;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (cancel || listener == null) {
            return;
        }
        listener.onFailure();
    }
}
