package com.yangxinyu.smkt.ui;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.base.BaseFragment;
import com.yangxinyu.smkt.model.DefaultRepository;
import com.yangxinyu.smkt.model.entity.MyActivity;
import com.yangxinyu.smkt.model.vo.TodoActivityTab;
import com.yangxinyu.smkt.ui.adapter.TodoActivityAdapter;

import java.util.ArrayList;
import java.util.List;

public class TodoActivityListFragment extends BaseFragment {
    public static final String KEY_MY_ACTIVITY_TYPE = "KEY_MY_ACTIVITY_TYPE";
    public static final String KEY_MY_ACTIVITY_TAB = "KEY_MY_ACTIVITY_TAB";
    private final TodoActivityAdapter adapter = new TodoActivityAdapter(new ArrayList<>());
    private MyActivity.ActivityType activityType;
    private TodoActivityTab activityTab;

    @Override
    public int layoutId() {
        return R.layout.fragment_todo_activity_list;
    }

    public static TodoActivityListFragment newInstance(MyActivity.ActivityType type, TodoActivityTab tab) {
        TodoActivityListFragment fragment = new TodoActivityListFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_MY_ACTIVITY_TYPE, type.ordinal());
        args.putInt(KEY_MY_ACTIVITY_TAB, tab.ordinal());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void init(View view) {
        super.init(view);
        Bundle arguments = getArguments();
        if (arguments != null) {
            int type = arguments.getInt(KEY_MY_ACTIVITY_TYPE, MyActivity.ActivityType.Offline.ordinal());
            int tab = arguments.getInt(KEY_MY_ACTIVITY_TAB, TodoActivityTab.All.ordinal());
            activityType = MyActivity.ActivityType.values()[type];
            activityTab = TodoActivityTab.values()[tab];
            System.out.println("tab:" + tab + ",type:" + type);
            initRecyclerView(view);
        }

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.main);
        swipeRefreshLayout.setOnRefreshListener(this::loadData);
        swipeRefreshLayout.setRefreshing(true);
        this.loadData();
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
                case Film:
                    classes.add(MyActivity.ActivityClass.Film);
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
