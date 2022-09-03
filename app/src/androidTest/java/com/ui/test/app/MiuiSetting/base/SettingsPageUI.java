package com.ui.test.app.MiuiSetting.base;

import android.support.test.uiautomator.UiDevice;

import com.ui.test.app.MiuiSetting.page.MiuiSettingsPage;
import com.ui.test.base.PageHelper;

public class SettingsPageUI extends PageHelper {


    public SettingsPageUI(UiDevice device) {
        super(device);
    }

    public MiuiSettingsPage getMiuiSettingsPage() {
        return MiuiSettingsPage.getInstance(device);
    }

}
