package com.ui.test.report;

import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;


public class TestDemo extends TestCaseResult {


	public void test1(){
		mDevice.pressBack();
		mDevice.pressMenu();
		mDevice.pressMenu();
		fail();
	}

	public void test2() throws UiObjectNotFoundException {
      UiObject object=new UiObject(new UiSelector().text("dsfaerew"));
      object.click();
	}

	public void test3(){
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressMenu();

	}

	public void test4(){
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressMenu();

	}

	public void test5(){
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressMenu();

	}

}
