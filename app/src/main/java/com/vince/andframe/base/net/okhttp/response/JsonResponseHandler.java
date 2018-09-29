package com.vince.andframe.base.net.okhttp.response;

import com.vince.andframe.base.net.okhttp.listener.IRequestListener;

import okhttp3.Response;

/**
 * Created by by tianweixin on 2018/9/28.
 */
public class JsonResponseHandler implements IResponseHandler {

    private IRequestListener listener;

    public JsonResponseHandler(IRequestListener listener) {
        this.listener = listener;
    }

    @Override
    public void handleSuccess(Response response) {

    }

    @Override
    public void handleFailure() {

    }
}
