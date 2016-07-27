package com.vince.aframe.base.tools;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by tianweixin on 2016-7-27.
 */
public class GsonUtil {
    private static GsonUtil gsonUtil;
    private Gson gson;
    private GsonUtil(){
        gson = new Gson();
    }
    public static GsonUtil getInstance(){
        if(gsonUtil == null){
            gsonUtil = new GsonUtil();
        }
        return gsonUtil;
    }

    private Object objectFromJson_Ety(String json, Type clazz){
        Object ret = gson.fromJson(json, clazz);
        return ret;
    }

    public static Object objectFromJson(JSONObject json, Type clazz){
        return  getInstance().objectFromJson_Ety(json.toString(), clazz);
    }
}
