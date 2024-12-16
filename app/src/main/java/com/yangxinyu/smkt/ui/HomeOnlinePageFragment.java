package com.yangxinyu.smkt.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.base.BaseFragment;
import com.yangxinyu.smkt.model.entity.MyActivity;

public class HomeOnlinePageFragment extends AbstractHomeDonePageFragment {

    @Override
    MyActivity.ActivityType activityType() {
        return MyActivity.ActivityType.Online;
    }

    public static HomeOnlinePageFragment newInstance() {
        HomeOnlinePageFragment fragment = new HomeOnlinePageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
