package com.yangxinyu.smkt.model.vo;

import com.yangxinyu.smkt.R;

public enum Tab {

    Offline(R.mipmap.ic_home_tab_offline, R.mipmap.ic_home_tab_offline_sel, "offline"),
    Online(R.mipmap.ic_home_tab_online, R.mipmap.ic_home_tab_online_sel, "online"),
    Todo(R.mipmap.ic_home_tab_todo, R.mipmap.ic_home_tab_todo_sel, "todo"),
    Mine(R.mipmap.ic_home_tab_mine, R.mipmap.ic_home_tab_mine_sel, "mine");

    private final int normal;
    private final int sel;
    private final String tag;

    Tab(int normal, int sel, String tag) {
        this.normal = normal;
        this.sel = sel;
        this.tag = tag;
    }


    public int getNormal() {
        return normal;
    }

    public int getSel() {
        return sel;
    }

    public String getTag() {
        return tag;
    }
}
