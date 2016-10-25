package com.vince.andframe.base.net;

import com.android.volley.Request;
import com.vince.andframe.base.net.protocol.BaseProto;

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
