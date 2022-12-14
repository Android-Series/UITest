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

        final UiObject2 ui = mDevice.findObject(By.text("??????"));
        //???????????????
        mDevice.registerWatcher("testWatcher", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                if(mDevice.hasObject(By.text("?????????"))){
                    ui.click();
                    Log.i("testWatcher", "?????????????????????");
                    return true;
                }
                Log.i("testWatcher", "?????????????????????");
                return false;
            }
        });

        //??????????????????
        mDevice.wait(Until.findObject(By.text("?????????")), 2000);
        UiObject2 btn = mDevice.findObject(By.text("?????????"));
        btn.click();
        Thread.sleep(2000);
        mDevice.pressBack();

        //???????????????
        mDevice.resetWatcherTriggers();
        mDevice.wait(Until.findObject(By.text("?????????")), 2000);
        btn.click();
        Thread.sleep(2000);
        mDevice.pressBack();
        Log.i("testWatcher", "?????????????????????");

        //???????????????
        mDevice.removeWatcher("testWatcher");
        Log.i("testWatcher", "?????????????????????");
        mDevice.wait(Until.findObject(By.text("?????????")), 2000);
        btn.click();
        Thread.sleep(2000);
        mDevice.pressBack();
    }

    @Test
    public void testWatcher() throws UiObjectNotFoundException {
        //?????????????????????
        UiDevice.getInstance().registerWatcher("phone", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                //????????????
                UiObject call=new UiObject(new UiSelector().text("????????????"));
                UiObject endBtn=new UiObject(new UiSelector().resourceId("com.android.dialer:id/endButton"));
                if(call.exists()){
                    System.out.println("????????????????????????????????????");
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
                System.out.println("???????????????");
                return false;//??????false,?????????????????????????????????
            }
        });
        //??????????????????
        UiObject vol = new UiObject(new UiSelector().className("android.widget.LinearLayout")
                .index(6)).getChild(new UiSelector().text("??????"));
        UiObject back = new UiObject(new UiSelector().description("?????????????????????"));
        vol.clickAndWaitForNewWindow();
        sleep(2000);
        back.clickAndWaitForNewWindow();
        sleep(2000);
        //UiDevice.getInstance().removeWatcher("phone");//???????????????
        vol.clickAndWaitForNewWindow();
        sleep(2000);
        back.clickAndWaitForNewWindow();
        sleep(2000);
        System.out.println("?????????????????????????????????"+UiDevice.getInstance().hasAnyWatcherTriggered());
        System.out.println("????????????????????????????????????"+UiDevice.getInstance().hasWatcherTriggered("phone"));
        System.out.println("????????????????????????????????????"+UiDevice.getInstance().hasWatcherTriggered("msm"));
    }

    @Test
    public void testWatcher2() throws UiObjectNotFoundException {
        //?????????????????????
        //UiDevice.getInstance().registerWatcher("phone", new AppCrashWatcher());
        UiDevice.getInstance().registerWatcher("msm", new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                System.out.println("???????????????");
                return false;//??????false,?????????????????????????????????
            }
        });
        //??????????????????
        UiObject vol = new UiObject(new UiSelector().className("android.widget.LinearLayout")
                .index(6)).getChild(new UiSelector().text("??????"));
        UiObject back = new UiObject(new UiSelector().description("?????????????????????"));
        vol.clickAndWaitForNewWindow();
        sleep(2000);
        back.clickAndWaitForNewWindow();
        sleep(2000);
        //UiDevice.getInstance().removeWatcher("phone");//???????????????
        vol.clickAndWaitForNewWindow();
        sleep(2000);
        back.clickAndWaitForNewWindow();
        sleep(2000);
//        System.out.println("?????????????????????????????????"+UiDevice.getInstance().hasAnyWatcherTriggered());
//        System.out.println("????????????????????????????????????"+UiDevice.getInstance().hasWatcherTriggered("phone"));
//        System.out.println("????????????????????????????????????"+UiDevice.getInstance().hasWatcherTriggered("msm"));
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