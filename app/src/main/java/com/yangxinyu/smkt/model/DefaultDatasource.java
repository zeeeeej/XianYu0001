package com.yangxinyu.smkt.model;

import androidx.annotation.NonNull;

import com.yangxinyu.smkt.model.entity.MyActivity;
import com.yangxinyu.smkt.model.entity.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class DefaultDatasource implements Datasource {
    public static final String DEFAULT_USER = "yangxinyu";
    public static final String DEFAULT_USER_ID = "0000000001";
    private final Random random = new Random();
    private final HashMap<String, List<MyActivity>> doneActivities = new HashMap<>();
    private final User user = new User(DEFAULT_USER, DEFAULT_USER_ID, DEFAULT_USER);

    {
        new Thread(() -> {
            List<MyActivity> activities = new ArrayList<>();
            MyActivity myActivity;
            for (int i = 0; i < 5; i++) {
                myActivity = new MyActivity();
                myActivity.setAddress("随即地址" + i);
                myActivity.setDatetime(System.currentTimeMillis() + 3600 * 24 * i * 1000);
                myActivity.setName("随机名称" + i);
                myActivity.setType(i % 2 == 0 ? MyActivity.ActivityType.Offline : MyActivity.ActivityType.Online);
                MyActivity.ActivityClass[] classes = MyActivity.ActivityClass.values();
                myActivity.setClz(classes[random.nextInt(classes.length)]);
                activities.add(myActivity);
            }
            doneActivities.put(DEFAULT_USER_ID, activities);
        }).start();

    }

    @Override
    public @NotNull List<MyActivity> allDoneActivitiesByUserId(String id) {
        try {
            List<MyActivity> myActivities = doneActivities.get(id);
            Thread.sleep(1000);
            return myActivities == null ? new ArrayList<>() : myActivities;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @NonNull
    @Override
    public List<MyActivity> allTodoActivities(MyActivity.ActivityType type, List<MyActivity.ActivityClass> activityClasses) {
        int count = random.nextInt(100);
        List<MyActivity> activities = new ArrayList<>();
        MyActivity myActivity;
        MyActivity.ActivityClass[] classes = MyActivity.ActivityClass.values();
        MyActivity.ActivityStatus[] statuses = MyActivity.ActivityStatus.values();
        MyActivity.ActivitySigned[] signeds = MyActivity.ActivitySigned.values();
        if (count == 0) return new ArrayList<>();
        for (int i = 0; i < count; i++) {
            myActivity = new MyActivity();
            myActivity.setAddress("随即地址" + i);
            myActivity.setDatetime(System.currentTimeMillis() + 3600 * 24 * i * 1000);
            myActivity.setName("随机名称" + i);
            myActivity.setType(i % 2 == 0 ? MyActivity.ActivityType.Offline : MyActivity.ActivityType.Online);
            myActivity.setClz(classes[random.nextInt(classes.length)]);
            myActivity.setPublisher(user);
            myActivity.setStatus(statuses[random.nextInt(statuses.length)]);
            myActivity.setSigned(signeds[random.nextInt(signeds.length)]);
            activities.add(myActivity);
        }

        return activities;
    }

    @Override
    public User user() {
        return user;
    }
}
