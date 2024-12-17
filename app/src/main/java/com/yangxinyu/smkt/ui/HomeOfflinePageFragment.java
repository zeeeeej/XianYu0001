package com.yangxinyu.smkt.ui;

import android.os.Bundle;

import com.yangxinyu.smkt.repository.entity.ReaderActivity;

/**
 * 主页-线下活动
 */
public class HomeOfflinePageFragment extends AbstractHomeDonePageFragment {


    public static HomeOfflinePageFragment newInstance() {
        HomeOfflinePageFragment fragment = new HomeOfflinePageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    ReaderActivity.ActivityType activityType() {
        return ReaderActivity.ActivityType.Offline;
    }
}
