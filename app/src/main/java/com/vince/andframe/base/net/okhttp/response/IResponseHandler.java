package com.vince.andframe.base.net.okhttp.response;


import com.vince.andframe.base.net.okhttp.listener.IRequestListener;

import okhttp3.Response;

/**
 * Created by by tianweixin on 2018/9/28.
 */
public interface IResponseHandler {

    void handleSuccess(Response response);

    void handleFailure();
}
