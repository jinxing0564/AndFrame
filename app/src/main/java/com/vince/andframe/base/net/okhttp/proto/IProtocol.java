package com.vince.andframe.base.net.okhttp.proto;


import com.vince.andframe.base.net.okhttp.listener.IRequestListener;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by tianweixin on 2016-7-26.
 */
public interface IProtocol {

    String getBaseUrl();

    String getServiceId();

    String getTag();

    HashMap<String,String> getHeaders();

    void addPubParam(JSONObject json);

    void addExtraParam(JSONObject json);

    Request getRequest();

    void setCall(Call call);

    IRequestListener getListener();

    /**
     * 返回Response的class，用于gson获取Response对象
     */
    Type getResponseClass();

    void cancel();

    boolean isCancelled();

//    boolean isMockEnabled();

//    void setMockEnabled(boolean mockEnabled);

//    Object mockSuccess();

//    Object mockFailure();

//    boolean isMockSuccessBranch();

//    void setMockSuccessBranch(boolean mockSuccessBranch);
}
