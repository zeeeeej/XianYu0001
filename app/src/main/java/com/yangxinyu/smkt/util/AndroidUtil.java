package com.yangxinyu.smkt.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.yangxinyu.smkt.R;

public class AndroidUtil {

    public static float dp2px(Context context, float dp) {
        return context.getResources().getDisplayMetrics().density * dp + 0.5f;
    }

    public static void applyAddress(Context context, TextView textView, String address) {
        Resources resources = context.getResources();
        String nav = resources.getString(R.string.nav);
        Drawable drawable = resources.getDrawable(R.mipmap.todo_nav);
        drawable.setBounds(0, 0, (int) dp2px(context, 10), (int) dp2px(context, 10));
        int color = resources.getColor(R.color.span);
        String text = address + nav;
        int len = text.length();
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
        spannableString.setSpan(foregroundColorSpan, len - 3, len - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
        spannableString.setSpan(imageSpan, len - 1, len, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }
}
