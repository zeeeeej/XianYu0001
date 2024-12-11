package com.yangxinyu.smkt;

import android.app.Application;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static Application application = null;
}
