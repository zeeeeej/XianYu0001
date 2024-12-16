package com.yangxinyu.smkt.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.base.BaseFragment;
import com.yangxinyu.smkt.model.DefaultRepository;
import com.yangxinyu.smkt.model.entity.MyActivity;
import com.yangxinyu.smkt.model.vo.DoneActivityTab;
import com.yangxinyu.smkt.ui.adapter.TodoActivityAdapter;
import com.yangxinyu.smkt.util.XLog;

import java.util.ArrayList;
import java.util.List;

public class DoneActivityListFragment extends BaseFragment {
    public static final String KEY_MY_ACTIVITY_TYPE = "KEY_MY_ACTIVITY_TYPE";
    public static final String KEY_MY_ACTIVITY_TAB = "KEY_MY_ACTIVITY_TAB";
    private final TodoActivityAdapter adapter = new TodoActivityAdapter(new ArrayList<>());
    private MyActivity.ActivityType activityType;
    private DoneActivityTab activityTab;

    @Override
    public int layoutId() {
        return R.layout.fragment_todo_activity_list;
    }

    public static DoneActivityListFragment newInstance(MyActivity.ActivityType type, DoneActivityTab tab) {
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
            int type = arguments.getInt(KEY_MY_ACTIVITY_TYPE, MyActivity.ActivityType.Offline.ordinal());
            int tab = arguments.getInt(KEY_MY_ACTIVITY_TAB, DoneActivityTab.All.ordinal());
            activityType = MyActivity.toActivityType(type);
            activityTab = DoneActivityTab.values()[tab];
            initRecyclerView(view);
            XLog.d("DoneActivityListFragment " + "init " + "activityType:" + activityType + ",activityTab:" + activityTab);
            SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.main);
            swipeRefreshLayout.setOnRefreshListener(this::loadData);
            swipeRefreshLayout.setRefreshing(true);
            this.loadData();
        }


    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.activity_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
    }

    private void refreshRecyclerView(List<MyActivity> list) {
        adapter.setData(list);
    }

    private void loadData() {
        new Thread(() -> {
            List<MyActivity.ActivityClass> classes = new ArrayList<>();
            switch (activityTab) {
                case All:
                    classes.add(MyActivity.ActivityClass.Book);
                    classes.add(MyActivity.ActivityClass.Film);
                    classes.add(MyActivity.ActivityClass.Magic);
                    classes.add(MyActivity.ActivityClass.Tea);
                    break;
                case Book:
                    classes.add(MyActivity.ActivityClass.Book);
                    break;
                case Tea:
                    classes.add(MyActivity.ActivityClass.Tea);
                    break;
                case Magic:
                    classes.add(MyActivity.ActivityClass.Magic);
                    break;
            }
            DefaultRepository.getInstance().getTodoActivities(activityType, classes, (list) -> {
                runOnUiThread(() -> {
                    refreshRecyclerView(list);
                    finishRefresh();
                });
            });
        }).start();
    }

    private void finishRefresh() {
        View view = getView();
        if (view == null) return;
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.main);
        swipeRefreshLayout.setRefreshing(false);
    }
}
