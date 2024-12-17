package com.yangxinyu.smkt.repository;

import androidx.annotation.NonNull;

import com.yangxinyu.smkt.repository.entity.ReaderActivity;
import com.yangxinyu.smkt.repository.entity.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * 使用内存存储数据
 */
public class MemoryDatasource implements Datasource {
    private final Random random = new Random();
    public static final String DEFAULT_USER = "yangxinyu";
    public static final String DEFAULT_USER_ID = "0000000001";

    /* 所有活动*/
    private final List<User> allUsers = new ArrayList<>();
    private final List<ReaderActivity> allActivities = new ArrayList<>();

    {
        synchronized (this) {
            new Thread(() -> {
                User root = new User(DEFAULT_USER, DEFAULT_USER_ID, DEFAULT_USER);
                allUsers.add(root);
                ReaderActivity myActivity;
                for (int i = 0; i < 30; i++) {
                    myActivity = new ReaderActivity();
                    ReaderActivity.ActivityClass[] classes = ReaderActivity.ActivityClass.values();
                    ReaderActivity.ActivityClass clz = classes[random.nextInt(classes.length)];
                    myActivity.setClz(clz);
                    String name = "";
                    switch (clz) {
                        case Book:
                            name = "书籍";
                            break;
                        case Film:
                            name = "电影";
                            break;
                        case Tea:
                            name = "茶话会";
                            break;
                        case Magic:
                            name = "创意活动";
                            break;
                    }
                    myActivity.setName("随机" + name + i);

                    myActivity.setDatetime(System.currentTimeMillis() + 3600 * 24 * i * 1000L);
                    ReaderActivity.ActivityType activityType = i % 2 == 0 ? ReaderActivity.ActivityType.Offline : ReaderActivity.ActivityType.Online;
                    myActivity.setType(activityType);
                    String address = "";
                    switch (activityType) {

                        case Offline:
                            address = "地点位置地点位置地点位置地点位";
                            break;
                        case Online:
                            address = "腾讯会议";

                            break;
                    }
                    myActivity.setAddress(address + i);

                    int total = 5 + random.nextInt(10);
                    myActivity.setPublisherTotalNumber(total);
                    myActivity.setPublisherSignedNumber(random.nextInt(total));
                    myActivity.setPublisher(root);
                    myActivity.setPublisherDatetime(System.currentTimeMillis() - +3600 * 24 * i * 1000L);
                    ReaderActivity.ActivitySigned[] values = ReaderActivity.ActivitySigned.values();
                    myActivity.setSigned(values[random.nextInt(values.length)]);
                    ReaderActivity.ActivityStatus[] statues = ReaderActivity.ActivityStatus.values();
                    myActivity.setStatus(statues[random.nextInt(statues.length)]);
                    allActivities.add(myActivity);
                }
            }).start();
        }

    }

    @Override
    public boolean addUser(@NotNull User user) {
        synchronized (this) {
            if (allUsers.isEmpty()) {
                allUsers.add(user);
            } else {
                for (User u : allUsers) {
                    if (u.getUsername().equals(user.getUsername())) {
                        return false;
                    }
                }
                allUsers.add(user);
            }
            return true;
        }
    }

    @Override
    public boolean deleteUser(String username) {
        synchronized (this) {
            if (!allUsers.isEmpty()) {
                Iterator<User> iterator = allUsers.iterator();
                while (iterator.hasNext()) {
                    User next = iterator.next();
                    if (next.getUsername().equals(username)) {
                        allUsers.remove(next);
                    }
                }
            }
            return true;
        }
    }

    @Override
    public boolean updateUser(@NotNull User user) {
        synchronized (this) {
            if (!allUsers.isEmpty()) {
                User u = null;
                for (User next : allUsers) {
                    if (next.getUsername().equals(user.getUsername())) {
                        u = next;
                        break;
                    }
                }
                if (u != null) {
                    u.setNickname(user.getNickname());
                }
            }
            return true;
        }
    }

    @NonNull
    @Override
    public List<User> allUsers() {
        synchronized (this) {
            return new ArrayList<>(allUsers);
        }
    }

    @Override
    public User findUser(String username) {
        synchronized (this) {
            if (!allUsers.isEmpty()) {
                for (User u : allUsers) {
                    if (u.getUsername().equals(username)) {
                        return u;
                    }
                }
            }
            return null;
        }

    }

    @Override
    public boolean addActivity(@NotNull ReaderActivity activity) {
        synchronized (this) {
            if (allUsers.isEmpty()) {
                allActivities.add(activity);
            } else {
                for (ReaderActivity act : allActivities) {
                    if (act.getId().equals(activity.getId())) {
                        return false;
                    }
                }
                allActivities.add(activity);
            }
            return true;
        }
    }

    @Override
    public boolean deleteActivity(String id) {
        synchronized (this) {
            if (!allActivities.isEmpty()) {
                Iterator<ReaderActivity> iterator = allActivities.iterator();
                while (iterator.hasNext()) {
                    ReaderActivity next = iterator.next();
                    if (next.getId().equals(id)) {
                        allActivities.remove(next);
                    }
                }
            }
            return true;
        }
    }

    @Override
    public boolean updateActivity(@NotNull ReaderActivity activity) {
        synchronized (this) {
            if (!allUsers.isEmpty()) {
                ReaderActivity act = null;
                for (ReaderActivity next : allActivities) {
                    if (next.getId().equals(activity.getId())) {
                        act = next;
                        break;
                    }
                }
                if (act != null) {
                    act.setName(activity.getName());
                    act.setDatetime(activity.getDatetime());
                    act.setIcon(activity.getIcon());
                    act.setType(activity.getType());
                    act.setClz(activity.getClz());
                    act.setStatus(activity.getStatus());
                    act.setSigned(activity.getSigned());
                    act.setAddress(activity.getAddress());
                    act.setPublisher(activity.getPublisher());
                    act.setPublisherDatetime(activity.getPublisherDatetime());
                    act.setPublisherSignedNumber(activity.getPublisherSignedNumber());
                    act.setPublisherTotalNumber(activity.getPublisherTotalNumber());

                }
            }
            return true;
        }
    }

    @NonNull
    @Override
    public List<ReaderActivity> allMyActivities() {
        return new ArrayList<>(allActivities);
    }
}
