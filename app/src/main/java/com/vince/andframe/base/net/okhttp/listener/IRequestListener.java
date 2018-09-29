package com.vince.andframe.base.net.okhttp.listener;

/**
 * Created by tianweixin on 2016-7-26.
 */
public interface IRequestListener {
    void onSuccess(Object response);

    void onFailure(); //TODO: 如果需要错误信息，请添加相应参数

    void onCancel();
}
