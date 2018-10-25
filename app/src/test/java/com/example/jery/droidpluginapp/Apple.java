package com.example.jery.droidpluginapp;

import com.morgoo.helper.Log;

public class Apple implements IFruit {
    private static final String TAG = Apple.class.getSimpleName();

    @Override
    public String print() {
        System.out.println("this is Apple");
        return "Apple";
    }
}
