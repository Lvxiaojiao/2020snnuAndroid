package com.hodo.application;

import android.app.Application;

import com.mob.MobSDK;

/**
 * Created by liuyihao on 15/12/29.
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
        MobSDK.init(this);
    }


    private void init() {

    }

    public static MyApplication getInstance() {
        return instance;
    }
}
