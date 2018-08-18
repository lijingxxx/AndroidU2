package com.example.lijingxin.androidu2;

import android.app.Instrumentation;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */



@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    public static Instrumentation mInstrumentation = InstrumentationRegistry.getInstrumentation();
    public static Context mContext = InstrumentationRegistry.getContext();        // 上下文资源获取
    public static UiDevice mDevice= UiDevice.getInstance(mInstrumentation);       // 设备实例化

    String pidstr;

    int pidint;
    boolean isWebview = false;
    @Test
    public void useAppContext() throws Exception {
        while(!isWebview){
            find_bug();
        }
    }

    public void find_bug() throws IOException, InterruptedException, RemoteException {
        mDevice.executeShellCommand("monkey -p jingdong.app.mall -p com.sina.weibo -p com.tencent.mobileqq -p com.netease.cliudmusic -p com.taobao.taobao -p com.tencent.mm --throttle 100 --ignore-crashes --ignore-timeouts --ignore-security-exceptions --ignore-native-crashes --monitor-native-crashes -v -v -v 2000");
        Thread.sleep(3600000);
        pidstr = mDevice.executeShellCommand("ps | grep monkey");
        String[] aa = pidstr.split(" ");
        pidint = Integer.parseInt(aa[1]);
        mDevice.executeShellCommand("kill -9 " + pidint);
        Thread.sleep(1000);
        mDevice.sleep();
        Thread.sleep(5000);
        mDevice.wakeUp();
        Thread.sleep(8000);
        String app = mDevice.getCurrentPackageName();
        if(app.equals("com.android.browser")) {
            isWebview = true;
        }
        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage("com.android.browser");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);
        Thread.sleep(8000);
        if(mDevice.getClass().equals("android.webkit.WebView")){
            isWebview = true;
        }
    }
}
