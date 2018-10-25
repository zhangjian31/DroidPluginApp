package com.example.jery.droidpluginapp;

import com.morgoo.helper.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class IfruitHook implements InvocationHandler {
    private static final String TAG = IfruitHook.class.getSimpleName();
    private IFruit iFruit;

    public IfruitHook(IFruit iFruit) {
        this.iFruit = iFruit;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("##########");
        String result = (String) method.invoke(iFruit, args);
        System.out.println("result = " + result);
        System.out.println("##########");
        return null;
    }

    public static IFruit getProxy(IFruit iFruit) {
        return (IFruit) Proxy.newProxyInstance(iFruit.getClass().getClassLoader(), iFruit.getClass().getInterfaces(), new IfruitHook(iFruit));
    }
}
