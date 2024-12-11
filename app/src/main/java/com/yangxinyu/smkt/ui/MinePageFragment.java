package com.yangxinyu.smkt.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.base.BaseFragment;

import java.util.Random;

public class MinePageFragment extends BaseFragment {
    @Override
    public int layoutId() {
        return R.layout.fragment_mine;
    }

    public static MinePageFragment newInstance() {
        MinePageFragment fragment = new MinePageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init(View view) {
        super.init(view);
        TextView viewById = view.findViewById(R.id.login_commit);
        viewById.setText(new Random().nextInt()+"<=");
    }
}
