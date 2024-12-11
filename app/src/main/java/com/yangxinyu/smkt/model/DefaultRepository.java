package com.yangxinyu.smkt.model;

import com.yangxinyu.smkt.model.entity.User;

import java.util.Random;

/**
 * Model层
 * 进行真正的数据处理
 */
public class DefaultRepository {

    public static final String DEFAULT_USER = "yangxinyu";
    private final Random random = new Random();
    private User user;

    public static DefaultRepository getInstance() {
        return Holder.INSTANCE;
    }

    public boolean isLogin() {
        return user != null;
    }

    public void login(LoginCallback callback) {
        new Thread(() -> {
            try {
                Thread.sleep(1000);

                if (random.nextBoolean()) {
                    user = new User(DEFAULT_USER);
                    callback.onSuccess(user);
                } else {
                    callback.onFail("登录失败");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                callback.onFail("登录失败:" + e.getMessage());
            }

        }).start();
    }


    public interface LoginCallback {
        void onSuccess(User user);

        void onFail(String msg);
    }

    private final static class Holder {
        private static final DefaultRepository INSTANCE = new DefaultRepository();
    }
}
