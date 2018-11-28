package com.vince.andframe.demo.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vince.aframe.R;

/**
 * Created by by tianweixin on 2018/11/28.
 */
public class TabDemoFragment extends Fragment {
    private String text;
    Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_demo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = (Button) view.findViewById(R.id.button);
        button.setText(text);
    }

    public void setText(String text) {
        this.text = text;
    }

    public static TabDemoFragment newInstance(String text) {
        TabDemoFragment fragment = new TabDemoFragment();
        fragment.text = text;
        return fragment;
    }
}
