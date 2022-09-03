package com.ui.test.app.Contacts.page;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiSelector;

import com.ui.test.app.MiuiSetting.constant.UIElementConstants;

public class ContactsPage {

    public static UiDevice device;


    UiObject2 imageButton;

    private ContactsPage(){}

    private static ContactsPage instance = null;

    /**
     * 设计成单例模式
     * @param mdevice
     * @return
     */
    public static ContactsPage getInstance(UiDevice mdevice){

        device = mdevice;

        if(instance == null){
            instance = new ContactsPage();
        }
        return instance;
    }


    public UiObject2 getTextView(String resourceId) {

        imageButton = device.findObject(By.res(resourceId));

        return imageButton;
    }
}
