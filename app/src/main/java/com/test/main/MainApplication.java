package com.test.main;

import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDexApplication;

import com.qihoo360.replugin.RePlugin;

public class MainApplication extends MultiDexApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        RePlugin.App.attachBaseContext(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RePlugin.App.onCreate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        RePlugin.App.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        RePlugin.App.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        RePlugin.App.onConfigurationChanged(config);
    }
}
