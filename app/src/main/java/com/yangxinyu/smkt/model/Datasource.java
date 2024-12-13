package com.yangxinyu.smkt.model;

import com.yangxinyu.smkt.model.entity.MyActivity;
import com.yangxinyu.smkt.model.entity.User;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Datasource {

    @NotNull List<MyActivity> allDoneActivitiesByUserId(String id);

    @NotNull List<MyActivity> allTodoActivities(MyActivity.ActivityType type,List<MyActivity.ActivityClass> activityClasses);

    User user();
}
