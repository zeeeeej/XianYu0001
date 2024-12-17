package com.yangxinyu.smkt.repository;

import android.content.Context;
import android.content.SharedPreferences;

import org.jetbrains.annotations.NotNull;

public class SP {

    private SharedPreferences sharedPreferences;

    public static final String KEY_USERNAME = "key_username";

    public static SP getInstance() {
        return Holder.INSTANCE;
    }

    public void init(Context context) {
        synchronized (this) {
            sharedPreferences = context.getApplicationContext().getSharedPreferences("shuimuketing", Context.MODE_PRIVATE);
            sharedPreferences.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {

            });
        }

    }

    private void check() {
        if (sharedPreferences == null) throw new IllegalStateException("请调用init初始化SP");
    }

    public void setUsername(String username) {
        synchronized (this) {
            check();
            sharedPreferences.edit().putString(KEY_USERNAME, username).apply();
        }
    }

    public @NotNull String getUsername() {
        synchronized (this) {
            check();
            return sharedPreferences.getString(KEY_USERNAME, "");
        }
    }

    public void clear() {
        synchronized (this) {
            check();
            sharedPreferences.edit().clear().apply();
        }
    }

    private final static class Holder {
        private static final SP INSTANCE = new SP();
    }
}
