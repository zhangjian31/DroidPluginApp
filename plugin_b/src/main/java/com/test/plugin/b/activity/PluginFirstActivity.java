package com.test.plugin.b.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.test.eventbus.EventBean;
import com.test.plugin.b.R;

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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBean bean) {
        if (bean != null && !TextUtils.isEmpty(bean.getTag())) {
            Toast.makeText(PluginFirstActivity.this, "On Plugin B First:" + bean.getTag(), Toast.LENGTH_SHORT).show();
        }
    }


    public void sendEventBus(View view) {
        EventBus.getDefault().post(new EventBean("plugin b"));
    }
}
