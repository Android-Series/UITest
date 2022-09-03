package com.ui.test.app.Contacts.base;

import android.util.Log;

import com.ui.test.app.Contacts.constant.ContactsConstants;
import com.ui.test.app.MiuiSetting.base.SettingsPageUI;
import com.ui.test.app.MiuiSetting.constant.ActivityConstants;
import com.ui.test.base.BaseStep;

import org.junit.After;
import org.junit.Before;

import java.io.IOException;

/**
 * 每个app独有的初始化步骤：满足灵活的个性化需求
 */
public class InitStep extends BaseStep {

    public PhonePageUI phonePageUI;

    @Before
    public void setUp() {
        Log.i("uiTest", "setUp");
        //初始化
        registerWatchers();
        initPage();
        startActivity(ContactsConstants.activity_TwelveKeyDialer);
    }

    public void initPage() {
        Log.i("uiTest", "子类：initPage");
        phonePageUI = new PhonePageUI(mDevice);
    }

    @After
    public void tearDown() {
        Log.i("uiTest", "tearDown");
        mDevice.pressHome();
        unregisterWatchers();
        Log.i("uiTest", "-------------结束-----------------");
        stopApp(ContactsConstants.packageName);
    }

    public void stopApp(String packageName) {

        try {
            mDevice.executeShellCommand("am force-stop " + packageName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
