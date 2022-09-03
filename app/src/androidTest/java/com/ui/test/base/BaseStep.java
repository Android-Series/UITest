package com.ui.test.base;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiWatcher;
import android.util.Log;

import com.ui.test.app.MiuiSetting.constant.ActivityConstants;
import com.ui.test.report.MyTestWatcher;
import com.ui.test.watcher.AppCrashWatcher;
import com.ui.test.watcher.MessagingWatcher;

import org.junit.runner.RunWith;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * 顶级父类：实现一些通用功能
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 21)
public class BaseStep {

    public static UiDevice mDevice;
    public static Context instrumentationContext;
    public static Context targetContext;
    public List<String> mWatchers;
    public PageHelper pageHelper;
    public static final String DIR_NAME;//任务目录名

    private static final String BASIC_SAMPLE_PACKAGE = "com.example.android.testing.uiautomator.BasicSample";
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String STRING_TO_BE_TYPED = "UiAutomator";

    public static final String TAG = "uiTest";

    static {
        Log.i(TAG, "static");
        Instrumentation mInstrumentation = InstrumentationRegistry.getInstrumentation(); // 返回当前正在运行的Instrumentation
        mDevice = UiDevice.getInstance(mInstrumentation); //本单例模式
        instrumentationContext = InstrumentationRegistry.getContext();// 返回此Instrumentation软件包的上下文
        targetContext = InstrumentationRegistry.getTargetContext();// 返回目标应用的应用上下文。

        SimpleDateFormat formattime1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
        long ctime = System.currentTimeMillis();
        DIR_NAME = formattime1.format(new Date(ctime));
        MyTestWatcher.DIR_NAME = DIR_NAME;
    }

    // Rule是JUnit4中的新特性，它让我们可以扩展JUnit的功能，灵活地改变测试方法的行为。
//    @Rule
//    public TestWatcher testWatcher = new MyTestWatcher(mDevice);


    public void initPage() {
        Log.i(TAG, "父类：initPage");
        pageHelper = new PageHelper(mDevice);
    }

    /**
     * 通过adb 命令启动app
     * @param activity com.android.settings/com.android.settings.MiuiSettings
     */
    public void startActivity(String activity) {
        try {
            mDevice.executeShellCommand("am start " + activity);
            mDevice.waitForIdle();
            //sleep(2000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据应用包名启动app, 高版本的Android 不可用
     * @param packageName 包名
     */
    public void startAPP(String packageName) {
        Intent mIntent = targetContext.getPackageManager().getLaunchIntentForPackage(packageName);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        targetContext.startActivity(mIntent);
        mDevice.waitForIdle();
    }

//
    /**
     * Uiwatcher用于处理脚本执行过程中遇到非预想的步骤
     */
    public void registerWatchers() {
        UiWatcher[] watchers = new UiWatcher[]{
                new AppCrashWatcher(),
                new MessagingWatcher()
        };
        mWatchers = new ArrayList<String>();
        for (UiWatcher watcher : watchers) {
            String name = watcher.toString();
            mDevice.registerWatcher(name, watcher);
            mWatchers.add(name);
        }
    }

    public void execQuery(String query) {
        try {
            mDevice.executeShellCommand("am broadcast -a com.mor.mic.open");
            mDevice.executeShellCommand("am broadcast -a sayinfo --es query " + query);
            Thread.sleep(4000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void execCMD(String cmd) {
        try {
            mDevice.executeShellCommand("am broadcast -a com.mor.mic.open");
            mDevice.executeShellCommand("am broadcast -a sayinfo --es query " + cmd);
            Thread.sleep(4000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 休眠时间
     * @param ms
     */
    public void sleep(long ms) {
        SystemClock.sleep(ms);
    }

    public void pressDPadDown(int count) {
        for (int i = 0; i < count; i++) {
            mDevice.pressDPadDown();
            sleep(1500);
        }
    }

    public void unregisterWatchers() {
        Log.i(TAG, "是否有监听器触发过：" + mDevice.hasAnyWatcherTriggered());
        for (String name : mWatchers) {
            mDevice.removeWatcher(name);
        }
    }
}
