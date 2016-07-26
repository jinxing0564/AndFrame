package com.vince.aframe.base.net;

import com.android.volley.Request;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by tianweixin on 2016-7-26.
 */
public interface IProtocol {
    int getMethod();

    Request getRequest();

    Map<String, String> getPubParam();

    Map<String, String> getExtraParam();

    String getOperation();

    IRequestListener getListener();

    Type getResponseClass();

    void cancel();

//    boolean isMockEnabled();

//    void setMockEnabled(boolean mockEnabled);

//    Object mockSuccess();

//    Object mockFailure();

//    boolean isMockSuccessBranch();

//    void setMockSuccessBranch(boolean mockSuccessBranch);
}
