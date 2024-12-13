package com.yangxinyu.smkt.model;

import com.yangxinyu.smkt.model.entity.MyActivity;
import com.yangxinyu.smkt.model.entity.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Model层
 * 进行真正的数据处理
 */
public class DefaultRepository {
    private final Datasource datasource = new DefaultDatasource();
    private final Random random = new Random();
    private final ExecutorService pool = Executors.newCachedThreadPool();

    public static DefaultRepository getInstance() {
        return Holder.INSTANCE;
    }

    public boolean checkLogin() {
        return datasource.user() == null;
    }

    public User getUser() {
        return datasource.user();
    }

    public @NotNull List<MyActivity> getDoneActivities() {
        User user = getUser();
        return user == null ? new ArrayList<>() : datasource.allDoneActivitiesByUserId(user.getId());
    }

    public @NotNull void getTodoActivities(MyActivity.ActivityType type, List<MyActivity.ActivityClass> activityClasses, Consumer<List<MyActivity>> consumer) {
        pool.execute(() -> {
            List<MyActivity> list = datasource.allTodoActivities(type, activityClasses);
            consumer.accept(list);
        });
    }


    public void checkLogin(LoginCallback callback) {
        pool.execute(() -> {
            try {
                Thread.sleep(1000);
                if (random.nextBoolean()) {
                    callback.onSuccess(getUser());
                } else {
                    callback.onFail("登录失败");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                callback.onFail("登录失败:" + e.getMessage());
            }
        });
    }


    public interface LoginCallback {
        void onSuccess(User user);

        void onFail(String msg);
    }

    private final static class Holder {
        private static final DefaultRepository INSTANCE = new DefaultRepository();
    }
}
