package com.yangxinyu.smkt.ui;

import android.os.Bundle;

import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.base.BaseFragment;

public class TodoPageFragment extends BaseFragment {
    @Override
    public int layoutId() {
        return R.layout.fragment_todo;
    }

    public static TodoPageFragment newInstance() {
        TodoPageFragment fragment = new TodoPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
