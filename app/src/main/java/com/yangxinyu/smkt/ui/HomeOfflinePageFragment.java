package com.yangxinyu.smkt.ui;

import android.os.Bundle;

import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.base.BaseFragment;

public class HomeOfflinePageFragment extends BaseFragment {
    @Override
    public int layoutId() {
        return R.layout.fragment_offline;
    }

    public static HomeOfflinePageFragment newInstance() {
        HomeOfflinePageFragment fragment = new HomeOfflinePageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
