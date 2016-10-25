package com.vince.andframe.demo.network;

import com.android.volley.Request;
import com.vince.andframe.base.net.protocol.BaseProto;
import com.vince.andframe.base.net.protocol.listener.IRequestListener;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by tianweixin on 2016-7-28.
 */
public class TestNetProto extends BaseProto {
    @Override
    public int getMethod(){
        return Request.Method.GET;
    }

    public TestNetProto(IRequestListener listener){
        super(listener);
    }
    @Override
    public Map<String, String> getExtraParam() {
        return null;
    }

    @Override
    public String getOperation() {
        return "hdb/cms/cms_content/version_android";
    }

    @Override
    public Type getResponseClass() {
        return NetTestResponse.class;
    }
}
