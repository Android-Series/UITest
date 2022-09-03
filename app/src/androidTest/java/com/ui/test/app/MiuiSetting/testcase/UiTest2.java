package com.ui.test.app.MiuiSetting.testcase;

import android.content.Intent;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.util.Log;

import com.ui.test.app.MiuiSetting.base.InitStep;
import com.ui.test.app.MiuiSetting.constant.ActivityConstants;
import com.ui.test.app.MiuiSetting.constant.UIElementConstants;
import com.ui.test.app.MiuiSetting.rule.LoopRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class UiTest2 extends InitStep {


    @Test
    public void test00001() {

        UiObject2 searchInput1 = settingsPageUI.getMiuiSettingsPage().get_TextView_byResourceId(UIElementConstants.input_id);
        searchInput1.click();
    }


}
