package com.yangxinyu.smkt.ui;

import static com.yangxinyu.smkt.MainActivity2.LOGIN_FRAGMENT_TAG;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.base.BaseFragment;
import com.yangxinyu.smkt.model.entity.MyActivity;
import com.yangxinyu.smkt.util.XLog;

public abstract class AbstractHomeDonePageFragment extends BaseFragment {

    @Override
    public int layoutId() {
        return R.layout.fragment_home_done;
    }

    abstract MyActivity.ActivityType activityType();

    @Override
    protected void init(View view) {
        super.init(view);
        MyActivity.ActivityType activityType = activityType();
        View locationView = view.findViewById(R.id.done_location);
        switch (activityType) {
            case Offline:
                locationView.setVisibility(View.VISIBLE);
                break;
            case Online:
                locationView.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MyActivity.ActivityType activityType = activityType();
        Fragment fragment;
        switch (activityType) {
            case Offline:
                fragment = DoneActivityFragment.newInstance(MyActivity.ActivityType.Offline);
                initDoneActivity(activityType.name(), fragment);
                break;
            case Online:
                fragment = DoneActivityFragment.newInstance(MyActivity.ActivityType.Online);
                initDoneActivity(activityType.name(), fragment);
                break;
        }
    }

    private void initDoneActivity(String tag, Fragment fragment) {
        try {
            XLog.i("initDoneActivity tag=" + tag + ",fragment=" + fragment);
            FragmentManager parentFragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = parentFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.home_done_fragment_container, fragment, tag);
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
