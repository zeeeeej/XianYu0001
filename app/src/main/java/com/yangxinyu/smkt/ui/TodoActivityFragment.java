package com.yangxinyu.smkt.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.ui.base.BaseFragment;
import com.yangxinyu.smkt.repository.entity.ReaderActivity;
import com.yangxinyu.smkt.ui.vo.TodoActivityTab;

import org.jetbrains.annotations.NotNull;

/**
 * 待参加-活动
 * Tab+VP2
 */
public class TodoActivityFragment extends BaseFragment {
    public static final String KEY_MY_ACTIVITY_TYPE = "KEY_MY_ACTIVITY_TYPE";
    private ReaderActivity.ActivityType activityType;

    @Override
    public int layoutId() {
        return R.layout.fragment_todo_activity;
    }

    public static TodoActivityFragment newInstance(ReaderActivity.ActivityType type) {
        TodoActivityFragment fragment = new TodoActivityFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_MY_ACTIVITY_TYPE, type.ordinal());
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
            int type = arguments.getInt(KEY_MY_ACTIVITY_TYPE, ReaderActivity.ActivityType.Offline.ordinal());
            activityType = ReaderActivity.ActivityType.values()[type];
        }

        initViewPager(view);

    }

    private void initViewPager(@NotNull View view) {
        TabLayout tabView = view.findViewById(R.id.todo_activity_tab);
        ViewPager2 vpView = view.findViewById(R.id.todo_activity_vp);
        TodoActivityTab[] values = TodoActivityTab.values();
        vpView.setUserInputEnabled(true);

        vpView.setAdapter(new FragmentStateAdapter(getChildFragmentManager(), getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {

                return TodoActivityListFragment.newInstance(activityType, values[position]);
            }

            @Override
            public int getItemCount() {
                return values.length;
            }
        });


        tabView.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                if (customView == null) return;
                TextView textView = customView.findViewById(R.id.text);
                textView.setTextColor(Color.WHITE);
                textView.setBackgroundResource(R.drawable.todo_activity_tab_bg_sel);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                if (customView == null) return;
                TextView textView = customView.findViewById(R.id.text);
                textView.setTextColor(getResources().getColor(R.color.todo_activity_tab_normal));
                textView.setBackgroundResource(R.drawable.todo_activity_tab_bg_normal);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        new TabLayoutMediator(tabView, vpView, (tab, position) -> {
            View customView = tab.getCustomView();
            if (customView == null) {
                View contentView = LayoutInflater.from(requireContext()).inflate(R.layout.view_todo_activity_tab, null, false);
                TextView textView = contentView.findViewById(R.id.text);
                TodoActivityTab value = values[position];
                String title = "";
                switch (value) {
                    case All:
                        title = "全部";
                        break;
                    case Book:
                        title = "书籍";
                        break;
                    case Film:
                        title = "电影";
                        break;
                    case Tea:
                        title = "茶话会";
                        break;
                    case Magic:
                        title = "创意活动";
                        break;
                }
                textView.setText(title);
                tab.setCustomView(contentView);
            }

        }).attach();
    }
}
