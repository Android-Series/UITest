package com.ui.test.app.MiuiSetting.testcase;

import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;
import android.util.Log;
import com.ui.test.app.MiuiSetting.base.InitStep;
import com.ui.test.app.MiuiSetting.constant.ActivityConstants;
import com.ui.test.app.MiuiSetting.constant.UIElementConstants;
import com.ui.test.app.MiuiSetting.rule.LoopRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class UiTest extends InitStep {

    @Rule
    public LoopRule loopRule = new LoopRule(1);

    @Test
    public void test00001() {

        UiObject2 searchInput1 = settingsPageUI.getMiuiSettingsPage().get_TextView_byResourceId(UIElementConstants.input_id);
        searchInput1.click();
        UiObject2 searchInput2 = settingsPageUI.getMiuiSettingsPage().get_TextView_byResourceId(UIElementConstants.input_id);
        searchInput2.setText("电池");
        mDevice.waitForIdle();
        //点击并等待新窗口出现
        settingsPageUI.getMiuiSettingsPage().get_TextView_byText("省电与电池").clickAndWait(Until.newWindow(), 3000);
        Assert.assertEquals("超级省电",settingsPageUI.getMiuiSettingsPage().get_TextView_byText("超级省电").getText());
    }

    @Test
    public void test00002() {
        Log.i(TAG, "test00002");
        UiObject2 recyclerView = settingsPageUI.getMiuiSettingsPage().get_RecyclerView_byResourceId(UIElementConstants.recyclerView_id);
        recyclerView.scroll(Direction.DOWN, 1.0f, 1000);
    }
}
