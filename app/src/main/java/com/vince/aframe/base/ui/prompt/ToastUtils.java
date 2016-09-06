package com.vince.aframe.base.ui.prompt;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vince.aframe.R;
import com.vince.aframe.app.AFApp;

/**
 * Created by tianweixin on 2016/09/05.
 */
public class ToastUtils {

    public static void showToast(String text, int duration) {
        getToast(text, duration).show();
    }

    public static void showToast(int textId, int duration) {
        getToast(textId, duration).show();
    }

    private static Toast getToast(String text, int duration) {
        Context context = AFApp.getAppContext();
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.custom_toast, null);
        TextView tv = (TextView) v.findViewById(R.id.message);
        Toast toast = new Toast(context);
        tv.setText(text);
        toast.setView(v);
        toast.setDuration(duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        return toast;
    }

    private static Toast getToast(int textId, int duration) {
        String text = AFApp.getAppContext().getString(textId);
        return getToast(text, duration);
    }


}
