package com.yangxinyu.smkt;

import android.app.Application;

import com.yangxinyu.smkt.repository.SP;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        SP.getInstance().init(this);
    }

    public static Application application = null;
}
