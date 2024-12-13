package com.yangxinyu.smkt.util;

import android.content.Context;

public class AndroidUtil {

    public static float dp2px(Context context, float dp) {
        return context.getResources().getDisplayMetrics().density * dp + 0.5f;
    }
}
