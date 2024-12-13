package com.yangxinyu.smkt.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.base.BaseFragment;
import com.yangxinyu.smkt.model.entity.MyActivity;

public class HomeTodoPageFragment extends BaseFragment {
    @Override
    public int layoutId() {
        return R.layout.fragment_todo;
    }

    public static HomeTodoPageFragment newInstance() {
        HomeTodoPageFragment fragment = new HomeTodoPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init(View view) {
        super.init(view);
        TabLayout tabView = view.findViewById(R.id.todo_tab);
        ViewPager2 vpView = view.findViewById(R.id.todo_vp);
        vpView.setUserInputEnabled(false);
        MyActivity.ActivityType[] values = MyActivity.ActivityType.values();
        vpView.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {

                return TodoActivityFragment.newInstance(values[position]);
            }

            @Override
            public int getItemCount() {
                return values.length;
            }
        });
        new TabLayoutMediator(tabView, vpView, new TabLayoutMediator.TabConfigurationStrategy() {

            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TabLayout.TabView tabInnerView = tab.view;
                MyActivity.ActivityType value = values[position];
                String title = "";
                switch (value){
                    case Online:
                        title = "线上活动";
                        break;
                    case Offline:
                        title = "线下活动";
                        break;
                }
                if (title.isEmpty()) return;
                tab.setText(title);
            }
        }).attach();
    }
}
