package com.vince.aframe.base.net.response.listener;

/**
 * Created by tianweixin on 2016-7-27.
 */
public class ResponseCancelable {
    public boolean cancel = false;

    public void cancel(){
        cancel = true;
    }
}
