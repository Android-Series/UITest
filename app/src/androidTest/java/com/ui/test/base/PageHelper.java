package com.ui.test.base;

import android.support.test.uiautomator.UiDevice;


public class PageHelper {

    public UiDevice device;

    public PageHelper(UiDevice device){
        this.device=device;
    }
}
