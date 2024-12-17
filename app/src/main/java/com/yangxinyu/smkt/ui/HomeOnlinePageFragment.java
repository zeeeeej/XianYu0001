package com.yangxinyu.smkt.ui;

import android.os.Bundle;
import android.view.View;

import com.yangxinyu.smkt.repository.entity.ReaderActivity;
import com.yangxinyu.smkt.ui.viewmodel.DoneViewModel;

/**
 * 主页-线上活动
 */
public class HomeOnlinePageFragment extends AbstractHomeDonePageFragment {

    private DoneViewModel viewModel;
    @Override
    ReaderActivity.ActivityType activityType() {
        return ReaderActivity.ActivityType.Online;
    }

    public static HomeOnlinePageFragment newInstance() {
        HomeOnlinePageFragment fragment = new HomeOnlinePageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init(View view) {

        super.init(view);
    }
}
