package com.vince.aframe.base.net;

import org.json.JSONObject;

/**
 * Created by tianweixin on 2016-7-26.
 */
public interface IRequestListener {
    void onSuccess(JSONObject response);
    void onFailure();
}
