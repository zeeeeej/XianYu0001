package com.yangxinyu.smkt.model;

import com.yangxinyu.smkt.model.entity.MyActivity;
import com.yangxinyu.smkt.model.entity.User;

import java.util.List;

public interface Datasource {

    List<MyActivity> allDoneActivitiesByUserId(String id);

    User user();
}
