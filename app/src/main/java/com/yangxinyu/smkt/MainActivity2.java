package com.yangxinyu.smkt;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.yangxinyu.smkt.base.BaseActivity;
import com.yangxinyu.smkt.model.DefaultRepository;
import com.yangxinyu.smkt.model.vo.Tab;
import com.yangxinyu.smkt.ui.LoginFragment;
import com.yangxinyu.smkt.ui.MinePageFragment;
import com.yangxinyu.smkt.ui.OfflinePageFragment;
import com.yangxinyu.smkt.ui.OnlinePageFragment;
import com.yangxinyu.smkt.ui.TodoPageFragment;

public class MainActivity2 extends BaseActivity {
    public static final String LOGIN_FRAGMENT_TAG = "LoginFragmentTag";
    /* 当前选中的Tab */
    private Tab selectedTab = Tab.Offline;

    @Override
    public int layoutId() {
        return R.layout.activity_main3;
    }

    @Override
    public void init() {

        View todoTabView = findViewById(R.id.home_tab_todo);
        View offlineTabView = findViewById(R.id.home_tab_offline);
        View onlineTabView = findViewById(R.id.home_tab_online);
        View mineTabView = findViewById(R.id.home_tab_mine);
        refreshByTab(selectedTab);
        offlineTabView.setOnClickListener((v) -> {
            selectedTab = Tab.Offline;
            refreshByTab();
        });
        onlineTabView.setOnClickListener((v) -> {
            selectedTab = Tab.Online;
            refreshByTab();
        });
        todoTabView.setOnClickListener((v) -> {
            selectedTab = Tab.Todo;
            refreshByTab();
        });
        mineTabView.setOnClickListener((v) -> {
            selectedTab = Tab.Mine;
            refreshByTab();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLogin();
    }


    private void refreshByTab() {
        refreshByTab(selectedTab);
    }

    private void refreshByTab(Tab tab) {
        refreshOfflineTab(tab == Tab.Offline);
        refreshOnlineTab(tab == Tab.Online);
        refreshTodoTab(tab == Tab.Todo);
        refreshMineTab(tab == Tab.Mine);

        refreshTitle(tab);
        showPageFragment(selectedTab);
    }

    private void refreshTitle(Tab tab) {
        TextView titleView = findViewById(R.id.home_title);
        int title = 0;
        switch (tab) {
            case Offline:
                title = R.string.home_tab_offline;
                break;
            case Online:
                title = R.string.home_tab_online;
                break;
            case Todo:
                title = R.string.home_tab_todo;
                break;
            case Mine:
                title = R.string.home_tab_mine;
                break;
        }
        if (title != 0) {
            titleView.setText(title);
        }
    }

    private void refreshOfflineTab(boolean selected) {
        ImageView iconView = findViewById(R.id.home_tab_offline_icon);
        TextView textView = findViewById(R.id.home_tab_offline_text);
        View page = findViewById(R.id.fragment_container_home_tab_page_offline);
        if (selected) {
            iconView.setImageResource(Tab.Offline.getSel());
            textView.setTextColor(getColor(R.color.home_tab_text_sel));
            page.setVisibility(View.VISIBLE);
        } else {
            iconView.setImageResource(Tab.Offline.getNormal());
            textView.setTextColor(getColor(R.color.home_tab_text));
            page.setVisibility(View.INVISIBLE);
        }
    }

    private void refreshOnlineTab(boolean selected) {
        ImageView iconView = findViewById(R.id.home_tab_online_icon);
        TextView textView = findViewById(R.id.home_tab_online_text);
        View page = findViewById(R.id.fragment_container_home_tab_page_online);
        if (selected) {
            iconView.setImageResource(Tab.Online.getSel());
            textView.setTextColor(getColor(R.color.home_tab_text_sel));
            page.setVisibility(View.VISIBLE);
        } else {
            iconView.setImageResource(Tab.Online.getNormal());
            textView.setTextColor(getColor(R.color.home_tab_text));
            page.setVisibility(View.INVISIBLE);
        }
    }

    private void refreshTodoTab(boolean selected) {
        ImageView iconView = findViewById(R.id.home_tab_todo_icon);
        TextView textView = findViewById(R.id.home_tab_todo_text);
        View page = findViewById(R.id.fragment_container_home_tab_page_todo);
        if (selected) {
            iconView.setImageResource(Tab.Todo.getSel());
            textView.setTextColor(getColor(R.color.home_tab_text_sel));
            page.setVisibility(View.VISIBLE);
        } else {
            iconView.setImageResource(Tab.Todo.getNormal());
            textView.setTextColor(getColor(R.color.home_tab_text));
            page.setVisibility(View.INVISIBLE);
        }
    }

    private void refreshMineTab(boolean selected) {
        ImageView iconView = findViewById(R.id.home_tab_mine_icon);
        TextView textView = findViewById(R.id.home_tab_mine_text);
        View page = findViewById(R.id.fragment_container_home_tab_page_mine);
        if (selected) {
            iconView.setImageResource(Tab.Mine.getSel());
            textView.setTextColor(getColor(R.color.home_tab_text_sel));
            page.setVisibility(View.VISIBLE);
        } else {
            iconView.setImageResource(Tab.Mine.getNormal());
            textView.setTextColor(getColor(R.color.home_tab_text));
            page.setVisibility(View.INVISIBLE);
        }
    }


    private void checkLogin() {
        if (DefaultRepository.getInstance().checkLogin()) {
            showFragment(LoginFragment.newInstance());
        }
    }

    private void showPageFragment(Tab tab) {
        try {
            String tag = tab.getTag();
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            Fragment fragment = supportFragmentManager.findFragmentByTag(tag);
            int containerViewId = 0;
            if (fragment == null) {
                switch (tab) {
                    case Offline:
                        fragment = OfflinePageFragment.newInstance();
                        containerViewId = R.id.fragment_container_home_tab_page_offline;
                        break;
                    case Online:
                        fragment = OnlinePageFragment.newInstance();
                        containerViewId = R.id.fragment_container_home_tab_page_online;
                        break;
                    case Todo:
                        fragment = TodoPageFragment.newInstance();
                        containerViewId = R.id.fragment_container_home_tab_page_todo;
                        break;
                    case Mine:
                        fragment = MinePageFragment.newInstance();
                        containerViewId = R.id.fragment_container_home_tab_page_mine;
                        break;
                    default:
                        break;
                }
                if (fragment == null || containerViewId == 0) return;
                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                fragmentTransaction.add(containerViewId, fragment, tag);
                fragmentTransaction.commitAllowingStateLoss();

            }
        } catch (Exception e) {
            e.printStackTrace();
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