/*
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ui.test.watcher;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class BaseTest {

    protected UiDevice mDevice;
    protected Context mContext;
    public List<String> mWatchers;

    @Before
    public void setUp() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(getInstrumentation());
        mContext = getInstrumentation().getTargetContext().getApplicationContext();
        registerWatchers();

    }
    private void registerWatchers() {
        UiWatcher[] watchers = new UiWatcher[]{
                new AppCrashWatcher(),
                new MessagingWatcher()
        };
        mWatchers = new ArrayList<String>();
        for(UiWatcher watcher: watchers){
            String name = watcher.toString();
            System.out.println(name);
            mDevice.registerWatcher(name, watcher);
            mWatchers.add(name);
        }
    }
    public void sleep(long ms){
        SystemClock.sleep(ms);
    }

    @Test
    public void test0001() throws InterruptedException {

        final UiObject2 ui = mDevice.findObject(By.text("短信"));
        //注册监听器
        mDevice.registerWatcher("testWatcher", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                if(mDevice.hasObject(By.text("联系人"))){
                    ui.click();
                    Log.i("testWatcher", "监听器被触发了");
                    return true;
                }
                Log.i("testWatcher", "监听器未被触发");
                return false;
            }
        });

        //运行用例步骤
        mDevice.wait(Until.findObject(By.text("写短信")), 2000);
        UiObject2 btn = mDevice.findObject(By.text("写短信"));
        btn.click();
        Thread.sleep(2000);
        mDevice.pressBack();

        //重置监听器
        mDevice.resetWatcherTriggers();
        mDevice.wait(Until.findObject(By.text("写短信")), 2000);
        btn.click();
        Thread.sleep(2000);
        mDevice.pressBack();
        Log.i("testWatcher", "重置监听器成功");

        //移除监听器
        mDevice.removeWatcher("testWatcher");
        Log.i("testWatcher", "移除监听器成功");
        mDevice.wait(Until.findObject(By.text("写短信")), 2000);
        btn.click();
        Thread.sleep(2000);
        mDevice.pressBack();
    }

    @Test
    public void testWatcher() throws UiObjectNotFoundException {
        //先要注册监听器
        UiDevice.getInstance().registerWatcher("phone", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                //挂掉电话
                UiObject call=new UiObject(new UiSelector().text("正在拨号"));
                UiObject endBtn=new UiObject(new UiSelector().resourceId("com.android.dialer:id/endButton"));
                if(call.exists()){
                    System.out.println("电话监听器被触发啦！！！");
                    try {
                        endBtn.clickAndWaitForNewWindow();
                        return true;
                    } catch (UiObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
        UiDevice.getInstance().registerWatcher("msm", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                System.out.println("短信监听器");
                return false;//返回false,说明这个监听器是失败的
            }
        });
        //执行用例步骤
        UiObject vol = new UiObject(new UiSelector().className("android.widget.LinearLayout")
                .index(6)).getChild(new UiSelector().text("声音"));
        UiObject back = new UiObject(new UiSelector().description("声音：向上导航"));
        vol.clickAndWaitForNewWindow();
        sleep(2000);
        back.clickAndWaitForNewWindow();
        sleep(2000);
        //UiDevice.getInstance().removeWatcher("phone");//移除监听器
        vol.clickAndWaitForNewWindow();
        sleep(2000);
        back.clickAndWaitForNewWindow();
        sleep(2000);
        System.out.println("是否有监听器被触发过："+UiDevice.getInstance().hasAnyWatcherTriggered());
        System.out.println("电话监听器是否被触发过："+UiDevice.getInstance().hasWatcherTriggered("phone"));
        System.out.println("短信监听器是否被触发过："+UiDevice.getInstance().hasWatcherTriggered("msm"));
    }

    @Test
    public void testWatcher2() throws UiObjectNotFoundException {
        //先要注册监听器
        //UiDevice.getInstance().registerWatcher("phone", new AppCrashWatcher());
        UiDevice.getInstance().registerWatcher("msm", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                System.out.println("短信监听器");
                return false;//返回false,说明这个监听器是失败的
            }
        });
        //执行用例步骤
        UiObject vol = new UiObject(new UiSelector().className("android.widget.LinearLayout")
                .index(6)).getChild(new UiSelector().text("声音"));
        UiObject back = new UiObject(new UiSelector().description("声音：向上导航"));
        vol.clickAndWaitForNewWindow();
        sleep(2000);
        back.clickAndWaitForNewWindow();
        sleep(2000);
        //UiDevice.getInstance().removeWatcher("phone");//移除监听器
        vol.clickAndWaitForNewWindow();
        sleep(2000);
        back.clickAndWaitForNewWindow();
        sleep(2000);
//        System.out.println("是否有监听器被触发过："+UiDevice.getInstance().hasAnyWatcherTriggered());
//        System.out.println("电话监听器是否被触发过："+UiDevice.getInstance().hasWatcherTriggered("phone"));
//        System.out.println("短信监听器是否被触发过："+UiDevice.getInstance().hasWatcherTriggered("msm"));
    }

    private void unregisterWatchers() {
        for(String name : mWatchers){
            mDevice.removeWatcher(name);
        }
    }
    public void tearDown() {

        unregisterWatchers();
    }
}