package com.yangxinyu.smkt.util;

import android.util.Log;

public class XLog {

    public static final boolean debug = true;
    private static final String TAG = "xlog";

    public static void d(String tag, String msg) {
        if (!debug) return;
        Log.d(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (!debug) return;
        Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (!debug) return;
        Log.e(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (!debug) return;
        Log.i(tag, msg);
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void i(String msg) {
        i(TAG, msg);
    }
}
