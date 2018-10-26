package com.test.main;

import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDexApplication;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.RePluginConfig;
import com.test.main.utils.TestUtils;



public class MainApplication extends MultiDexApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        RePlugin.App.attachBaseContext(this, getConfig());
        RePlugin.enableDebugger(base, true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RePlugin.App.onCreate();
        try {
            getClassLoader().loadClass("org.greenrobot.eventbus.EventBus");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        TestUtils.print();
        try {
            getClassLoader().loadClass("org.greenrobot.eventbus.EventBus");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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

    private RePluginConfig getConfig() {
        RePluginConfig config = new RePluginConfig();
        config.setUseHostClassIfNotFound(true);
        config.setVerifySign(false);
        config.setMoveFileWhenInstalling(true);
        config.setPrintDetailLog(true);
        return config;

    }
}
