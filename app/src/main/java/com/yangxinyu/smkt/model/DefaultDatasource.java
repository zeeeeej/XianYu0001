package com.yangxinyu.smkt.model;

import com.yangxinyu.smkt.model.entity.MyActivity;
import com.yangxinyu.smkt.model.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DefaultDatasource implements Datasource {
    public static final String DEFAULT_USER = "yangxinyu";
    public static final String DEFAULT_USER_ID = "0000000001";
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
                activities.add(myActivity);
            }
            doneActivities.put(DEFAULT_USER_ID, activities);
        }).start();

    }

    @Override
    public List<MyActivity> allDoneActivitiesByUserId(String id) {
        List<MyActivity> myActivities = doneActivities.get(id);
        return myActivities == null ? new ArrayList<>() : myActivities;
    }

    @Override
    public User user() {
        return user;
    }
}
