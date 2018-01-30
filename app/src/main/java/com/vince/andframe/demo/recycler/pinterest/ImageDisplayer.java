package com.vince.andframe.demo.recycler.pinterest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by tianweixin on 2017-4-6.
 */

public class ImageDisplayer {
    private PopupWindow window;
    private Context context;

    public ImageDisplayer(Context context) {
        this.context = context;
        window = new PopupWindow(new View(context), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }


}
