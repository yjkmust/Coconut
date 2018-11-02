package com.yjkmust.core;

import android.app.Application;

public class App extends Application {
    public static App mInstance;
    public static App instance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
