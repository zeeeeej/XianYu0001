package com.yangxinyu.smkt.ui.widget;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

@Deprecated
public class DefaultPageTransformer implements ViewPager2.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
        // 不应用任何动画效果
        page.setTranslationX(-position * page.getWidth());
        if (Math.abs(position) < 0.5) {
            page.setVisibility(View.VISIBLE);
        } else {
            page.setVisibility(View.GONE);
        }
    }
}
