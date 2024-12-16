package com.yangxinyu.smkt.ui;

import android.os.Bundle;

import com.yangxinyu.smkt.model.entity.MyActivity;

public class HomeOfflinePageFragment extends AbstractHomeDonePageFragment {


    public static HomeOfflinePageFragment newInstance() {
        HomeOfflinePageFragment fragment = new HomeOfflinePageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    MyActivity.ActivityType activityType() {
        return MyActivity.ActivityType.Offline;
    }
}
