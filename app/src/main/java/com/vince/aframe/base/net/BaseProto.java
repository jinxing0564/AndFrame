package com.vince.aframe.base.net;

import com.android.volley.Request;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tianweixin on 2016-7-26.
 */
public abstract class BaseProto implements IProtocol {

    /**
     * 用于设定http协议方式：POST GET PUT
     */
    @Override
    public int getMethod() {
        return Request.Method.POST;
    }

    @Override
    public Request getRequest() {
        AFJsonRequest request = new AFJsonRequest(getMethod(), getUrl(),
                getRequestBody(), getSuccessListener(), getFailureListener());
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
        //TODO: 取消协议，不要回调listener

    }

    @Override
    public abstract IRequestListener getListener();

    /**
     * 返回Response的class，用于gson获取Response对象
     */
    @Override
    public abstract Type getResponseClass();

    public String getUrl() {
        //TODO：env为环境，DEV，TEST，PROD等
        String env = "";
        return "" + getOperation(); // 获取ENV参数添加上来
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
        return new ResponseSuccessListener(getListener());
    }

    public ResponseFailureListener getFailureListener() {
        return new ResponseFailureListener(getListener());
    }

    public void send() {
        HTTPClient.sendRequest(getRequest());
    }


}
