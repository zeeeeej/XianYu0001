package com.yangxinyu.smkt.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.yangxinyu.smkt.MainActivity;
import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.ui.base.BaseFragment;
import com.yangxinyu.smkt.repository.entity.ReaderActivity;
import com.yangxinyu.smkt.repository.entity.User;
import com.yangxinyu.smkt.ui.viewmodel.MainViewModel;
import com.yangxinyu.smkt.ui.viewmodel.MineViewModel;
import com.yangxinyu.smkt.ui.widget.IndicateView;
import com.yangxinyu.smkt.util.ToastUtil;
import com.yangxinyu.smkt.util.XLog;

import java.text.MessageFormat;
import java.util.List;

/**
 * 主页-我的
 */
public class HomeMinePageFragment extends BaseFragment {
    /* 已参加活动 当前页 */
    private int currentDoneActivityPosition = 0;

    private MainViewModel viewModel;
    private MineViewModel mineViewModel;
    private AlertDialog logoutDialog;

    @Override
    public int layoutId() {
        return R.layout.fragment_mine;
    }

    public static HomeMinePageFragment newInstance() {
        HomeMinePageFragment fragment = new HomeMinePageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init(View view) {
        super.init(view);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mineViewModel = new ViewModelProvider(this).get(MineViewModel.class);
        viewModel.user.observe(this, (user) -> {
            refreshUserinfo(user);
        });
        mineViewModel.historyActivities.observe(this, (activities) -> {
            refreshDoneViewPager(activities);
        });
        mineViewModel.getHistoryActivitiesEffect.observe(this, (effect) -> {
            switch (effect) {

                case Idle:
                    setRefreshing(false);
                    break;
                case Start:
                    setRefreshing(true);
                    break;
                case Success:
                case Fail:
                    mineViewModel.resetGetHistoryActivitiesEffect();
                    break;
            }
        });

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.main);
        swipeRefreshLayout.setOnRefreshListener(this::loadData);

        view.findViewById(R.id.mine_icon).setOnClickListener((v) -> {
            MainActivity.doSomethingBeforeCheckUserLogin(this, () -> {
                logoutDialog = new AlertDialog.Builder(requireActivity())
                        .setTitle("提示")
                        .setMessage("退出当前用户？")
                        .setNegativeButton("取消", (dialog, position) -> {
                            if (logoutDialog != null) {
                                logoutDialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", (dialog, position) -> {
                            if (logoutDialog != null) {
                                logoutDialog.dismiss();
                            }
                            viewModel.loginOut();
                        })
                        .create();
                logoutDialog.show();
            });
        });
        view.findViewById(R.id.mine_more_info).setOnClickListener((v) -> {
            MainActivity.doSomethingBeforeCheckUserLogin(this, () -> {
                ToastUtil.show("TODO 个人信息");
            });
        });
        view.findViewById(R.id.mine_vip_action).setOnClickListener((v) -> {
            MainActivity.doSomethingBeforeCheckUserLogin(this, () -> {
                ToastUtil.show("TODO 立即续费");
            });
        });
        view.findViewById(R.id.mine_item_pub).setOnClickListener((v) -> {
            MainActivity.doSomethingBeforeCheckUserLogin(this, () -> {
                ToastUtil.show("TODO 我发起的活动");
            });
        });
        view.findViewById(R.id.mine_item_contact).setOnClickListener((v) -> {
            MainActivity.doSomethingBeforeCheckUserLogin(this, () -> {
                ToastUtil.show("TODO 联系我们");
            });
        });
        view.findViewById(R.id.mine_item_code).setOnClickListener((v) -> {

            MainActivity.doSomethingBeforeCheckUserLogin(this, () -> {
                ToastUtil.show("TODO 兑换码");
            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        XLog.i("HomeMinePageFragment onResume");
        loadData();
    }

    private void loadData() {
        mineViewModel.getHistoryActivities();
    }

    private void refreshUserinfo(User user) {
        View view = getView();
        if (view == null) return;
        TextView nicknameView = view.findViewById(R.id.mine_nickname);
        TextView idView = view.findViewById(R.id.mine_id_value);
        nicknameView.setText(user == null ? "-" : user.getNickname());
        idView.setText(user == null ? "-" : user.getId());
    }

    private void refreshDoneViewPager(List<ReaderActivity> list) {
        View view = getView();
        if (view == null) return;
        ViewPager2 vp = view.findViewById(R.id.mine_done_vp);
        IndicateView indicateView = view.findViewById(R.id.mine_activity_indicate);
        vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacksAndMessages(null);
                currentDoneActivityPosition = position;
                int size = list.size();
                if (size > 0) {
                    indicateView.setProgress(currentDoneActivityPosition % size);
                    auto(vp, indicateView, size);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        TextView countView = view.findViewById(R.id.mine_item_done_text_count);
        countView.setText(MessageFormat.format("({0})", list.size()));
        vp.setAdapter(new DoneAdapter(requireActivity(), list));
        indicateView.setMax(list.size());
        auto(vp, indicateView, list.size());


    }

    /* 自动切换 已参加活动 当前页 */
    private void auto(ViewPager2 vp, IndicateView indicateView, int count) {
        if (count == 0) {
            handler.removeCallbacksAndMessages(null);
            return;
        }
        handler.postDelayed(() -> {
            currentDoneActivityPosition++;
            vp.setCurrentItem(currentDoneActivityPosition % count);
            indicateView.setProgress(currentDoneActivityPosition % count);
            auto(vp, indicateView, count);

        }, 3000);
    }

    private void setRefreshing(boolean refresh) {
        View view = getView();
        if (view == null) return;
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.main);
        swipeRefreshLayout.setEnabled(false);
        //swipeRefreshLayout.setRefreshing(refresh);
    }

    private static class DoneAdapter extends FragmentStateAdapter {
        private final List<ReaderActivity> list;

        public DoneAdapter(@NonNull FragmentActivity fragmentActivity, List<ReaderActivity> list) {
            super(fragmentActivity);
            this.list = list;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return MineDoneFragment.newInstance(list.get(position), position, list.size());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}
