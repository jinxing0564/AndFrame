package com.vince.aframe.base.tools;

import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tianweixin on 2016-8-19.
 * <p>
 * 存储需要显示互斥的多个view组
 */

public class ViewShowMutexHandler {
    //List:存储需要不可同时显示的view
    //Map:List组成的Map
    private Map<Integer, List<View>> viewMap;

    public ViewShowMutexHandler() {
        viewMap = new HashMap<>();
    }

    private List<View> putViewMap(int muteKey, View view) {
        List<View> views = viewMap.get(muteKey);
        if (views == null) {
            views = new ArrayList<>();
            views.add(view);
            viewMap.put(muteKey, views);
        } else {
            if (!views.contains(view)) {
                views.add(view);
            }
        }
        return views;
    }

    /**
     * @param muteKey: 互斥的标识，可以使用某个view的id
     * @param view:    需要显示的View
     */
    public void showInLayout(int muteKey, View view) {
        List<View> views = putViewMap(muteKey, view);
        for (View v : views) {
            if (v == view) {
                v.setVisibility(View.VISIBLE);
            } else {
                v.setVisibility(View.GONE);
            }
        }
    }

    public void showAll(int muteKey) {
        visiableAll(muteKey, View.VISIBLE);
    }

    public void goneAll(int muteKey) {
        visiableAll(muteKey, View.GONE);
    }

    private void visiableAll(int muteKey, int visable) {
        List<View> views = viewMap.get(muteKey);
        for (View v : views) {
            v.setVisibility(visable);
        }
    }

}
