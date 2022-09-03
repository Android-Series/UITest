package com.ui.test.utils;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import com.ui.test.base.BaseStep;
import org.junit.Test;
import java.io.IOException;

public class SwitchKey extends BaseStep {

    public static final String mortv_key="3099B87833837758";
    public static final String children_key="053D8030DDA5AD31";
    public static final String demo_key="8CE1F8D99D1184E3";


    @Test
    public void setMortv_key(){
       switchKey(mortv_key);
    }

    @Test
    public void setChildren_key(){
        switchKey(children_key);
    }

    @Test
    public void setDemo_key(){
        switchKey(demo_key);
    }


    public void switchKey(String key){
        try {
            mDevice.executeShellCommand("pm clear com.stv.voice");
            mDevice.executeShellCommand("am start com.stv.voice/com.xiaomor.mor.app.tv.LaunchActivity");
            Thread.sleep(2000);
            UiObject authority = mDevice.findObject(new UiSelector().className("android.widget.Button").text("允许"));
            if(authority.exists()) {
                for(int i=0;i<5;i++) {
                    authority.click();
                }
            }
            UiObject firstLogin = mDevice.findObject(new UiSelector().resourceIdMatches(".*key_intput_et"));
            if(firstLogin.exists()) {
                firstLogin.setText(key);
                UiObject confirm =mDevice.findObject(new UiSelector().resourceIdMatches(".*key_confirm_btn"));
                confirm.click();
                Thread.sleep(2000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
