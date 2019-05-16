package com.tao.demo;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2018/7/31.
 */

public class MyApplication extends Application {
    private final String LOG_TAG = "===nbiot====";
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        if (BuildConfig.DEBUG) {
//            LeakCanary.install(this);
        }
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }

    public static MyApplication getApplication() {
        return mInstance;
    }

}
