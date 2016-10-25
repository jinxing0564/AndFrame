package com.vince.andframe.base.net.response.listener;

import com.android.volley.Response;
import com.vince.andframe.base.net.protocol.listener.IRequestListener;
import com.vince.andframe.base.net.response.BaseResponse;
import com.vince.andframe.base.tools.GsonUtil;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by tianweixin on 2016-7-26.
 */
public class ResponseSuccessListener extends ResponseCancelable implements Response.Listener<JSONObject> {
    IRequestListener listener;
    Type clazz;

    public ResponseSuccessListener(IRequestListener listener, Type clazz) {
        this.listener = listener;
        this.clazz = clazz;
    }

    @Override
    public void onResponse(JSONObject jsonResponse) {
        if (cancel || listener == null) {
            return;
        }
        BaseResponse response;
        try {
            response = (BaseResponse) GsonUtil.objectFromJson(jsonResponse, clazz);
        } catch (Exception e) {
            listener.onFailure();
            return;
        }
        //这里处理resultCode是否异常，如果异常就fail
        //异常的逻辑需要根据具体项目规定来设定
        if (response.code == null) {
            listener.onFailure();
        }

        listener.onSuccess(response);
    }
}
