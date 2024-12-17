package com.yangxinyu.smkt.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yangxinyu.smkt.repository.DefaultRepository;
import com.yangxinyu.smkt.repository.entity.ReaderActivity;
import com.yangxinyu.smkt.repository.entity.User;
import com.yangxinyu.smkt.ui.vo.Effect;
import com.yangxinyu.smkt.ui.vo.TodoActivityTab;

import java.util.ArrayList;
import java.util.List;

public class TodoViewModel extends ViewModel {

    private final MutableLiveData<User> _user = new MutableLiveData<>(null);
    private final MutableLiveData<List<ReaderActivity>> _todoActivities = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<ReaderActivity>> todoActivities = _todoActivities;
    private final MutableLiveData<Effect> _getTodoActivitiesEffect = new MutableLiveData<>(Effect.Idle);
    public LiveData<Effect> getTodoActivitiesEffect = _getTodoActivitiesEffect;


    public void resetGetTodoActivitiesEffect() {
        this._getTodoActivitiesEffect.setValue(Effect.Idle);

    }

    public void getTodoActivities(ReaderActivity.ActivityType activityType, TodoActivityTab tab) {
        resetGetTodoActivitiesEffect();
        _getTodoActivitiesEffect.postValue(Effect.Start);
        List<ReaderActivity.ActivityClass> activityClasses = MainViewModel.todoClasses(tab);
        DefaultRepository.getInstance().getTodoActivities(activityType, activityClasses, (activities) -> {
            this._todoActivities.postValue(activities);
            _getTodoActivitiesEffect.postValue(Effect.Success);
        });
    }
}
