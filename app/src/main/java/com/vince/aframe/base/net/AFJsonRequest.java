package com.vince.aframe.base.net;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by tianweixin on 2016-7-26.
 */
public class AFJsonRequest extends JsonObjectRequest {

    public AFJsonRequest(int method, String url, String requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    public AFJsonRequest(BaseProto proto) {
        super(proto.getMethod()
                , proto.getUrl()
                , proto.getRequestBody()
                , proto.getSuccessListener()
                , proto.getFailureListener());
    }

}
