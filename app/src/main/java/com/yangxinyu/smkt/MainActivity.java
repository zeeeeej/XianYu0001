package com.yangxinyu.smkt;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.yangxinyu.smkt.ui.base.BaseActivity;
import com.yangxinyu.smkt.repository.entity.User;
import com.yangxinyu.smkt.ui.vo.Tab;
import com.yangxinyu.smkt.ui.LoginFragment;
import com.yangxinyu.smkt.ui.HomeMinePageFragment;
import com.yangxinyu.smkt.ui.HomeOfflinePageFragment;
import com.yangxinyu.smkt.ui.HomeOnlinePageFragment;
import com.yangxinyu.smkt.ui.HomeTodoPageFragment;
import com.yangxinyu.smkt.ui.viewmodel.MainViewModel;
import com.yangxinyu.smkt.util.Callback;
import com.yangxinyu.smkt.util.ToastUtil;

public class MainActivity extends BaseActivity {

    private MainViewModel viewModel;

    private final Tab[] values = Tab.values();

    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        ViewPager2 vp = findViewById(R.id.home_vp);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.user.observe(this, this::refreshUi);
        viewModel.checkLoginEffect.observe(this, (effect) -> {
            switch (effect) {

                case Idle:
                    break;
                case Start:
                    break;
                case Success:
                    vp.setCurrentItem(0);
                    break;
                case Fail:
                    showLogin();
                    break;
            }
        });

        viewModel.checkLogin();
        viewModel.loginOutEffect.observe(this, (effect) -> {
            switch (effect) {

                case Idle:
                    break;
                case Start:
                    break;
                case Success:
                    ToastUtil.show("退出成功");
                    vp.setCurrentItem(0);
                    showLogin();
                    viewModel.resetLoginOutEffect();
                    break;
                case Fail:

                    break;
            }
        });
        vp.setUserInputEnabled(false);
        View todoTabView = findViewById(R.id.home_tab_todo);
        View offlineTabView = findViewById(R.id.home_tab_offline);
        View onlineTabView = findViewById(R.id.home_tab_online);
        View mineTabView = findViewById(R.id.home_tab_mine);

