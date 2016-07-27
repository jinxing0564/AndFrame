package com.vince.aframe.base.net.protocol;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vince.aframe.base.net.response.listener.ResponseFailureListener;
import com.vince.aframe.base.net.response.listener.ResponseSuccessListener;

import org.json.JSONObject;

/**
 * Created by tianweixin on 2016-7-26.
 */
public class AFJsonRequest extends JsonObjectRequest {
    private ResponseSuccessListener successListener;
    private ResponseFailureListener failureListener;

    public AFJsonRequest(int method, String url, String requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
        successListener = (ResponseSuccessListener) listener;
        failureListener = (ResponseFailureListener) errorListener;
    }

    public AFJsonRequest(BaseProto proto) {
        super(proto.getMethod()
                , proto.getUrl()
                , proto.getRequestBody()
                , proto.getSuccessListener()
                , proto.getFailureListener());
        successListener = proto.getSuccessListener();
        failureListener = proto.getFailureListener();
    }

    @Override
    public void cancel(){
        if (successListener != null) {
            successListener.cancel();
        }
        if (failureListener != null) {
            failureListener.cancel();
        }
        super.cancel();
    }

}
