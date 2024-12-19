package com.yangxinyu.smkt.ui.vo;

import com.yangxinyu.smkt.repository.entity.ReaderActivity;

import java.util.ArrayList;
import java.util.List;

public class Converts {

    public static List<ReaderActivity.ActivityClass> doneClasses(DoneActivityTab tab) {
        List<ReaderActivity.ActivityClass> classes = new ArrayList<>();
        switch (tab) {
            case All:
                classes.add(ReaderActivity.ActivityClass.Book);
                classes.add(ReaderActivity.ActivityClass.Film);
                classes.add(ReaderActivity.ActivityClass.Magic);
                classes.add(ReaderActivity.ActivityClass.Tea);
                break;
            case Book:
                classes.add(ReaderActivity.ActivityClass.Book);
                break;
            case Tea:
                classes.add(ReaderActivity.ActivityClass.Tea);
                break;
            case Magic:
                classes.add(ReaderActivity.ActivityClass.Magic);
                break;
        }
        return classes;
    }

    public static List<ReaderActivity.ActivityClass> todoClasses(TodoActivityTab tab) {
        List<ReaderActivity.ActivityClass> classes = new ArrayList<>();
        switch (tab) {
            case All:
                classes.add(ReaderActivity.ActivityClass.Book);
                classes.add(ReaderActivity.ActivityClass.Film);
                classes.add(ReaderActivity.ActivityClass.Magic);
                classes.add(ReaderActivity.ActivityClass.Tea);
                break;
            case Book:
                classes.add(ReaderActivity.ActivityClass.Book);
                break;
            case Film:
                classes.add(ReaderActivity.ActivityClass.Film);
                break;
            case Tea:
                classes.add(ReaderActivity.ActivityClass.Tea);
                break;
            case Magic:
                classes.add(ReaderActivity.ActivityClass.Magic);
                break;
        }
        return classes;
    }
}
