package com.vince.aframe.business;

import android.os.Bundle;

import com.vince.aframe.R;
import com.vince.aframe.base.net.protocol.listener.IRequestListener;
import com.vince.aframe.base.ui.BaseActivity;
import com.vince.aframe.business.test.network.NetTestResponse;
import com.vince.aframe.business.test.network.TestNetProto;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestTestNetData();
    }

    private void requestTestNetData(){
        TestNetProto proto = new TestNetProto(new IRequestListener() {
            @Override
            public void onSuccess(Object response) {
                if(response instanceof NetTestResponse){
                    NetTestResponse respone = (NetTestResponse) response;
                }
            }

            @Override
            public void onFailure() {
                int a;

            }
        });
        proto.send();
    }
}
