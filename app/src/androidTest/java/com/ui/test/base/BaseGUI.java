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

package com.ui.test.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.SystemClock;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import java.io.IOException;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class BaseGUI {

    private static final int LAUNCH_TIMEOUT = 5000;
    public static final String APP="com.stv.voice";
    protected UiDevice mDevice;
    protected Context mContext;
    protected PageHelper pageHelper;

    @Before
    public void setUp() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(getInstrumentation());
        mContext = getInstrumentation().getTargetContext().getApplicationContext();
        initPage();
    }
    public void execQuery(String query){
        try {
            mDevice.executeShellCommand("am broadcast -a com.mor.mic.open");
            mDevice.executeShellCommand("am broadcast -a sayinfo --es query "+query);
            Thread.sleep(4000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void initPage(){
        pageHelper=new PageHelper(mDevice);
    }

    public void sleep(long ms) {
        SystemClock.sleep(ms);
    }
    @After
    public void tearDown(){

        //方案一
        //execQuery("回到首页");
        //方案二
//        try {
//            sleep(2000);
//            mDevice.executeShellCommand("am start com.stv.voice/com.xiaomor.mor.app.tv.launcher.MainActivity");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //方案三
        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(APP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear out any previous instances
        mContext.startActivity(intent);
        sleep(2000);
    }

    public void pressEnter(){
        mDevice.pressEnter();
        sleep(1500);
    }

    public void pressDPadRight(int count){
        for (int i=0;i<count;i++){
            mDevice.pressDPadRight();
            sleep(500);
        }
    }

    public void pressDPadDown(int count){
        for(int i=0;i<count;i++){
            mDevice.pressDPadDown();
            SystemClock.sleep(1500);
        }
    }
    public void pressDPadDown(String text){
        switch (text){
            case "首页":
                break;
            case "影视":
                pressDPadDown(1);
                break;
            case "美食":
                pressDPadDown(2);
                break;
            case "资讯":
                pressDPadDown(3);
                break;
            case "音乐":
                pressDPadDown(4);
                break;
            case "景点":
                pressDPadDown(5);
                break;
        }
    }

    private void openApp(String packageName) {
        // Start from the home screen
        //mDevice.pressHome();
        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
        // Launch the blueprint app
        final Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        mContext.startActivity(intent);
        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), LAUNCH_TIMEOUT);
        sleep(5000);
    }

    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */
    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        // Use PackageManager to get the launcher package name
        PackageManager pm = mContext.getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
}
