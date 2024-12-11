package com.yangxinyu.smkt.ui;

import android.os.Bundle;

import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.base.BaseFragment;

public class OnlinePageFragment extends BaseFragment {
    @Override
    public int layoutId() {
        return R.layout.fragment_online;
    }

    public static OnlinePageFragment newInstance() {
        OnlinePageFragment fragment = new OnlinePageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
