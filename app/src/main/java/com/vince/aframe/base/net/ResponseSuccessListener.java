package com.vince.aframe.base.net;

import com.android.volley.Response;

import org.json.JSONObject;

/**
 * Created by tianweixin on 2016-7-26.
 */
public class ResponseSuccessListener implements Response.Listener<JSONObject> {
    IRequestListener listener;

    public ResponseSuccessListener(IRequestListener listener) {
        this.listener = listener;
    }

    @Override
    public void onResponse(JSONObject response) {
        if (listener != null) {
            listener.onSuccess(response);
        }
    }
}
