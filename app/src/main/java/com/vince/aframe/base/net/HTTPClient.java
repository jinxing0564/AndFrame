package com.vince.aframe.base.net;

import com.android.volley.Request;

/**
 * Created by tianweixin on 2016-7-26.
 */
public class HTTPClient {

    public static void sendRequest(Request request){
        VolleyManager.getInstance().addRequest(request);
    }

    public static void sendProto(BaseProto proto){
        Request request = proto.getRequest();
        sendRequest(request);
    }
}
