package com.test.plugin.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.loader.s.PluginServiceClient;
import com.test.eventbus.EventBean;
import com.test.plugin.Constant;
import com.test.plugin.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PluginFirstActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        showInfo();
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @SuppressLint("WrongConstant")
    private void showInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("Plugin:");
        builder.append(getPackageName());
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        builder.append("[");
        builder.append("\r\n");
        builder.append(applicationInfo.uid);
        builder.append("\r\n");
        builder.append(applicationInfo.packageName);
        builder.append("\r\n");
        builder.append(applicationInfo.processName);
        builder.append("\r\n");
        builder.append("]");
        Toast.makeText(PluginFirstActivity.this, builder.toString(), Toast.LENGTH_SHORT).show();
    }

    public void startMainService(View view) {
        Intent intent = new Intent().setPackage("com.test.main").setAction(Constant.MAIN_SERVICE_ACTION);
        startService(intent);
//        Intent intent = RePlugin.createIntent("com.test.main", "com.test.main.service.PluginService");
//        PluginServiceClient.startService(PluginFirstActivity.this, intent);
    }

    public void openMainSecondActivity(View view) {

        Intent intent = new Intent().setClassName("com.test.main", "com.test.main.activity.MainSecondActivity");
        startActivity(intent);
//        Intent intent = RePlugin.createIntent("com.test.main", "com.test.main.activity.MainSecondActivity");
//        if (!RePlugin.startActivity(PluginFirstActivity.this, intent)) {
//            Toast.makeText(PluginFirstActivity.this, "启动失败", Toast.LENGTH_LONG).show();
//        }

//        if (isActivityActionAvailable(this, Constant.MAIN_SECOND_ACTION)) {
//            Intent intent = new Intent().setAction(Constant.MAIN_SECOND_ACTION);
//            startActivity(intent);
//        } else {
//            Toast.makeText(PluginFirstActivity.this, "启动MainSecondActivity失败", Toast.LENGTH_SHORT).show();
//        }
    }

    public void sendBroadcastToMain(View view) {
        Intent intent = new Intent().setAction(Constant.MAIN_RECEIVER_ACTION);
        sendBroadcast(intent);
    }

    public static boolean isActivityActionAvailable(Context context, String action) {
        Intent intent = new Intent(action);
        return context.getPackageManager().resolveActivity(intent, 0) != null;
    }

    public static boolean isServiceActionAvailable(Context context, String action) {
        Intent intent = new Intent(action);
        return context.getPackageManager().resolveService(intent, 0) != null;
    }

    public void sendEventBus(View view) {
        ClassLoader classLoader = RePlugin.getHostClassLoader();
        try {
            Class cls = classLoader.loadClass("com.test.eventbus.EventBean");
            Constructor constructor = cls.getConstructor(String.class);
            Object obj = constructor.newInstance("plugin");
            Method method = cls.getDeclaredMethod("send");
            method.invoke(obj);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBean bean) {
        if (bean != null && !TextUtils.isEmpty(bean.getTag())) {
            Toast.makeText(PluginFirstActivity.this, "On PluginFirst:" + bean.getTag(), Toast.LENGTH_SHORT).show();
        }
    }

    public void startPluginSecond(View view) {
        Intent intent = new Intent(PluginFirstActivity.this, PluginSecondActivity.class);
        startActivity(intent);
    }

    public void startPluginB(View view) {
        if (isActivityActionAvailable(this, Constant.PLUGIN_B_FIRST_ACTION)) {
            Intent intent = new Intent().setAction(Constant.PLUGIN_B_FIRST_ACTION);
            startActivity(intent);
        } else {
            Toast.makeText(PluginFirstActivity.this, "启动Plugin B First Activity失败", Toast.LENGTH_SHORT).show();
        }
    }
}
