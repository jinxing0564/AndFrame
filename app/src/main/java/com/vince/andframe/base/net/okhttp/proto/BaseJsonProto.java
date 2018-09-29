package com.vince.andframe.base.net.okhttp.proto;

import android.net.Uri;

import com.vince.aframe.BuildConfig;
import com.vince.andframe.base.net.env.Environment;
import com.vince.andframe.base.net.okhttp.OkhttpManager;
import com.vince.andframe.base.net.okhttp.listener.IRequestListener;
import com.vince.andframe.base.net.okhttp.response.IResponseHandler;
import com.vince.andframe.base.net.okhttp.response.JsonResponseHandler;
import com.vince.andframe.base.tools.DeviceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * Created by tianweixin on 2016-7-26.
 */
public abstract class BaseJsonProto implements IProtocol {
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");

    private IRequestListener listener;
    private Call call;
    private boolean cancelled = false;

    public BaseJsonProto(IRequestListener listener) {
        this.listener = listener;
    }

    @Override
    public String getBaseUrl() {
        return Environment.getInstance().getBaseURL();
    }

    @Override
    public String getTag() {
        return null;
    }

    @Override
    public HashMap<String, String> getHeaders() {
        return new HashMap<>();
    }

    @Override
    public void setCall(Call call) {
        this.call = call;
    }

    @Override
    public Request getRequest() {
        Request.Builder builder = new Request.Builder();
        builder.url(getBaseUrl());
        builder.tag(getTag());
        initHeaders(builder);
        initBody(builder);
        return builder.build();
    }

    private void initHeaders(Request.Builder builder) {
        Iterator<Map.Entry<String, String>> iter = getHeaders().entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            String key = entry.getKey();
            String value = entry.getValue();
            builder.header(correctHeader(key), correctHeader(value));
        }
    }

    private void initBody(Request.Builder builder) {
        JSONObject obj = getBodyJson();

        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, obj.toString());
        try {
            builder.header("Content-Length", String.valueOf(body.contentLength()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        builder.post(body);
    }

    private JSONObject getBodyJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("serviceId", getServiceId());
            addPubParam(json);
            addExtraParam(json);
//            json.put("param", new EncoderModule(null).encode(json));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 目前okhttp(最新V3.4.1)header只支持ASCII字符,如果字符串符合条件直接返回原字符串,否则做编码。
     */
    protected String correctHeader(String str) {
        boolean needEncode = false;
        for (int i = 0, length = str.length(); i < length; i++) {
            char c = str.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                needEncode = true;
                break;
            }
        }

        if (needEncode) {
            String result = Uri.encode(str);
//            YDLog.e("header should use ASCII character, " + str + " has encode to" + result);
            return result;
        }
        return str;
    }

    @Override
    public void addPubParam(JSONObject param) {
        try {
            param.put("reqNo", UUID.randomUUID());
            Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            String time = (dateFormat).format(localCalendar.getTime());
            param.put("reqDate", time);

            param.put("versionCode", BuildConfig.VERSION_CODE);
            param.put("versionName", BuildConfig.VERSION_NAME);
            param.put("productCode", "YUANDIAN");
            param.put("os", "android");
            param.put("deviceId", DeviceUtil.deviceID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addExtraParam(JSONObject param) {

    }

    @Override
    public void cancel() {
        this.cancelled = true;
        this.call.cancel();
        if (this.listener != null) {
            this.listener.onCancel();
        }
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public IRequestListener getListener() {
        return listener;
    }

//
//    public ResponseSuccessListener getSuccessListener() {
//        return new ResponseSuccessListener(getListener(), getResponseClass());
//
//    }
//
//    public ResponseFailureListener getFailureListener() {
//        return new ResponseFailureListener(getListener());
//    }

    public void send() {
        IResponseHandler responseHandler = new JsonResponseHandler(getListener());
        OkhttpManager.getInstance().sendRequest(this, responseHandler);
    }


}
