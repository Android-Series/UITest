package com.ui.test.app.Contacts.base;

import android.support.test.uiautomator.UiDevice;

import com.ui.test.app.Contacts.page.ContactsPage;
import com.ui.test.base.PageHelper;

public class PhonePageUI extends PageHelper {


    public PhonePageUI(UiDevice device) {
        super(device);
    }

    public ContactsPage getContactsPage() {
        return ContactsPage.getInstance(device);
    }
}
