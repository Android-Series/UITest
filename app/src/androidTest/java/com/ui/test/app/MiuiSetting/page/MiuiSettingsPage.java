package com.ui.test.app.MiuiSetting.page;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiSelector;

import com.ui.test.app.MiuiSetting.constant.UIElementConstants;

public class MiuiSettingsPage {

    public static UiDevice device;

    UiObject2 recyclerView;

    UiObject2 textView;


    private MiuiSettingsPage(){}

    private static MiuiSettingsPage instance = null;

    /**
     * 设计成单例模式
     * @param mdevice
     * @return
     */
    public static MiuiSettingsPage getInstance(UiDevice mdevice){

        device = mdevice;

        if(instance == null){
            instance = new MiuiSettingsPage();
        }
        return instance;
    }


    public UiObject2 get_RecyclerView_byResourceId(String resourceId) {

        recyclerView = device.findObject(By.res(resourceId));

        return recyclerView;
    }


    public UiObject2 get_TextView_byText(String text) {
        textView = device.findObject(By.text(text));
        return textView;
    }

    public UiObject2 get_TextView_byResourceId(String resourceId) {
        textView = device.findObject(By.res(resourceId));
        return textView;
    }
}
