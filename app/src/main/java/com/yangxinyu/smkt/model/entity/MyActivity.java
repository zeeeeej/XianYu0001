package com.yangxinyu.smkt.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MyActivity implements Parcelable {
    private String name;
    private long datetime;
    private String address;
    private String icon;
    private ActivityType type;
    private ActivityClass clz;
    private ActivityStatus status;
    private ActivitySigned signed;
    private User publisher;


    public MyActivity() {
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    protected MyActivity(Parcel in) {
        name = in.readString();
        datetime = in.readLong();
        address = in.readString();
        icon = in.readString();
    }

    public static final Creator<MyActivity> CREATOR = new Creator<MyActivity>() {
        @Override
        public MyActivity createFromParcel(Parcel in) {
            return new MyActivity(in);
        }

        @Override
        public MyActivity[] newArray(int size) {
            return new MyActivity[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public ActivityClass getClz() {
        return clz;
    }

    public void setClz(ActivityClass clz) {
        this.clz = clz;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeLong(datetime);
        dest.writeString(address);
        dest.writeString(icon);
    }

    public ActivitySigned getSigned() {
        return signed;
    }

    public void setSigned(ActivitySigned signed) {
        this.signed = signed;
    }

    public enum ActivityType {
        Offline, Online;
    }

    public enum ActivityClass {
        Book,
        Film,
        Tea,
        Magic,
        ;
    }

    public enum ActivityStatus {
        Done,
        Todo,
        Doing;
    }

    public enum ActivitySigned {
        Done,
        Todo
        ;
    }

    public static ActivityType toActivityType(int value){
        if (ActivityType.Offline.ordinal() == value) {
            return ActivityType.Offline;
        }else  if (ActivityType.Online.ordinal() == value) {
            return ActivityType.Online;
        } else{
            throw new IllegalStateException("错误的value:"+value);
        }
    }
}


