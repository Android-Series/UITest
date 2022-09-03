package com.ui.test.app.MiuiSetting.testcase.suite;

import com.ui.test.app.MiuiSetting.testcase.UiTest;
import com.ui.test.app.MiuiSetting.testcase.UiTest2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 测试套件：组织测试类一起运行
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({UiTest.class, UiTest2.class})
public class MiuiSettingSuite {

}
