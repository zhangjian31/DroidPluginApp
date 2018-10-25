package com.example.jery.droidpluginapp;

import com.morgoo.helper.Log;

public class Banana implements IFruit {
    private static final String TAG = Banana.class.getSimpleName();

    @Override
    public String print() {
        System.out.println("this is Banana");
        return "Banana";
    }
}
