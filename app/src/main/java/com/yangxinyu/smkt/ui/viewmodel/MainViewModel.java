package com.yangxinyu.smkt.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yangxinyu.smkt.repository.DefaultRepository;
import com.yangxinyu.smkt.repository.entity.User;
import com.yangxinyu.smkt.ui.base.Effect;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<User> _user = new MutableLiveData<>(null);
    public LiveData<User> user = _user;

    private final MutableLiveData<Effect> _loginEffect = new MutableLiveData<>(Effect.Idle);
    private final MutableLiveData<Effect> _loginOutEffect = new MutableLiveData<>(Effect.Idle);
    private final MutableLiveData<Effect> _checkLoginEffect = new MutableLiveData<>(Effect.Idle);
    private final MutableLiveData<Boolean> _firstEnter = new MutableLiveData<>(false);

    public LiveData<Effect> loginEffect = _loginEffect;
    public LiveData<Effect> loginOutEffect = _loginOutEffect;
    public LiveData<Effect> checkLoginEffect = _checkLoginEffect;
    public LiveData<Boolean> firstEnter = _firstEnter;

    public void checkLogin() {
        _checkLoginEffect.postValue(Effect.Start);
        DefaultRepository.getInstance().checkLogin(new DefaultRepository.CheckLoginCallback() {
            @Override
            public void onSuccess(User user) {
                _user.postValue(user);
                _checkLoginEffect.postValue(Effect.Success);
                _firstEnter.postValue(true);
            }

            @Override
            public void onFail(String msg) {
                _checkLoginEffect.postValue(Effect.Fail);
            }
        });
    }

    public void login(String username) {

        _loginEffect.postValue(Effect.Start);
        DefaultRepository.getInstance().login(username, new DefaultRepository.LoginCallback() {
            @Override
            public void onSuccess(User user) {
                _user.postValue(user);
                _loginEffect.postValue(Effect.Success);
                _firstEnter.postValue(true);
            }

            @Override
            public void onFail(String msg) {
                _loginEffect.postValue(Effect.Fail);
            }
        });
    }

    public void loginOut() {
        _loginOutEffect.postValue(Effect.Start);
        DefaultRepository.getInstance().loginOut((success) -> {
            if (success) {
                _user.postValue(null);
                _firstEnter.postValue(false);
                _loginOutEffect.postValue(Effect.Success);
            } else {
                _loginOutEffect.postValue(Effect.Fail);
            }
        });
    }

    public void resetLoginEffect() {
        this._loginEffect.setValue(Effect.Idle);
    }

    public void resetLoginOutEffect() {
        this._loginOutEffect.setValue(Effect.Idle);
    }

    public void updateFirstEnter() {
        this._firstEnter.setValue(false);
    }
}
