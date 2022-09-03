package com.ui.test.base;

import com.robotium.solo.Solo;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;


public class BaseActIns extends ActivityInstrumentationTestCase2 {
    protected Solo solo;
    private static final String MainActivity="com.xiaomor.mor.app.tv.launcher.MainActivity";
    private static Class<?> launchActivityClass;
    static{
        try{
            launchActivityClass = Class.forName(MainActivity);
        }catch(ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
    public BaseActIns(){
        super(launchActivityClass);
    }
    @Override
    protected void setUp() throws Exception{
        super.setUp();
        init();
    }
    public void init() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),getActivity());
    }

    public void test0001(){
        System.out.println("jxq:"+getActivity());
    }
    @Override
    protected void tearDown() throws Exception{

        solo.finishOpenedActivities();
        super.tearDown();

//		boolean more = true;
//		while(more) {
//		    try {
//		        getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
//		        getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_CENTER);
//		    } catch (SecurityException e) {
//		        more = false;
//		  }
//	    }
//	    super.tearDown();
    }
}
