package com.yangxinyu.smkt.repository;

import com.yangxinyu.smkt.repository.entity.ReaderActivity;
import com.yangxinyu.smkt.repository.entity.User;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 数据源
 */
public interface Datasource {

    boolean addUser(@NotNull User user);

    boolean deleteUser(String username);

    boolean updateUser(@NotNull User user);

    @NotNull
    List<User> allUsers();

    User findUser(String username);

    boolean addActivity(@NotNull ReaderActivity activity);

    boolean deleteActivity(String id);

    boolean updateActivity(@NotNull ReaderActivity activity);

    @NotNull
    List<ReaderActivity> allMyActivities();

}
