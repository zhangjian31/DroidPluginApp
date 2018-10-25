package com.test.plugin.b.utils;

import android.content.Context;
import android.widget.Toast;

public class PluginBUtils {
    public void showToastInfo(Context context) {
        Toast.makeText(context, "This is from pluginB", Toast.LENGTH_SHORT).show();
    }
}
