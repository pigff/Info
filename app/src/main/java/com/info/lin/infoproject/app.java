package com.info.lin.infoproject;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by lin on 2017/2/18.
 */
public class App extends Application {

    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "0c0c8bc28f", true);
        sInstance = this;
    }

    public static App getInstance() {
        return sInstance;
    }
}
