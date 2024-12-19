package com.yangxinyu.smkt.repository;

import com.yangxinyu.smkt.repository.entity.ReaderActivity;
import com.yangxinyu.smkt.repository.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mock {

    public static final String DEFAULT_USER = "昵称昵称";
    public static final String DEFAULT_USER_2 = "昵称昵称昵称";
    public static final String DEFAULT_USER_ID = "1234567890";
    public static final String DEFAULT_USERNAME = "1234567890";

    private static ReaderActivity mock(ReaderActivity.ActivityStatus status,
                                       ReaderActivity.ActivitySigned signed,
                                       User publisher,
                                       int signedNumber,
                                       int totalNumber


    ) {
        ReaderActivity act = new ReaderActivity();
        act.setName("名称名称名称名称名称...");
        act.setDatetime(System.currentTimeMillis());
        // act.setIcon();
        //act.setType(activity.getType());
        //act.setClz(activity.getClz());
        act.setStatus(status);
        act.setSigned(signed);
        //act.setAddress("地点位置地点位置地点位置地点位...");
        act.setPublisher(publisher);
        act.setPublisherDatetime(System.currentTimeMillis());
        act.setPublisherSignedNumber(signedNumber);
        act.setPublisherTotalNumber(totalNumber);
        return act;
    }

    private static String type2Address(ReaderActivity.ActivityType type) {


        String address = "";
        switch (type) {
            case Offline:
                address = "地点位置地点位置地点位置地点位位置地点位置地点位...";
                break;
            case Online:
                address = "腾讯会议";
                break;
        }
        return address;
    }

    public static List<ReaderActivity> mockList(Random random) {
        User publisher = new User(Mock.DEFAULT_USERNAME, Mock.DEFAULT_USER_ID, Mock.DEFAULT_USER_2);
        List<ReaderActivity> allActivities = new ArrayList<>();
        ReaderActivity.ActivityStatus[] statues = ReaderActivity.ActivityStatus.values();
        ReaderActivity.ActivitySigned[] signeds = ReaderActivity.ActivitySigned.values();
        ReaderActivity.ActivityType[] activityTypes = ReaderActivity.ActivityType.values();
        ReaderActivity.ActivityClass[] values = ReaderActivity.ActivityClass.values();
        for (ReaderActivity.ActivityType activityType : activityTypes) {
            for (ReaderActivity.ActivityClass value : values) {
                for (int i = 0; i < 5; i++) {
                    ReaderActivity.ActivityStatus status = statues[random.nextInt(statues.length)];
                    ReaderActivity.ActivitySigned signed = signeds[random.nextInt(signeds.length)];
                    int total = 5 + random.nextInt(10);
                    int number = random.nextInt(total);
                    ReaderActivity mock = mock(status, signed, publisher, number, total);
                    mock.setType(activityType);
                    mock.setAddress(type2Address(activityType));
                    mock.setClz(value);
                    allActivities.add(mock);
                }
            }
        }
        return allActivities;
    }


}
