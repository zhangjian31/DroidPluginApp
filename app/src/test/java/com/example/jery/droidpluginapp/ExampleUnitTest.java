package com.example.jery.droidpluginapp;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testHook() {
//        IFruit apple = new Apple();
////        IfruitHook.getProxy(apple).print();
        changeName();
    }

    private void changeName() {
        try {
            Class<?> cls = Class.forName("com.example.jery.droidpluginapp.Apple");
            Method method = cls.getDeclaredMethod("print", null);
            Object obj = cls.newInstance();
            method.setAccessible(true);
            method.invoke(obj, null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


}