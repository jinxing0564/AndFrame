package com.vince.aframe.business;

import android.os.Bundle;

import com.vince.aframe.R;
import com.vince.aframe.base.ui.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toast.makeText(this, Environment.getInstance().getBaseURL(),Toast.LENGTH_LONG).show();
    }
}
