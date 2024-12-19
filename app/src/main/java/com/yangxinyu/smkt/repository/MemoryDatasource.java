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


    /* 所有活动*/
    private final List<User> allUsers = new ArrayList<>();
    private final List<ReaderActivity> allActivities = new ArrayList<>();

    {
        synchronized (this) {
            new Thread(() -> {
                User root = new User(Mock.DEFAULT_USERNAME, Mock.DEFAULT_USER_ID, Mock.DEFAULT_USER);
                allUsers.add(root);
                List<ReaderActivity> readerActivities = Mock.mockList(random);
                allActivities.clear();
                allActivities.addAll(readerActivities);
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
    public List<ReaderActivity> allReaderActivities() {
        synchronized (this) {
            return new ArrayList<>(allActivities);
        }
    }
}
