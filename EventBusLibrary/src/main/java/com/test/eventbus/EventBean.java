package com.test.eventbus;

import org.greenrobot.eventbus.EventBus;

public class EventBean {
    private String tag;

    public EventBean(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void send() {
        EventBus.getDefault().post(this);
    }
}
