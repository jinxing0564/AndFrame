package com.vince.andframe.base.net.env;

import android.text.TextUtils;

import com.vince.andframe.app.AFApp;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by tianweixin on 2016-7-27.
 */
public class Environment {
    private static Environment environment;
    private Properties prop;

    public static String ENV_PATH = "config/env.properties";
    public static String KEY_ENV = "ENV";
    public static String KEY_ENV_TEST = "TEST";
    public static String KEY_ENV_DEV = "DEV";
    public static String KEY_ENV_PROD = "PROD";
    public static String BASE_URL = "BASE_URL";

    private Environment() {
        prop = new Properties();
        loadEnv();
    }

    public static Environment getInstance() {
        if (environment == null) {
            environment = new Environment();
        }
        return environment;
    }

    private void loadEnv() {
        try {
            prop.load(AFApp.getAppContext().getAssets().open(ENV_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCurEnv() {
        String curEnv = prop.getProperty(KEY_ENV);
        if (!curEnv.equals(KEY_ENV_TEST)
                && !curEnv.equals(KEY_ENV_DEV)
                && !curEnv.equals(KEY_ENV_PROD)) {
            throw new RuntimeException("can't recognize env: " + curEnv);
        }
        return curEnv;
    }

    public String getBaseURL() {
        String baseURL = prop.getProperty(BASE_URL + "." + getCurEnv());
        if (TextUtils.isEmpty(baseURL)) {
            throw new RuntimeException("Base URL is null");
        }
        return baseURL;
    }
}
