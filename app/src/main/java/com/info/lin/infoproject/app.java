package com.info.lin.infoproject;

import android.app.Application;

/**
 * Created by lin on 2017/2/18.
 */
public class App extends Application {

    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static App getInstance() {
        return sInstance;
    }
}
