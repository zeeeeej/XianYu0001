package com.yangxinyu.smkt.model.entity;

public class User {
    private String username;
    private String id;
    private String nickname;

    public User() {
    }

    public User(String username, String id, String nickname) {
        this.username = username;
        this.id = id;
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
