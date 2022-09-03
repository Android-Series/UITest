package com.ui.test.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.net.ParseException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiDevice;

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestListener;
import junit.framework.TestResult;

/**
 * 功能：执行测试保存测试结果到手机SD卡中
 * 发生错误的时候：保存错误信息与截图
 * 发生失败的时候：保存失败信息与截图
 * 开始测试时候：     保存当前时间
 * 开始测试时候：     保存结束时间
 * 指定保存文件夹
 */

public class TestCaseResult extends UiAutomatorTestCase {

//    protected static final String ROOT_PATH = "/mnt/sdcard/AppTestReport/";
    protected static final String ROOT_PATH = "/data/local/tmp/AppTestReport/";
    protected static final String DIR_NAME;//任务目录名
    protected static String sTestName;//测试用例名
    protected static String filePath;//保存用例测试信息文件名
    protected static String pngPath;//保存图像文件名
    protected static boolean isSaveReport2SD = true;//控制保存到SD卡报告的开关
    protected static TestListener listen;//监听

    protected  UiDevice mDevice;


    //静态初始化目录  当然目录名可以从命令传入，保证每次任务结果在同一个目录下面
    static {
        SimpleDateFormat formattime1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
        long ctime = System.currentTimeMillis();
        DIR_NAME = formattime1.format(new Date(ctime));
//        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    public void setUp() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Override
    public void run(TestResult result) {
//        Bundle bundle = getParams();
//        String is2SD = bundle.getString("tosd");
//        if (is2SD != null && is2SD.matches("true|false")) {
//            isSaveReport2SD = Boolean.valueOf(is2SD);
//        }
//        if (!isSaveReport2SD) {
//            super.run(result);
//            return;
//        }
        String testName = getName().toString();

        SimpleDateFormat formattime1 = new SimpleDateFormat("MMdd_HHmmss");
        long ctime = System.currentTimeMillis();
        String time = formattime1.format(new Date(ctime));
        String dirPath = ROOT_PATH + DIR_NAME;
        sTestName = testName + "_" + time;
        filePath = dirPath + "/" + testName + "_" + time + ".txt";
        pngPath = dirPath + "/" + testName + "_" + time + ".png";

        try {
//            mDevice.executeShellCommand("mkdir " + ROOT_PATH);
//            mDevice.executeShellCommand("mkdir " + dirPath);
//            mDevice.executeShellCommand("touch " + filePath);
            Runtime.getRuntime().exec("mkdir " + ROOT_PATH);
            Runtime.getRuntime().exec("mkdir " + dirPath);
            Runtime.getRuntime().exec("touch " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        listen = new listener(filePath, result);
        result.addListener(listen);    //加入监听者
        super.run(result);

    }

    public class listener implements TestListener {

        PrintStream printTrace;
        TestResult results;
        File ft;
        String testResult = "P";
        Throwable trace;

        public listener(String filePath, TestResult result) {
            this.results = result;
            ft = new File(filePath);
            try {
                FileOutputStream fileOutputTrace = new FileOutputStream(ft, true);
                printTrace = new PrintStream(fileOutputTrace);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void addError(Test arg0, Throwable arg1) {
            saveFile("Error in " + arg0.getClass() + "#" + getName(), ft);
            arg1.printStackTrace(printTrace);
            File storePath = new File(pngPath);
            UiDevice.getInstance().takeScreenshot(storePath, 0.3f, 8);
            testResult = "E";
            trace = arg1;
        }

        @Override
        public void addFailure(Test arg0, AssertionFailedError arg1) {
            saveFile("Failure in " + arg0.getClass() + "#" + getName(), ft);
            arg1.printStackTrace(printTrace);
            File storePath = new File(pngPath);
            UiDevice.getInstance().takeScreenshot(storePath, 0.3f, 8);
            testResult = "F";
            trace = arg1;
        }

        String startTime;
        String endTime;

        @Override
        public void endTest(Test arg0) {
            endTime = getCurrentSysTime();
            saveFile("End Test Time:" + endTime, ft);
            try {
                long d = getTimeDistance(endTime, startTime);
                saveFile("Time:" + d + "ms", ft);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }

            results.removeListener(listen);//结束移除监听

        }

        @Override
        public void startTest(Test arg0) {
            startTime = getCurrentSysTime();
            saveFile("Start Test Time:" + startTime, ft);
        }
    }

    public void exec(String cmd) {
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFile(String line, File file) {
        BufferedWriter bw = null;
        try {
            FileOutputStream fo = new FileOutputStream(file, true);
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

    public String getCurrentSysTimeUnsigned() {
        SimpleDateFormat formattime1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
        long ctime = System.currentTimeMillis();
        String currenttime = formattime1.format(new Date(ctime));
        return currenttime;
    }

    public String getCurrentSysTime() {
        SimpleDateFormat formattime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        long ctime = System.currentTimeMillis();
        String currenttime = formattime1.format(new Date(ctime));
        return currenttime;
    }

    public long getTimeDistance(String time1, String time2) throws ParseException, java.text.ParseException {
        SimpleDateFormat formattime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Date t1 = formattime1.parse(time1);
        Date t2 = formattime1.parse(time2);
        long d = t1.getTime() - t2.getTime();
        return d;

    }
}
