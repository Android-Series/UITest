package com.ui.test.report;

import android.net.ParseException;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 记录每个通过和不通过的测试
 */
public class MyTestWatcher extends TestWatcher{

    public static final String ROOT_PATH = "/sdcard/Download/AppTestReport/";
    public static String DIR_NAME;//任务目录名
    public static String filePath;//保存用例测试信息文件名
    public static String pngPath;//保存图像文件名
    public static UiDevice mDevice;
    public String TAG = "uiTest";

//    static {
//        SimpleDateFormat formattime1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
//        long ctime = System.currentTimeMillis();
//        DIR_NAME = formattime1.format(new Date(ctime));
//        mDevice = UiDevice.getInstance(getInstrumentation());
//    }

    public MyTestWatcher(UiDevice device) {
        Log.i(TAG, "MyTestWatcher");
        this.mDevice = device;
    }

    File ft;
    PrintStream printTrace;
    String startTime;
    String endTime;

    @Override
    protected void starting(Description description) {
        Log.e(TAG, description.getMethodName() + " starting");
        startTime = getCurrentSysTime();
        createTestDir(description);
        saveFile("Start Test Time:" + startTime);
    }

    @Override
    protected void succeeded(Description description) {
        Log.e(TAG, description.getMethodName() + " Succeed");
        saveFile("Succeed " + description.getClassName() + "#" + description.getMethodName());
    }

    @Override
    protected void failed(Throwable e, Description description) {
        Log.e(TAG, description.getMethodName() + " Fail");
        saveFile("Failure in " + description.getClassName() + "#" + description.getMethodName());
        e.printStackTrace(printTrace);
        File storePath = new File(pngPath);
        mDevice.takeScreenshot(storePath, 0.3f, 8);
    }

    @Override
    protected void skipped(AssumptionViolatedException e, Description description) {
        Log.e(TAG, description.getMethodName() + " Skipped");
    }

    @Override
    protected void finished(Description description) {
        Log.e(TAG, description.getMethodName() + " finished");
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

    public void createTestDir(Description description) {
        String testName = description.getMethodName();
        String testClass = description.getTestClass().getSimpleName();
        SimpleDateFormat formattime1 = new SimpleDateFormat("MMdd_HHmmss");
        long ctime = System.currentTimeMillis();
        String time = formattime1.format(new Date(ctime));
        String dirPath = ROOT_PATH + testClass + "_" + DIR_NAME;
        filePath = dirPath + "/" + testClass + "_" + testName + "_" + time + ".txt";
        pngPath = dirPath + "/" + testClass + "_" + testName + "_" + time + ".png";
        try {
            mDevice.executeShellCommand("mkdir " + ROOT_PATH);
            mDevice.executeShellCommand("mkdir " + dirPath);
            mDevice.executeShellCommand("touch " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void saveFile (String line){
        //create file
        ft = new File(filePath);
        try {
            FileOutputStream fileOutputTrace = new FileOutputStream(ft, true);
            printTrace = new PrintStream(fileOutputTrace);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //create BufferedWriter
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
    public long getTimeDistance (String time1, String time2) throws ParseException, java.text.ParseException {
        SimpleDateFormat formattime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Date t1 = formattime1.parse(time1);
        Date t2 = formattime1.parse(time2);
        long d = t1.getTime() - t2.getTime();
        return d;
    }
    public String getCurrentSysTime () {
        SimpleDateFormat formattime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        long ctime = System.currentTimeMillis();
        String currenttime = formattime1.format(new Date(ctime));
        return currenttime;
    }
}