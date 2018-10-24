package com.test.plugin.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.test.eventbus.EventBean;
import com.test.plugin.Constant;
import com.test.plugin.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class PluginFirstActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    public void startMainService(View view) {
        Intent intent = new Intent().setPackage("com.test.main").setAction(Constant.MAIN_SERVICE_ACTION);
        if (isServiceActionAvailable(this, Constant.MAIN_SERVICE_ACTION)) {
            startService(intent);
        } else {
            Toast.makeText(PluginFirstActivity.this, "启动MainService服务失败", Toast.LENGTH_SHORT).show();
        }
    }

    public void openMainSecondActivity(View view) {
        if (isActivityActionAvailable(this, Constant.MAIN_SECOND_ACTION)) {
            Intent intent = new Intent().setAction(Constant.MAIN_SECOND_ACTION);
            startActivity(intent);
        } else {
            Toast.makeText(PluginFirstActivity.this, "启动MainSecondActivity失败", Toast.LENGTH_SHORT).show();
        }
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
        EventBus.getDefault().post(new EventBean("plugin"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBean bean) {
        if (bean != null && !TextUtils.isEmpty(bean.getTag())) {
            Toast.makeText(PluginFirstActivity.this, "On PluginFirst:" + bean.getTag(), Toast.LENGTH_SHORT).show();
        }
    }
}
