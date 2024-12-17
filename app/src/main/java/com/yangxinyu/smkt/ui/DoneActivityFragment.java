package com.yangxinyu.smkt.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.ui.base.BaseFragment;
import com.yangxinyu.smkt.repository.entity.ReaderActivity;
import com.yangxinyu.smkt.ui.vo.DoneActivityTab;

import org.jetbrains.annotations.NotNull;

/**
 * 已完成-活动
 * Tab+VP2
 */
public class DoneActivityFragment extends BaseFragment {
    private static final String KEY_MY_ACTIVITY_TYPE = "KEY_MY_ACTIVITY_TYPE";
    private ReaderActivity.ActivityType activityType;

    @Override
    public int layoutId() {
        return R.layout.fragment_activity_done;
    }

    public static DoneActivityFragment newInstance(ReaderActivity.ActivityType type) {
        DoneActivityFragment fragment = new DoneActivityFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_MY_ACTIVITY_TYPE, type.ordinal());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init(View view) {
        super.init(view);
        Bundle arguments = getArguments();
        if (arguments != null) {
            int type = arguments.getInt(KEY_MY_ACTIVITY_TYPE, ReaderActivity.ActivityType.Offline.ordinal());
            activityType = ReaderActivity.ActivityType.from(type);
        }
        initViewPager(view);

    }

    private void initViewPager(@NotNull View view) {
        TabLayout tabView = view.findViewById(R.id.done_tab);
        ViewPager2 vpView = view.findViewById(R.id.done_vp);
        DoneActivityTab[] values = DoneActivityTab.values();
        vpView.setUserInputEnabled(true);
        vpView.setOffscreenPageLimit(values.length);
        vpView.setAdapter(new FragmentStateAdapter(getChildFragmentManager(), this.getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return DoneActivityListFragment.newInstance(activityType, values[position]);
            }

            @Override
            public int getItemCount() {
                return values.length;
            }
        });

        int offlineDrawable = (R.drawable.done_indicate_offline_bg);
        int onLineDrawable = (R.drawable.done_indicate_online_bg);
        int selDrawable = activityType == ReaderActivity.ActivityType.Offline ? offlineDrawable : onLineDrawable;
        int normalDrawable = (R.drawable.done_indicate_normal_bg);
        int normalColor = getResources().getColor(R.color.done_tab_normal);
        int selColor = getResources().getColor(R.color.done_tab_sel);

        tabView.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                if (customView == null) return;
                TextView textView = customView.findViewById(R.id.text);
                ImageView iconView = customView.findViewById(R.id.icon);
                textView.setTextColor(selColor);
                iconView.setImageResource(selDrawable);
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                if (customView == null) return;
                TextView textView = customView.findViewById(R.id.text);
                ImageView iconView = customView.findViewById(R.id.icon);
                textView.setTextColor(normalColor);
                iconView.setImageResource(normalDrawable);
                textView.setTypeface(textView.getTypeface(), Typeface.NORMAL);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        new TabLayoutMediator(tabView, vpView, (tab, position) -> {
            View customView = tab.getCustomView();
            if (customView == null) {
                View contentView = LayoutInflater.from(requireContext()).inflate(R.layout.view_done_activity_tab, null, false);
                TextView textView = contentView.findViewById(R.id.text);
                ImageView iconView = contentView.findViewById(R.id.icon);
                DoneActivityTab value = values[position];
                String title = "";
                switch (value) {
                    case All:
                        title = "全部";
                        break;
                    case Book:
                        title = "书籍";
                        break;
                    case Tea:
                        title = "茶话会";
                        break;
                    case Magic:
                        title = "创意活动";
                        break;
                }
                textView.setText(title);
                iconView.setImageResource(R.drawable.todo_activity_tab_bg_normal);
                tab.setCustomView(contentView);

            }

        }).attach();
    }
}
