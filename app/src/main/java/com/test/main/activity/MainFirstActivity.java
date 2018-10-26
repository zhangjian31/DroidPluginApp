package com.test.main.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.component.service.PluginServiceClient;
import com.qihoo360.replugin.model.PluginInfo;
import com.test.eventbus.EventBean;
import com.test.main.Constant;
import com.test.main.R;
import com.test.main.utils.FIleUtil;
import com.test.main.utils.TestUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

public class MainFirstActivity extends AppCompatActivity {
    public static final String TAG = MainFirstActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        checkPermission();
        showInfo();
        TestUtils.print();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @SuppressLint("WrongConstant")
    private void showInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("Main:");
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
        Toast.makeText(MainFirstActivity.this, builder.toString(), Toast.LENGTH_LONG).show();
    }

    public void installPlugin(final View view) {
        try {
            new Thread() {
                @Override
                public void run() {
                    final File file = new File(FIleUtil.getDiskDir(MainFirstActivity.this));
                    File f = file.listFiles()[0];
                    final PluginInfo pi = RePlugin.install(f.getPath());
                    if (pi == null) {
                        Toast.makeText(MainFirstActivity.this, "安装失败", Toast.LENGTH_LONG).show();
                        return;
                    }
                    MainFirstActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainFirstActivity.this, pi.toString(), Toast.LENGTH_LONG).show();
                            Log.d("PluginInfo-->", pi.toString());
                        }
                    });
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainFirstActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
        }
    }

    public void startPluginService(View view) {
        Intent intent = RePlugin.createIntent("com.test.plugin", "com.test.plugin.service.PluginService");
        PluginServiceClient.startService(MainFirstActivity.this, intent);
    }

    public void openPluginFirstActivity(View view) {
        Intent intent = RePlugin.createIntent("com.test.plugin", "com.test.plugin.activity.PluginFirstActivity");
        if (!RePlugin.startActivity(MainFirstActivity.this, intent)) {
            Toast.makeText(MainFirstActivity.this, "启动失败", Toast.LENGTH_LONG).show();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBean bean) {
        if (bean != null && !TextUtils.isEmpty(bean.getTag())) {
            Toast.makeText(MainFirstActivity.this, "On MainFirst:" + bean.getTag(), Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isActivityActionAvailable(Context context, String action) {
        Intent intent = new Intent(action);
        return context.getPackageManager().resolveActivity(intent, 0) != null;
    }

    public static boolean isServiceActionAvailable(Context context, String action) {
        Intent intent = new Intent(action);
        return context.getPackageManager().resolveService(intent, 0) != null;
    }


    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasRStoragePermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int hasWStoragePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (hasRStoragePermission != PackageManager.PERMISSION_GRANTED || hasWStoragePermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                return;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            Toast.makeText(MainFirstActivity.this, "已获取权限", Toast.LENGTH_SHORT).show();
        }
    }


}
