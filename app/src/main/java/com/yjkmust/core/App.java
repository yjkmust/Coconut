package com.yjkmust.core;

import android.app.Application;

import com.yjkmust.config.URL;
import com.yjkmust.lemon.http.HttpHelper;

public class App extends Application {
    public static App mInstance;
    public static App instance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        new HttpHelper.Builder(this)
                .initOkHttp()
                .createRetrofit(URL.BASE_URL)
                .build();
    }
}
