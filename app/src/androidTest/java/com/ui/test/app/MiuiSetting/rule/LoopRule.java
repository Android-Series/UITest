package com.ui.test.app.MiuiSetting.rule;

import android.util.Log;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/*
   用于循环执行测试的Rule，在构造函数中给定循环次数。
 */
public class LoopRule implements TestRule {

    private int loopCount;

    public LoopRule(int loopCount) {
        this.loopCount = loopCount + 1;
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            //在测试方法执行的前后分别打印消息
            @Override
            public void evaluate() throws Throwable {
                for (int i = 1; i < loopCount; i++) {
                    Log.i("uiTest","Loop " + i + " started!");
                    base.evaluate(); // 运行测试方法
                    Log.i("uiTest","Loop " + i + " finished!");
                }
            }
        };
    }
}
