package com.yangxinyu.smkt;

import static com.yangxinyu.smkt.MainActivity2.LOGIN_FRAGMENT_TAG;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.yangxinyu.smkt.base.BaseActivity;
import com.yangxinyu.smkt.model.DefaultRepository;
import com.yangxinyu.smkt.model.vo.Tab;
import com.yangxinyu.smkt.ui.LoginFragment;
import com.yangxinyu.smkt.ui.HomeMinePageFragment;
import com.yangxinyu.smkt.ui.HomeOfflinePageFragment;
import com.yangxinyu.smkt.ui.HomeOnlinePageFragment;
import com.yangxinyu.smkt.ui.HomeTodoPageFragment;

public class MainActivity extends BaseActivity {

    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        ViewPager2 vp = findViewById(R.id.home_vp);
        vp.setUserInputEnabled(false);
        View todoTabView = findViewById(R.id.home_tab_todo);
        View offlineTabView = findViewById(R.id.home_tab_offline);
        View onlineTabView = findViewById(R.id.home_tab_online);
        View mineTabView = findViewById(R.id.home_tab_mine);
        Tab[] values = Tab.values();
        int size = values.length;
        vp.setOffscreenPageLimit(size);
        vp.setAdapter(new FragmentStateAdapter(this) {
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

    @Override
    protected void onResume() {
        super.onResume();
        checkLogin();
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
            if (DefaultRepository.getInstance().checkLogin()){
                mainView.setBackgroundColor(Color.WHITE);
            }else{

                mainView.setBackgroundResource(backgroundColor);
            }
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


    private void checkLogin() {
        if (DefaultRepository.getInstance().checkLogin()) {
            showFragment(LoginFragment.newInstance());
        }
    }


    private void showFragment(Fragment fragment) {
        try {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container_login, fragment, LOGIN_FRAGMENT_TAG);
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}