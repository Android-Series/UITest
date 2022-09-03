package com.ui.test.watcher;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;

public class AppCrashWatcher implements UiWatcher {

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
}
