package com.yangxinyu.smkt.repository.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * 活动用户
 */
public class User implements Parcelable {
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

    protected User(Parcel in) {
        username = in.readString();
        id = in.readString();
        nickname = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(id);
        dest.writeString(nickname);
    }
}
