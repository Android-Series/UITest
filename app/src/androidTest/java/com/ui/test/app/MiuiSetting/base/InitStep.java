package com.ui.test.app.MiuiSetting.base;

import android.util.Log;

import com.ui.test.app.Contacts.base.PhonePageUI;
import com.ui.test.app.MiuiSetting.constant.ActivityConstants;
import com.ui.test.base.BaseStep;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * 每个app独有的初始化步骤：满足灵活的个性化需求
 */
public class InitStep extends BaseStep {

    public SettingsPageUI settingsPageUI;

    @BeforeClass
    public static void beforeClass(){
        Log.i(TAG, "beforeClass");
    }

    @Before
    public void setUp() {
        Log.i(TAG, "setUp");
        //初始化
        registerWatchers();
        initPage();
        startActivity(ActivityConstants.launcherActivity);
    }

    public void initPage() {
        Log.i(TAG, "子类：initPage");
        settingsPageUI = new SettingsPageUI(mDevice);
    }


    @After
    public void tearDown() {
        Log.i(TAG, "tearDown");
        mDevice.pressHome();
        unregisterWatchers();
        Log.i(TAG, "-------------结束-----------------");
    }

    @AfterClass
    public static void afterClass(){
        Log.i(TAG, "afterClass");
    }
}
