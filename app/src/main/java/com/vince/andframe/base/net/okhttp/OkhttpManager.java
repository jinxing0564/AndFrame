package com.vince.andframe.base.net.okhttp;

import com.vince.andframe.base.net.okhttp.proto.IProtocol;
import com.vince.andframe.base.net.okhttp.response.IResponseHandler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by tianweixin on 2016-7-26.
 */
public class OkhttpManager {
    private static OkhttpManager manager;
    private OkHttpClient httpClient;

    private OkhttpManager() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10_000, TimeUnit.MILLISECONDS);
        builder.readTimeout(10_000, TimeUnit.MILLISECONDS);
        builder.writeTimeout(10_000, TimeUnit.MILLISECONDS);

//        builder.addInterceptor(new RetryInterceptor())
//                .addInterceptor(new LogInterceptor()); // 添加重试拦截器

        httpClient = builder.build();
    }

    public static OkhttpManager getInstance() {
        if (manager == null) {
            manager = new OkhttpManager();
        }
        return manager;
    }

    public void sendRequest(final IProtocol proto, final IResponseHandler responseHandler) {
        Call call = httpClient.newCall(proto.getRequest());
        proto.setCall(call);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (!proto.isCancelled()) {
                    responseHandler.handleFailure();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!proto.isCancelled()) {
                    responseHandler.handleSuccess(response);
                }
            }
        });
    }

}
