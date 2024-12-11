package com.yangxinyu.smkt.ui;

import android.os.Bundle;

import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.base.BaseFragment;

public class OfflinePageFragment extends BaseFragment {
    @Override
    public int layoutId() {
        return R.layout.fragment_offline;
    }

    public static OfflinePageFragment newInstance() {
        OfflinePageFragment fragment = new OfflinePageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
