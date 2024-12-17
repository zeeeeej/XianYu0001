package com.yangxinyu.smkt.repository;

import com.yangxinyu.smkt.repository.entity.ReaderActivity;
import com.yangxinyu.smkt.repository.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 数据仓库
 * 进行真正的数据处理
 */
public class DefaultRepository {
    private final Datasource datasource = new MemoryDatasource();
    public static final String DEFAULT_USER = "yangxinyu";
    public static final String DEFAULT_USER_ID = "0000000001";
    public static final int MOCK_MAX_TIMESTAMP = 1000;
    private final Random random = new Random();
    private final ExecutorService pool = Executors.newCachedThreadPool();

    private User user = null;
    private final List<ReaderActivity> allMyActivities = new ArrayList<>();

    public static DefaultRepository getInstance() {
        return Holder.INSTANCE;
    }

    {
        pool.execute(() -> {
            synchronized (this) {
                String username = SP.getInstance().getUsername();
                user = datasource.findUser(username);
            }
        });
    }

    public User getUser() {
        return user;
    }

    public void getAllMyActivities(Consumer<List<ReaderActivity>> consumer) {
        pool.execute(() -> {
            synchronized (this) {
                if (user == null) {
                    consumer.accept(new ArrayList<>());
                    return;
                }
                mockCastTime();
                if (!allMyActivities.isEmpty()) {
                    consumer.accept(new ArrayList<>(allMyActivities));
                } else {
                    consumer.accept(new ArrayList<>());
                }
            }
        });
    }

    public void getDoneActivities(ReaderActivity.ActivityType activityType, List<ReaderActivity.ActivityClass> classes, Consumer<List<ReaderActivity>> consumer) {
        pool.execute(() -> {
            if (user == null) {
                consumer.accept(new ArrayList<>());
                return;
            }
            mockCastTime();
            List<ReaderActivity> activities = datasource.allMyActivities();
            if (!activities.isEmpty()) {
                List<ReaderActivity> result = activities.stream()
                        .filter((act) -> act.getStatus() == ReaderActivity.ActivityStatus.Done)
                        .filter((act) -> act.getType() == activityType)
                        .filter((act) -> classes.isEmpty() || classes.contains(act.getClz()))
                        .collect(Collectors.toList());

                consumer.accept(result);
            } else {
                consumer.accept(new ArrayList<>());
            }
        });
    }

    public void getTodoActivities(ReaderActivity.ActivityType activityType, List<ReaderActivity.ActivityClass> classes, Consumer<List<ReaderActivity>> consumer) {

        pool.execute(() -> {
            if (user == null) {
                consumer.accept(new ArrayList<>());
                return;
            }
            mockCastTime();
            List<ReaderActivity> activities = datasource.allMyActivities();
            if (!activities.isEmpty()) {
                List<ReaderActivity> result = activities.stream()
                        .filter((act) -> act.getStatus() == ReaderActivity.ActivityStatus.Doing ||
                                act.getStatus() == ReaderActivity.ActivityStatus.Todo
                        )
                        .filter((act) -> act.getType() == activityType)
                        .filter((act) -> classes.isEmpty() || classes.contains(act.getClz()))
                        .collect(Collectors.toList());

                consumer.accept(result);
            } else {
                consumer.accept(new ArrayList<>());
            }
        });
    }

    public void checkLogin(CheckLoginCallback callback) {
        pool.execute(() -> {
            synchronized (this) {
                if (user == null) {
                    String username = SP.getInstance().getUsername();
                    user = datasource.findUser(username);
                    if (user != null) {
                        callback.onSuccess(user);
                    } else {
                        callback.onFail("未登录");
                    }
                } else {
                    callback.onSuccess(user);
                }
            }
        });
    }

    public void login(String username, LoginCallback callback) {
        pool.execute(() -> {
            synchronized (this) {
                try {
                    mockCastTime();
                    User u = datasource.findUser(username);
                    if (u != null) {
                        this.user = u;
                        List<ReaderActivity> activities = datasource.allMyActivities();
                        if (allMyActivities.isEmpty()) {
                            int count = 0;
                            for (ReaderActivity activity : activities) {
                                if (count < 5) {
                                    allMyActivities.add(activity);
                                    count++;
                                } else {
                                    break;
                                }
                            }
                        }
                        SP.getInstance().setUsername(u.getUsername());
                        callback.onSuccess(u);
                    } else {
                        callback.onFail("登录失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFail("登录失败:" + e.getMessage());
                }
            }
        });
    }

    public void loginOut(Consumer<Boolean> callback) {
        pool.execute(() -> {
            synchronized (this) {
                try {
                    mockCastTime();
                    SP.getInstance().clear();
                    this.user = null;
                    allMyActivities.clear();
                    callback.accept(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.accept(false);
                }
            }
        });
    }

    /* 模拟耗时 */
    private void mockCastTime() {
        try {
            Thread.sleep(random.nextInt(MOCK_MAX_TIMESTAMP));
        } catch (InterruptedException e) {
            // ignore
        }
    }

    public interface LoginCallback {
        void onSuccess(User user);

        void onFail(String msg);
    }

    public interface CheckLoginCallback {
        void onSuccess(User user);

        void onFail(String msg);
    }

    private final static class Holder {
        private static final DefaultRepository INSTANCE = new DefaultRepository();
    }
}
