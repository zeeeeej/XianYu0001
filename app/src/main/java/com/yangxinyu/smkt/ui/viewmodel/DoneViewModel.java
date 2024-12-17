package com.yangxinyu.smkt.ui.viewmodel;

import static com.yangxinyu.smkt.ui.viewmodel.MainViewModel.doneClasses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yangxinyu.smkt.repository.DefaultRepository;
import com.yangxinyu.smkt.repository.entity.ReaderActivity;
import com.yangxinyu.smkt.ui.vo.DoneActivityTab;
import com.yangxinyu.smkt.ui.vo.Effect;

import java.util.ArrayList;
import java.util.List;

public class DoneViewModel extends ViewModel {

    private final MutableLiveData<List<ReaderActivity>> _onlineActivities = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<ReaderActivity>> _offlineActivities = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<ReaderActivity>> onlineActivities = _onlineActivities;
    public LiveData<List<ReaderActivity>> offlineActivities = _offlineActivities;

    private final MutableLiveData<Effect> _getOnlineActivitiesEffect = new MutableLiveData<>(Effect.Idle);
    private final MutableLiveData<Effect> _getOfflineActivitiesEffect = new MutableLiveData<>(Effect.Idle);

    public LiveData<Effect> getOnlineActivitiesEffect = _getOnlineActivitiesEffect;
    public LiveData<Effect> getOfflineActivitiesEffect = _getOfflineActivitiesEffect;

    public void getOnlineActivities(DoneActivityTab tab) {
        resetGetOnlineActivitiesEffect();
        _getOnlineActivitiesEffect.postValue(Effect.Start);
        List<ReaderActivity.ActivityClass> activityClasses = doneClasses(tab);
        DefaultRepository.getInstance().getDoneActivities(ReaderActivity.ActivityType.Online, activityClasses, (activities) -> {
            this._onlineActivities.postValue(activities);
            _getOnlineActivitiesEffect.postValue(Effect.Success);
        });
    }

    public void getOfflineActivities(DoneActivityTab tab) {
        resetGetOnlineActivitiesEffect();
        _getOfflineActivitiesEffect.postValue(Effect.Start);
        List<ReaderActivity.ActivityClass> activityClasses = doneClasses(tab);
        DefaultRepository.getInstance().getDoneActivities(ReaderActivity.ActivityType.Offline, activityClasses, (activities) -> {
            this._offlineActivities.postValue(activities);
            _getOfflineActivitiesEffect.postValue(Effect.Success);
        });
    }


    public void resetGetOnlineActivitiesEffect() {
        this._getOnlineActivitiesEffect.setValue(Effect.Idle);
    }

    public void resetGetOfflineActivitiesEffect() {
        this._getOfflineActivitiesEffect.setValue(Effect.Idle);
    }

}
