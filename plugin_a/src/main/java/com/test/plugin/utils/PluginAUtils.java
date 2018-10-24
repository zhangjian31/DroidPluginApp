package com.test.plugin.utils;

import android.content.Context;
import android.widget.Toast;

public class PluginAUtils {
    public void showToastInfo(Context context) {
        Toast.makeText(context, "This is from pluginA", Toast.LENGTH_SHORT).show();
    }
}
