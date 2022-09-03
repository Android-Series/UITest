package com.ui.test.report;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiDevice;
import com.ui.test.base.PageHelper;
import java.io.IOException;

public class BaseUiAutomater extends UiAutomatorTestCase {

    public UiDevice mDevice;
    public Context mContext;
    public String APP="com.stv.voice";
    public PageHelper pageHelper;

    public void setUp(){
        //初始化
        mDevice=UiDevice.getInstance(getInstrumentation());
        mContext=InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        initPage();
    }

    public void initPage(){
        pageHelper=new PageHelper(mDevice);
    }

    public void execQuery(String query){
        try {
            mDevice.executeShellCommand("am broadcast -a com.mor.mic.open");
            mDevice.executeShellCommand("am broadcast -a sayinfo --es query "+query);
            Thread.sleep(4000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void test0001(){
        // 获取activity任务栈
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo info = manager.getRunningTasks(1).get(0);
        // 类名 .ui.mobile.activity.WebsiteLoginActivity
        String shortClassName = info.topActivity.getShortClassName();
        System.out.println("jxq:"+shortClassName);
        // 完整类名 com.haofang.testapp.ui.mobile.activity.WebsiteLoginActivity
        String className = info.topActivity.getClassName();
        System.out.println("jxq:"+className);
        // 包名 com.haofang.testapp
        String packageName = info.topActivity.getPackageName();
        System.out.println("jxq:"+mDevice.getCurrentPackageName());
        System.out.println("jxq:"+mDevice.getCurrentActivityName());
        System.out.println("jxq:"+mDevice.getLauncherPackageName());
        System.out.println("jxq:"+mDevice.getProductName());

    }

    public void test0002(){
        System.out.println("---------------->");
        final Intent intent = mContext.getPackageManager()
                .getLaunchIntentForPackage("com.stv.voice");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        mContext.startActivity(intent);
    }

    public void tearDown(){
        execQuery("回到首页");
        //恢复现场
        try {
            sleep(2000);

            mDevice.executeShellCommand("am start com.stv.voice/com.xiaomor.mor.app.tv.launcher.MainActivity");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        final Intent intent = mContext.getPackageManager().getLaunchIntentForPackage("com.stv.voice");
//        Intent intent=new Intent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear out any previous instances
//        mContext.startActivity(intent);
//        sleep(2000);
    }
    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */
    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = mContext.getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
}
