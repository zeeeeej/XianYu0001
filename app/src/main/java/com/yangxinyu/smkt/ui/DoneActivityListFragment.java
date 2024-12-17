package com.yangxinyu.smkt.ui;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yangxinyu.smkt.MainActivity;
import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.ui.base.BaseFragment;
import com.yangxinyu.smkt.repository.entity.ReaderActivity;
import com.yangxinyu.smkt.ui.viewmodel.DoneViewModel;
import com.yangxinyu.smkt.ui.vo.DoneActivityTab;
import com.yangxinyu.smkt.ui.adapter.DoneActivityAdapter;
import com.yangxinyu.smkt.ui.viewmodel.MainViewModel;
import com.yangxinyu.smkt.util.ToastUtil;
import com.yangxinyu.smkt.util.XLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 已完成-活动列表
 */
public class DoneActivityListFragment extends BaseFragment {
    public static final String KEY_MY_ACTIVITY_TYPE = "KEY_MY_ACTIVITY_TYPE";
    public static final String KEY_MY_ACTIVITY_TAB = "KEY_MY_ACTIVITY_TAB";
    private final DoneActivityAdapter adapter = new DoneActivityAdapter(new ArrayList<>(), (activity) -> {
        MainActivity.doSomethingBeforeCheckUserLogin(this, () -> {
            ToastUtil.show("TODO 查看详情");
        });
    });
    private ReaderActivity.ActivityType activityType;
    private DoneActivityTab activityTab;
    private MainViewModel viewModel;
    private DoneViewModel doneViewModel;


    @Override
    public int layoutId() {
        return R.layout.fragment_activity_list;
    }

    public static DoneActivityListFragment newInstance(ReaderActivity.ActivityType type, DoneActivityTab tab) {
        DoneActivityListFragment fragment = new DoneActivityListFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_MY_ACTIVITY_TYPE, type.ordinal());
        args.putInt(KEY_MY_ACTIVITY_TAB, tab.ordinal());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init(View view) {
        super.init(view);
        Bundle arguments = getArguments();
        if (arguments != null) {
            int type = arguments.getInt(KEY_MY_ACTIVITY_TYPE, ReaderActivity.ActivityType.Offline.ordinal());
            int tab = arguments.getInt(KEY_MY_ACTIVITY_TAB, DoneActivityTab.All.ordinal());
            activityType = ReaderActivity.ActivityType.from(type);
            activityTab = DoneActivityTab.values()[tab];
            initRecyclerView(view);
            XLog.d("DoneActivityListFragment " + "init " + "activityType:" + activityType + ",activityTab:" + activityTab);
            viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
            viewModel.firstEnter.observe(this, (firstEnter) -> {
                XLog.d("DoneActivityListFragment " + "firstEnter = " + firstEnter);
                if (firstEnter) {
                    loadData();
                    viewModel.updateFirstEnter();
                }
            });
            doneViewModel = new ViewModelProvider(this).get(DoneViewModel.class);
            switch (activityType) {
                case Offline:
                    doneViewModel.offlineActivities.observe(this, (activities) -> {
                        refreshRecyclerView(activities);
                    });
                    doneViewModel.getOfflineActivitiesEffect.observe(this, (effect) -> {
                        switch (effect) {

                            case Idle:
                                setRefreshing(false);
                                break;
                            case Start:
                                setRefreshing(true);
                                break;
                            case Success:
                            case Fail:
                                doneViewModel.resetGetOfflineActivitiesEffect();
                                break;
                        }
                    });
                    break;

                case Online:
                    doneViewModel.onlineActivities.observe(this, (activities) -> {
                        refreshRecyclerView(activities);
                    });
                    doneViewModel.getOnlineActivitiesEffect.observe(this, (effect) -> {
                        switch (effect) {

                            case Idle:
                                setRefreshing(false);
                                break;
                            case Start:
                                setRefreshing(true);
                                break;
                            case Success:
                            case Fail:
                                doneViewModel.resetGetOnlineActivitiesEffect();
                                break;
                        }
                    });
                    break;
            }
            SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.main);
            swipeRefreshLayout.setOnRefreshListener(this::loadData);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        XLog.i("DoneActivityListFragment onResume");
        this.loadData();
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.activity_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
    }

    private void refreshRecyclerView(List<ReaderActivity> list) {
        adapter.setData(list);
    }


    private void setRefreshing(boolean refresh) {
        View view = getView();
        if (view == null) return;
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.main);
        swipeRefreshLayout.setRefreshing(refresh);
    }

    private void loadData() {
        switch (activityType) {
            case Offline:
                doneViewModel.getOfflineActivities(activityTab);
                break;
            case Online:
                doneViewModel.getOnlineActivities(activityTab);
                break;
        }
    }

}
