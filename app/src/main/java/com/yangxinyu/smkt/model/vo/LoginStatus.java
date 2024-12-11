package com.yangxinyu.smkt.model.vo;

import com.yangxinyu.smkt.model.entity.User;

public class LoginStatus {
    private boolean doing = false;
    private boolean success = false;
    private User user = null;

    public boolean isDoing() {
        return doing;
    }

    public void setDoing(boolean doing) {
        this.doing = doing;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
