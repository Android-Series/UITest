package com.ui.test.app.Contacts.testcase;

import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.util.Log;

import com.ui.test.app.Contacts.base.InitStep;
import com.ui.test.app.Contacts.constant.ContactsConstants;
import org.junit.Test;

public class PhoneUiTest extends InitStep {


    @Test
    public void test00001() {

        UiObject2 one = phonePageUI.getContactsPage().getTextView(ContactsConstants.one);
        one.click();
        one.click();
        UiObject2 two = phonePageUI.getContactsPage().getTextView(ContactsConstants.two);
        two.click();
    }

}
