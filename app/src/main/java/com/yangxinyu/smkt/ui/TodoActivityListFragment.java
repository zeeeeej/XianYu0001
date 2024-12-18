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
import com.yangxinyu.smkt.ui.viewmodel.TodoViewModel;
import com.yangxinyu.smkt.ui.vo.TodoActivityTab;
import com.yangxinyu.smkt.ui.adapter.TodoActivityAdapter;
import com.yangxinyu.smkt.util.ToastUtil;
import com.yangxinyu.smkt.util.XLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 待参加-活动列表
 */
public class TodoActivityListFragment extends BaseFragment {
    public static final String KEY_MY_ACTIVITY_TYPE = "KEY_MY_ACTIVITY_TYPE";
    public static final String KEY_MY_ACTIVITY_TAB = "KEY_MY_ACTIVITY_TAB";
    private TodoViewModel todoReaderActivityViewModel;

    private final TodoActivityAdapter adapter = new TodoActivityAdapter(new ArrayList<>(),
            (activity) -> {
                MainActivity.doSomethingBeforeCheckUserLogin(this, () -> {
                    ToastUtil.show("TODO签到");
                });
            },
            (activity) -> {
                MainActivity.doSomethingBeforeCheckUserLogin(this, () -> {
                    ToastUtil.show("TODO导航");
                });
            });

    private ReaderActivity.ActivityType activityType;
    private TodoActivityTab activityTab;

    @Override
    public int layoutId() {
        return R.layout.fragment_activity_list;
    }

    public static TodoActivityListFragment newInstance(ReaderActivity.ActivityType type, TodoActivityTab tab) {
        TodoActivityListFragment fragment = new TodoActivityListFragment();
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
            int tab = arguments.getInt(KEY_MY_ACTIVITY_TAB, TodoActivityTab.All.ordinal());
            activityType = ReaderActivity.ActivityType.values()[type];
            activityTab = TodoActivityTab.values()[tab];
            System.out.println("tab:" + tab + ",type:" + type);
            initRecyclerView(view);
        }
        todoReaderActivityViewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        todoReaderActivityViewModel.todoActivities.observe(this, (activities) -> {
            refreshRecyclerView(activities);
        });
        todoReaderActivityViewModel.getTodoActivitiesEffect.observe(this, (effect) -> {
            switch (effect) {

                case Idle:
                    setRefreshing(false);
                    break;
                case Start:
                    setRefreshing(true);
                    break;
                case Success:
                case Fail:
                    todoReaderActivityViewModel.resetGetTodoActivitiesEffect();
                    break;
            }
        });

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.main);
        swipeRefreshLayout.setOnRefreshListener(this::loadData);
    }

    @Override
    public void onResume() {
        super.onResume();
        XLog.i("TodoActivityListFragment onResume");
        loadData();
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.activity_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
    }

    private void refreshRecyclerView(List<ReaderActivity> list) {
        refreshEmptyView(list.isEmpty());
        adapter.setData(list);
    }

    private void refreshEmptyView(boolean empty) {
        View view = getView();
        if (view==null)return;
        View emptyView= view.findViewById(R.id.empty_view);
        emptyView.setVisibility(empty?View.VISIBLE:View.INVISIBLE);
    }

    private void setRefreshing(boolean refresh) {
        View view = getView();
        if (view == null) return;
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.main);
        swipeRefreshLayout.setRefreshing(refresh);
    }

    private void loadData() {
        todoReaderActivityViewModel.getTodoActivities(activityType, activityTab);
    }
}
