package com.ui.test.utils;

import android.os.Environment;

public class Config {

    /**
     * The timeout length of the get, is, set, assert, enter and click methods. Default length is 10 000 milliseconds.
     */
    public int timeout_small = 10000;

    /**
     * The timeout length of the waitFor methods. Default length is 20 000 milliseconds.
     */
    public int timeout_large = 20000;

    /**
     * The screenshot save path. Default save path is /sdcard/Robotium-Screenshots/.
     */
    public String screenshotSavePath = Environment.getExternalStorageDirectory() + "/Robotium-Screenshots/";

    /**
     * The screenshot file type, JPEG or PNG. Use ScreenshotFileType.JPEG or ScreenshotFileType.PNG. Default file type is JPEG.
     */
    public ScreenshotFileType screenshotFileType = ScreenshotFileType.JPEG;

    /**
     * Set to true if the get, is, set, enter, type and click methods should scroll. Default value is true.
     */
    public boolean shouldScroll = true;

    /**
     * Set to true if JavaScript should be used to click WebElements. Default value is false.
     */
    public boolean useJavaScriptToClickWebElements = false;

    /**
     * The screenshot file type, JPEG or PNG.
     *
     * @author Renas Reda, renas.reda@robotium.com
     *
     */
    public enum ScreenshotFileType {
        JPEG, PNG
    }

    /**
     *  Set to true if Activity tracking should be enabled. Default value is true.
     */

    public boolean trackActivities = true;

    /**
     * Set the web frame to be used by Robotium. Default value is document.
     */

    public String webFrame = "document";

    /**
     *  Set to true if logging should be enabled. Default value is false.
     */

    public boolean commandLogging = false;

    /**
     * The command logging tag. Default value is "Robotium".
     */

    public String commandLoggingTag = "Robotium";

    /**
     * The sleep duration for the Sleeper sleep method. Default length is 500 milliseconds.
     */
    public int sleepDuration = 500;

    /**
     * The sleep duration for the Sleeper sleepMini method. Default length is 300 milliseconds.
     */
    public int sleepMiniDuration = 300;

}

