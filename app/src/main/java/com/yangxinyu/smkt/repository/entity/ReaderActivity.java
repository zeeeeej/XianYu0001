package com.yangxinyu.smkt.repository.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * 活动
 */
public class ReaderActivity implements Parcelable {

    private String id;
    /* 名称 */
    private String name;
    /* 时间 */
    private long datetime;
    /* 图片 */
    private String icon;
    /* 线上/线下 */
    private ActivityType type;
    /* 分类 */
    private ActivityClass clz;
    /* 状态 */
    private ActivityStatus status;
    /* 签到 */
    private ActivitySigned signed;
    /* 线上/线下地址 */
    private String address;
    /* 发布者 */
    private User publisher;
    /* 发起时间 */
    private long publisherDatetime;
    /* 发布人数 */
    private long publisherTotalNumber;
    /* 报名人数 */
    private long publisherSignedNumber;


    public ReaderActivity() {
    }

    protected ReaderActivity(Parcel in) {
        id = in.readString();
        name = in.readString();
        datetime = in.readLong();
        icon = in.readString();
        type = in.readParcelable(ActivityType.class.getClassLoader());
        clz = in.readParcelable(ActivityClass.class.getClassLoader());
        status = in.readParcelable(ActivityStatus.class.getClassLoader());
        signed = in.readParcelable(ActivitySigned.class.getClassLoader());
        address = in.readString();
        publisher = in.readParcelable(User.class.getClassLoader());
        publisherDatetime = in.readLong();
        publisherTotalNumber = in.readLong();
        publisherSignedNumber = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeLong(datetime);
        dest.writeString(icon);
        dest.writeParcelable(type, flags);
        dest.writeParcelable(clz, flags);
        dest.writeParcelable(status, flags);
        dest.writeParcelable(signed, flags);
        dest.writeString(address);
        dest.writeParcelable(publisher, flags);
        dest.writeLong(publisherDatetime);
        dest.writeLong(publisherTotalNumber);
        dest.writeLong(publisherSignedNumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ReaderActivity> CREATOR = new Creator<ReaderActivity>() {
        @Override
        public ReaderActivity createFromParcel(Parcel in) {
            return new ReaderActivity(in);
        }

        @Override
        public ReaderActivity[] newArray(int size) {
            return new ReaderActivity[size];
        }
    };

    public long getPublisherDatetime() {
        return publisherDatetime;
    }

    public void setPublisherDatetime(long publisherDatetime) {
        this.publisherDatetime = publisherDatetime;
    }

    public long getPublisherTotalNumber() {
        return publisherTotalNumber;
    }

    public void setPublisherTotalNumber(long publisherTotalNumber) {
        this.publisherTotalNumber = publisherTotalNumber;
    }

    public long getPublisherSignedNumber() {
        return publisherSignedNumber;
    }

    public void setPublisherSignedNumber(long publisherSignedNumber) {
        this.publisherSignedNumber = publisherSignedNumber;
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


    public ActivitySigned getSigned() {
        return signed;
    }

    public void setSigned(ActivitySigned signed) {
        this.signed = signed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public enum ActivityType implements Parcelable {
        Offline, Online;

        public static ActivityType from(int ordinal) {
            if (ActivityType.Offline.ordinal() == ordinal) {
                return ActivityType.Offline;
            } else if (ActivityType.Online.ordinal() == ordinal) {
                return ActivityType.Online;
            } else {
                throw new Error("wrong ordinal");
            }
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(ordinal());
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ActivityType> CREATOR = new Creator<ActivityType>() {
            @Override
            public ActivityType createFromParcel(Parcel in) {
                return ActivityType.from(in.readInt());
            }

            @Override
            public ActivityType[] newArray(int size) {
                return new ActivityType[size];
            }
        };
    }

    public enum ActivityClass implements Parcelable {
        Book,
        Film,
        Tea,
        Magic,
        ;

        public static ActivityClass from(int ordinal) {
            if (ActivityClass.Book.ordinal() == ordinal) {
                return ActivityClass.Book;
            } else if (ActivityClass.Film.ordinal() == ordinal) {
                return ActivityClass.Film;
            } else if (ActivityClass.Tea.ordinal() == ordinal) {
                return ActivityClass.Tea;
            } else if (ActivityClass.Magic.ordinal() == ordinal) {
                return ActivityClass.Magic;
            } else {
                throw new Error("wrong ordinal");
            }
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(ordinal());
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ActivityClass> CREATOR = new Creator<ActivityClass>() {
            @Override
            public ActivityClass createFromParcel(Parcel in) {
                return ActivityClass.from(in.readInt());
            }

            @Override
            public ActivityClass[] newArray(int size) {
                return new ActivityClass[size];
            }
        };
    }

    public enum ActivityStatus implements Parcelable {
        Done,
        Todo,
        Doing;

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(ordinal());
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ActivityStatus> CREATOR = new Creator<ActivityStatus>() {
            @Override
            public ActivityStatus createFromParcel(Parcel in) {
                return ActivityStatus.from(in.readInt());
            }

            @Override
            public ActivityStatus[] newArray(int size) {
                return new ActivityStatus[size];
            }
        };

        public static ActivityStatus from(int ordinal) {
            if (ActivityStatus.Done.ordinal() == ordinal) {
                return ActivityStatus.Done;
            } else if (ActivitySigned.Todo.ordinal() == ordinal) {
                return ActivityStatus.Todo;
            } else if (ActivityStatus.Doing.ordinal() == ordinal) {
                return ActivityStatus.Doing;
            } else {
                throw new Error("wrong ordinal");
            }
        }
    }

    public enum ActivitySigned implements Parcelable {
        Done,
        Todo;

        public static ActivitySigned from(int ordinal) {
            if (ActivitySigned.Done.ordinal() == ordinal) {
                return ActivitySigned.Done;
            } else if (ActivitySigned.Todo.ordinal() == ordinal) {
                return ActivitySigned.Todo;
            } else {
                throw new Error("wrong ordinal");
            }
        }

        public static final Creator<ActivitySigned> CREATOR = new Creator<ActivitySigned>() {
            @Override
            public ActivitySigned createFromParcel(Parcel in) {
                return ActivitySigned.from(in.readInt());
            }

            @Override
            public ActivitySigned[] newArray(int size) {
                return new ActivitySigned[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeInt(ordinal());
        }
    }
}