        int size = values.length;
        vp.setOffscreenPageLimit(size);
        vp.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(), this.getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = HomeOfflinePageFragment.newInstance();
                        break;
                    case 1:
                        fragment = HomeOnlinePageFragment.newInstance();
                        break;
                    case 2:
                        fragment = HomeTodoPageFragment.newInstance();
                        break;
                    case 3:
                        fragment = HomeMinePageFragment.newInstance();
                        break;
                    default:
                        break;
                }
                if (fragment == null) throw new IllegalStateException("程序写错了");
                return fragment;
            }

            @Override
            public int getItemCount() {
                return size;
            }
        });
        vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Tab tab = values[position];
                refreshTitleAndBackground(tab);
                refreshOfflineTab(tab == Tab.Offline);
                refreshOnlineTab(tab == Tab.Online);
                refreshTodoTab(tab == Tab.Todo);
                refreshMineTab(tab == Tab.Mine);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        offlineTabView.setOnClickListener((v) -> {
            vp.setCurrentItem(0);
        });
        onlineTabView.setOnClickListener((v) -> {
            vp.setCurrentItem(1);
        });
        todoTabView.setOnClickListener((v) -> {
            vp.setCurrentItem(2);
        });
        mineTabView.setOnClickListener((v) -> {
            vp.setCurrentItem(3);
        });
    }

    private void refreshTitleAndBackground(Tab tab) {
        TextView titleView = findViewById(R.id.home_title);
        View iconView = findViewById(R.id.home_title_icon);
        View mainView = findViewById(R.id.main);
        int title = 0;
        int backgroundColor = 0;
        switch (tab) {
            case Offline:
                title = R.string.home_tab_offline;
                backgroundColor = R.drawable.home_tab_offline_bg;
                iconView.setVisibility(View.VISIBLE);
                titleView.setVisibility(View.INVISIBLE);
                break;
            case Online:
                title = R.string.home_tab_online;
                backgroundColor = R.drawable.home_tab_online_bg;
                iconView.setVisibility(View.VISIBLE);
                titleView.setVisibility(View.INVISIBLE);
                break;
            case Todo:
                title = R.string.home_tab_todo;
                backgroundColor = R.drawable.home_tab_todo_bg;
                iconView.setVisibility(View.INVISIBLE);
                titleView.setVisibility(View.VISIBLE);
                break;
            case Mine:
                title = R.string.home_tab_mine;
                backgroundColor = R.drawable.home_tab_mine_bg;
                iconView.setVisibility(View.INVISIBLE);
                titleView.setVisibility(View.VISIBLE);
                break;
        }
        if (title != 0) {
            titleView.setText(title);
        }
        if (backgroundColor != 0) {
            mainView.setBackgroundResource(backgroundColor);
        }
    }

    private void refreshOfflineTab(boolean selected) {
        ImageView iconView = findViewById(R.id.home_tab_offline_icon);
        TextView textView = findViewById(R.id.home_tab_offline_text);
        if (selected) {
            iconView.setImageResource(Tab.Offline.getSel());
            textView.setTextColor(getColor(R.color.home_tab_text_sel));
        } else {
            iconView.setImageResource(Tab.Offline.getNormal());
            textView.setTextColor(getColor(R.color.home_tab_text));
        }
    }

    private void refreshOnlineTab(boolean selected) {
        ImageView iconView = findViewById(R.id.home_tab_online_icon);
        TextView textView = findViewById(R.id.home_tab_online_text);
        if (selected) {
            iconView.setImageResource(Tab.Online.getSel());
            textView.setTextColor(getColor(R.color.home_tab_text_sel));
        } else {
            iconView.setImageResource(Tab.Online.getNormal());
            textView.setTextColor(getColor(R.color.home_tab_text));
        }
    }

    private void refreshTodoTab(boolean selected) {
        ImageView iconView = findViewById(R.id.home_tab_todo_icon);
        TextView textView = findViewById(R.id.home_tab_todo_text);
        if (selected) {
            iconView.setImageResource(Tab.Todo.getSel());
            textView.setTextColor(getColor(R.color.home_tab_text_sel));
        } else {
            iconView.setImageResource(Tab.Todo.getNormal());
            textView.setTextColor(getColor(R.color.home_tab_text));
        }
    }

    private void refreshMineTab(boolean selected) {
        ImageView iconView = findViewById(R.id.home_tab_mine_icon);
        TextView textView = findViewById(R.id.home_tab_mine_text);
        if (selected) {
            iconView.setImageResource(Tab.Mine.getSel());
            textView.setTextColor(getColor(R.color.home_tab_text_sel));
        } else {
            iconView.setImageResource(Tab.Mine.getNormal());
            textView.setTextColor(getColor(R.color.home_tab_text));
        }
    }

    private void refreshUi(User user) {
        if (user != null) {
            ViewPager2 vp = findViewById(R.id.home_vp);
            refreshTitleAndBackground(values[vp.getCurrentItem()]);
            dismissLogin();
        }
    }

    public static final String LOGIN_FRAGMENT_TAG = "LOGIN_FRAGMENT_TAG";

    public void showLogin() {
        try {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            Fragment loginFragment = supportFragmentManager.findFragmentByTag(LOGIN_FRAGMENT_TAG);
            if (loginFragment != null) {
                ((DialogFragment) loginFragment).dismiss();
            }
            LoginFragment newFragment = new LoginFragment();
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container_login, newFragment, LOGIN_FRAGMENT_TAG).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dismissLogin() {
        try {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            Fragment loginFragment = supportFragmentManager.findFragmentByTag(LOGIN_FRAGMENT_TAG);
            if (loginFragment!=null){
                supportFragmentManager.beginTransaction().remove(loginFragment).commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void doSomethingBeforeCheckUserLogin(Fragment fragment, Callback callback) {
        FragmentActivity fragmentActivity = fragment.requireActivity();
        if (fragmentActivity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) fragmentActivity;
            User user = mainActivity.viewModel.user.getValue();
            if (user == null) {
                mainActivity.showLogin();
            } else {
                callback.call();
            }
        }
    }
}