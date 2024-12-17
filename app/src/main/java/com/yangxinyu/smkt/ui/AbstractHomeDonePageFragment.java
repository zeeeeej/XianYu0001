package com.yangxinyu.smkt.ui;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.yangxinyu.smkt.MainActivity;
import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.ui.base.BaseFragment;
import com.yangxinyu.smkt.repository.entity.ReaderActivity;
import com.yangxinyu.smkt.util.ToastUtil;

/**
 * 已完成活动（线下/线上）
 */
public abstract class AbstractHomeDonePageFragment extends BaseFragment {

    @Override
    public int layoutId() {
        return R.layout.fragment_home_done;
    }

    abstract ReaderActivity.ActivityType activityType();

    @Override
    protected void init(View view) {
        super.init(view);
        ReaderActivity.ActivityType activityType = activityType();
        View locationView = view.findViewById(R.id.done_location);
        switch (activityType) {
            case Offline:
                locationView.setVisibility(View.VISIBLE);
                break;
            case Online:
                locationView.setVisibility(View.GONE);
                break;
        }
        Fragment fragment;
        switch (activityType) {
            case Offline:
                fragment = DoneActivityFragment.newInstance(ReaderActivity.ActivityType.Offline);
                initDoneActivity(activityType.name(), fragment);
                break;
            case Online:
                fragment = DoneActivityFragment.newInstance(ReaderActivity.ActivityType.Online);
                initDoneActivity(activityType.name(), fragment);
                break;
        }

        view.findViewById(R.id.done_location).setOnClickListener((v)->{
            MainActivity.doSomethingBeforeCheckUserLogin(this,()->{
                ToastUtil.show("TODO 定位位置");
            });

        });
        view.findViewById(R.id.action_publish).setOnClickListener((v)->{
            MainActivity.doSomethingBeforeCheckUserLogin(this,()->{
                ToastUtil.show("TODO 发起活动");
            });

        });
        view.findViewById(R.id.action_member).setOnClickListener((v)->{
            MainActivity.doSomethingBeforeCheckUserLogin(this,()->{
                ToastUtil.show("TODO 成为会员");
            });

        });
        view.findViewById(R.id.action_history).setOnClickListener((v)->{
            MainActivity.doSomethingBeforeCheckUserLogin(this,()->{
                ToastUtil.show("TODO 历史活动");
            });
        });

    }

    private void initDoneActivity(String tag, Fragment fragment) {
        try {
            FragmentManager parentFragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = parentFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.home_done_fragment_container, fragment, tag);
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
