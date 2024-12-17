package com.yangxinyu.smkt.repository;

import androidx.annotation.NonNull;

import com.yangxinyu.smkt.repository.entity.ReaderActivity;
import com.yangxinyu.smkt.repository.entity.User;

import java.util.List;

/**
 * 使用数据库存储数据
 * TODO 使用Room
 */
public class DBDatasource implements Datasource{
    @Override
    public boolean addUser(@NonNull User user) {
        return false;
    }

    @Override
    public boolean deleteUser(String username) {
        return false;
    }

    @Override
    public boolean updateUser(@NonNull User user) {
        return false;
    }

    @NonNull
    @Override
    public List<User> allUsers() {
        return null;
    }

    @Override
    public User findUser(String username) {
        return null;
    }

    @Override
    public boolean addActivity(@NonNull ReaderActivity activity) {
        return false;
    }

    @Override
    public boolean deleteActivity(String id) {
        return false;
    }

    @Override
    public boolean updateActivity(@NonNull ReaderActivity activity) {
        return false;
    }

    @NonNull
    @Override
    public List<ReaderActivity> allMyActivities() {
        return null;
    }
}
