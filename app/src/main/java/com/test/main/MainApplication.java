package com.test.main;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.didi.virtualapk.PluginManager;


public class MainApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        PluginManager.getInstance(base).init();
    }
}
