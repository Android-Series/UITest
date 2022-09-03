package com.ui.test.report;

import android.content.Context;
import android.net.ParseException;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.Assert.*;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class TestDemo2 {
    public String TAG="JXQ";
    public static UiDevice mDevice;
    public Context mContext;
    public static final String ROOT_PATH = "/data/local/tmp/AppTestReport/";//测试报告保存目录
    public static final String DIR_NAME;//任务目录名
    public static String filePath;//保存用例测试信息文件名
    public static String pngPath;//保存图像文件名
    @Before
    public void setUp() {
        //初始化
        mDevice = UiDevice.getInstance(getInstrumentation());
        mContext = InstrumentationRegistry.getTargetContext();
    }
    //静态初始化目录 当然目录名可以从命令传入，保证每次任务结果在同一个目录下面
    static {
        SimpleDateFormat formattime1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
        long ctime = System.currentTimeMillis();
        DIR_NAME = formattime1.format(new Date(ctime));
        mDevice = UiDevice.getInstance(getInstrumentation());
    }

    @Rule
    public TestWatcher testWatcher = new TestWatcher() {
        File ft;
        PrintStream printTrace;
        String startTime;
        String endTime;
        @Override
        protected void starting(Description description) {
            Log.e(TAG,description.getMethodName() + " starting");
            startTime = getCurrentSysTime();
            createTestDir(description);
            saveFile("Start Test Time:" + startTime);
        }
        @Override
        protected void succeeded(Description description) {
            Log.e(TAG,description.getMethodName() + " Succeed");
//            createTestDir(description);
//            initFile();
//            saveFile("Succed " + description.getClassName() + "#" + description.getMethodName(),ft);
        }
        @Override
        protected void failed(Throwable e, Description description) {
            Log.e(TAG,description.getMethodName() + " Fail");
//            createTestDir(description);
            saveFile("Failure in " + description.getClassName() + "#" + description.getMethodName());
            e.printStackTrace(printTrace);
            File storePath = new File(pngPath);
            UiDevice.getInstance().takeScreenshot(storePath, 0.3f, 8);
        }
        @Override
        protected void skipped(AssumptionViolatedException e, Description description) {
            Log.e(TAG,description.getMethodName() + " Skipped");
        }

        @Override
        protected void finished(Description description) {
            Log.e(TAG,description.getMethodName() + " finished");
            endTime = getCurrentSysTime();
            saveFile("End Test Time:" + endTime);
            try {
                long d = getTimeDistance(endTime, startTime);
                saveFile("Time:" + d + "ms");
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        }

        public void saveFile(String line) {
            //
            ft = new File(filePath);
            try {
                FileOutputStream fileOutputTrace = new FileOutputStream(ft, true);
                printTrace = new PrintStream(fileOutputTrace);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //
            BufferedWriter bw = null;
            try {
                FileOutputStream fo = new FileOutputStream(ft, true);
                OutputStreamWriter ow = new OutputStreamWriter(fo);
                bw = new BufferedWriter(ow);
                bw.append(line);
                bw.newLine();
                bw.flush();
                bw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    };

    public void createTestDir(Description description) {
        String testName = description.getMethodName();
        String testClass=description.getTestClass().getSimpleName();
        SimpleDateFormat formattime1 = new SimpleDateFormat("MMdd_HHmmss");
        long ctime = System.currentTimeMillis();
        String time = formattime1.format(new Date(ctime));
        String dirPath = ROOT_PATH + testClass + "_" + DIR_NAME;
        filePath = dirPath + "/" +testClass+ "_" +testName + "_" + time + ".txt";
        pngPath = dirPath + "/" + testClass+ "_" +testName + "_" + time + ".png";
        try {
            mDevice.executeShellCommand("mkdir " + ROOT_PATH);
            mDevice.executeShellCommand("mkdir " + dirPath);
            mDevice.executeShellCommand("touch " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public long getTimeDistance(String time1, String time2) throws ParseException, java.text.ParseException {
        SimpleDateFormat formattime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Date t1 = formattime1.parse(time1);
        Date t2 = formattime1.parse(time2);
        long d = t1.getTime() - t2.getTime();
        return d;
    }
    public String getCurrentSysTime() {
        SimpleDateFormat formattime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        long ctime = System.currentTimeMillis();
        String currenttime = formattime1.format(new Date(ctime));
        return currenttime;
    }
    @Test
    public void test0001() throws UiObjectNotFoundException {
        UiObject wlan=new UiObject(new UiSelector().text("WLAN"));
        wlan.click();
        assertEquals("开启",new UiObject(new UiSelector().text("开启")).getText());
    }
    @Test
    public void test0002() throws UiObjectNotFoundException {
        UiObject wlan=new UiObject(new UiSelector().text("WLAN"));
        wlan.click();
        assertEquals("开启",new UiObject(new UiSelector().text("关闭")).getText());
    }
    @Test
    public void test0003() throws RemoteException {
        mDevice.pressRecentApps();
        mDevice.pressBack();

    }

    @Test
    public void test0004() throws UiObjectNotFoundException {
        UiObject obj = new UiObject(new UiSelector().resourceId("res"));
        obj.click();
    }
}
