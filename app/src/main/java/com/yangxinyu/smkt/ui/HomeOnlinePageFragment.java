package com.yangxinyu.smkt.ui;

import android.os.Bundle;

import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.base.BaseFragment;

public class HomeOnlinePageFragment extends BaseFragment {
    @Override
    public int layoutId() {
        return R.layout.fragment_online;
    }

    public static HomeOnlinePageFragment newInstance() {
        HomeOnlinePageFragment fragment = new HomeOnlinePageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
