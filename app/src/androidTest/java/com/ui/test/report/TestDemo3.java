package com.ui.test.report;

import android.content.Context;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TestDemo3 {
    public String TAG="JXQ";
    public static UiDevice mDevice;
    public Context mContext;
    public static final String DIR_NAME;//任务目录名
    @Before
    public void setUp() {
        //初始化
        mDevice = UiDevice.getInstance(getInstrumentation());
        mContext = InstrumentationRegistry.getTargetContext();
    }
    //静态初始化目录 当然目录名可以从命令传入，保证每次任务结果在同一个目录下面
    static {
        SimpleDateFormat formattime1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
        long ctime = System.currentTimeMillis();
        DIR_NAME = formattime1.format(new Date(ctime));
        mDevice = UiDevice.getInstance(getInstrumentation());
        MyTestWatcher.DIR_NAME=DIR_NAME;
    }

    @Rule
    public TestWatcher testWatcher = new MyTestWatcher(mDevice);

    @Test
    public void test0001() throws UiObjectNotFoundException {
        UiObject wlan=new UiObject(new UiSelector().text("WLAN"));
        wlan.click();
        assertEquals("开启",new UiObject(new UiSelector().text("开启")).getText());
    }
    @Test
    public void test0002() throws UiObjectNotFoundException {
        UiObject wlan=new UiObject(new UiSelector().text("WLAN"));
        wlan.click();
        assertEquals("开启",new UiObject(new UiSelector().text("关闭")).getText());
    }
    @Test
    public void test0003() throws RemoteException {
        mDevice.pressRecentApps();
        mDevice.pressBack();

    }

    @Test
    public void test0004() throws UiObjectNotFoundException {
        UiObject obj = new UiObject(new UiSelector().resourceId("res"));
        obj.click();
    }
}
