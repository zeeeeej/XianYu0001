package com.yangxinyu.smkt.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yangxinyu.smkt.repository.DefaultRepository;
import com.yangxinyu.smkt.repository.entity.ReaderActivity;
import com.yangxinyu.smkt.ui.base.Effect;

import java.util.ArrayList;
import java.util.List;

public class MineViewModel extends ViewModel {

    private final MutableLiveData<List<ReaderActivity>> _historyActivities = new MutableLiveData<>(new ArrayList<>());


    public LiveData<List<ReaderActivity>> historyActivities = _historyActivities;

    private final MutableLiveData<Effect> _getHistoryActivitiesEffect = new MutableLiveData<>(Effect.Idle);

    public LiveData<Effect> getHistoryActivitiesEffect = _getHistoryActivitiesEffect;

    public void getHistoryActivities() {
        _getHistoryActivitiesEffect.postValue(Effect.Start);
        DefaultRepository.getInstance().getAllMyActivities((activities) -> {
            this._historyActivities.postValue(activities);
            _getHistoryActivitiesEffect.postValue(Effect.Success);
        });
    }

    public void resetGetHistoryActivitiesEffect() {
        this._getHistoryActivitiesEffect.setValue(Effect.Idle);
    }
}
