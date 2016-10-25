package com.vince.andframe.base.net.protocol;

import com.android.volley.Request;
import com.vince.andframe.base.net.HTTPClient;
import com.vince.andframe.base.net.env.Environment;
import com.vince.andframe.base.net.protocol.listener.IRequestListener;
import com.vince.andframe.base.net.response.listener.ResponseFailureListener;
import com.vince.andframe.base.net.response.listener.ResponseSuccessListener;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tianweixin on 2016-7-26.
 */
public abstract class BaseProto implements IProtocol {

    private IRequestListener listener;
    private AFJsonRequest request;


    public BaseProto(IRequestListener listener) {
        this.listener = listener;
    }

    /**
     * 用于设定http协议方式：POST GET PUT
     */
    @Override
    public int getMethod() {
        return Request.Method.POST;
    }

    @Override
    public Request getRequest() {
        if (request == null) {
            request = new AFJsonRequest(getMethod(), getUrl(),
                    getRequestBody(), getSuccessListener(), getFailureListener());
        }
        return request;
    }

    @Override
    public Map<String, String> getPubParam() {
        //TODO: 用户信息，设备信息，版本号...
        return null;
    }

    @Override
    public abstract Map<String, String> getExtraParam();


    @Override
    public abstract String getOperation();

    @Override
    public void cancel() {
        if (request != null) {
            request.cancel();
        }
    }

    @Override
    public IRequestListener getListener() {
        return listener;
    }

    /**
     * 返回Response的class，用于gson获取Response对象
     */
    @Override
    public abstract Type getResponseClass();

    public String getUrl() {
        String envURL = Environment.getInstance().getBaseURL();
        return envURL + getOperation();
    }

    public String getRequestBody() {
        Map<String, String> allParam = new HashMap<String, String>();
        Map<String, String> pubParam = getPubParam();
        if (pubParam != null) {
            allParam.putAll(pubParam);
        }
        Map<String, String> extraParam = getExtraParam();
        if (extraParam != null) {
            allParam.putAll(extraParam);
        }
        JSONObject ret = new JSONObject(allParam);
        return ret.toString();
    }

    public ResponseSuccessListener getSuccessListener() {
        return new ResponseSuccessListener(getListener(), getResponseClass());

    }

    public ResponseFailureListener getFailureListener() {
        return new ResponseFailureListener(getListener());
    }

    public void send() {
        HTTPClient.sendRequest(getRequest());
    }


}
