package com.test.plugin;

import android.app.Application;
import android.util.Log;

import com.test.plugin.utils.ClassLoaderTest;

public class PluginApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ClassLoader classLoader = getClassLoader();
        int i = 1;
        if (classLoader != null) {
            Log.d("ClassLoader-", i + "   " + classLoader.toString());
            while (classLoader.getParent() != null) {
                classLoader = classLoader.getParent();
                Log.d("ClassLoader-", i + "   " + classLoader.toString());
            }
        }
        System.out.println("####PluginApplication"+classLoader.toString());
    }
}
