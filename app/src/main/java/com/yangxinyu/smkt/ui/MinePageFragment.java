package com.yangxinyu.smkt.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.base.BaseFragment;
import com.yangxinyu.smkt.model.DefaultRepository;
import com.yangxinyu.smkt.model.entity.MyActivity;
import com.yangxinyu.smkt.model.entity.User;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MinePageFragment extends BaseFragment {
    private int currentDoneActivityPosition = 0;

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
        User user = DefaultRepository.getInstance().getUser();
        refreshUserinfo(user);
        List<MyActivity> doneActivities = DefaultRepository.getInstance().getDoneActivities();
        refreshDoneViewPager(doneActivities);
    }

    private void refreshUserinfo(User user) {
        View view = getView();
        if (view == null) return;
        TextView nicknameView = view.findViewById(R.id.mine_nickname);
        TextView idView = view.findViewById(R.id.mine_id_value);
        nicknameView.setText(user == null ? "-" : user.getNickname());
        idView.setText(user == null ? "-" : user.getId());
    }

    private void refreshDoneViewPager(List<MyActivity> list) {
        View view = getView();
        if (view == null) return;
        ViewPager2 vp = view.findViewById(R.id.mine_done_vp);
        TextView countView = view.findViewById(R.id.mine_item_done_text_count);
        countView.setText(MessageFormat.format("({0})", list.size()));
        vp.setAdapter(new DoneAdapter(requireActivity(), list));
        auto(vp, list.size());
    }

    private void auto(ViewPager2 vp, int count) {
        if (count == 0) {
            handler.removeCallbacksAndMessages(null);
            return;
        }
        handler.postDelayed(() -> {
            currentDoneActivityPosition++;
            vp.setCurrentItem(currentDoneActivityPosition % count);
            auto(vp, count);
        }, 3000);
    }

    private static class DoneAdapter extends FragmentStateAdapter {
        private final List<MyActivity> list;

        public DoneAdapter(@NonNull FragmentActivity fragmentActivity, List<MyActivity> list) {
            super(fragmentActivity);
            this.list = list;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return DoneFragment.newInstance(list.get(position), position, list.size());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}
