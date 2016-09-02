package com.vince.aframe.demo.network;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.vince.aframe.R;
import com.vince.aframe.base.net.protocol.listener.IRequestListener;
import com.vince.aframe.base.ui.activity.BaseActivity;

/**
 * Created by tianweixin on 2016-7-28.
 */
public class NetTestActivity extends BaseActivity {

    private TextView tvJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_network);
        tvJson = (TextView) findViewById(R.id.tv_json);
        requestTestNetData();
    }

    private void requestTestNetData() {
        TestNetProto proto = new TestNetProto(new IRequestListener() {
            @Override
            public void onSuccess(Object response) {
                if (response instanceof NetTestResponse) {
                    NetTestResponse respone = (NetTestResponse) response;
                    VersionModel model = respone.result;
                    String value = "latestVersionCode = " + model.getLatestVersionName() + "\n\n"
                            + "minSupportedVersionCode = " + model.getMinSupportedVersionCode() + "\n\n"
                            + "appUrl = " + model.getAppUrl() + "\n\n"
                            + "message = " + model.getMessage();
                    tvJson.setText(value);
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(NetTestActivity.this, "network test failed", Toast.LENGTH_SHORT).show();
            }
        });
        proto.send();
    }

}
