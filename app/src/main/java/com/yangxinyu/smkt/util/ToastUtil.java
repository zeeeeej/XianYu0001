package com.yangxinyu.smkt.util;

import android.widget.Toast;

import com.yangxinyu.smkt.MyApp;

public class ToastUtil {
    private static Toast toast;

    public static void show(String content) {
        try {
            if (content == null || content.isEmpty()) {
                return;
            }
            if (toast == null) {
                toast = Toast.makeText(MyApp.application, content, Toast.LENGTH_SHORT);
            } else {
                toast.setText(content);
            }
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void show(int resId) {
        String content = MyApp.application.getString(resId);
        show(content);
    }
}
