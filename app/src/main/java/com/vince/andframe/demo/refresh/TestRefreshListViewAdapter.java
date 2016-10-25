package com.vince.andframe.demo.refresh;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vince.aframe.R;

/**
 * Created by tianweixin on 2016-9-2.
 */

public class TestRefreshListViewAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            holder.textView = new TextView(parent.getContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.textView.setLayoutParams(params);
            holder.textView.setPadding(20, 80, 20, 80);
            holder.textView.setBackgroundResource(R.color.white);
            convertView = holder.textView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText("This is Postion " + position);
        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }
}
