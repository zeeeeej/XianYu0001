package com.yangxinyu.smkt.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yangxinyu.smkt.model.entity.User;
import com.yangxinyu.smkt.model.vo.LoginStatus;

/**
 * VM层：状态集合
 * 存储当前页面所有状态，更新数据等。
 */
@Deprecated
public class MainViewModel extends ViewModel {
    private final DefaultRepository repository = DefaultRepository.getInstance();
    private final MutableLiveData<User> _userLiveData = new MutableLiveData<>(null);
    public LiveData<User> userLiveData = _userLiveData;

    private final MutableLiveData<LoginStatus> _loginStatusLiveData = new MutableLiveData<>(null);
    public LiveData<LoginStatus> loginStatusLiveData = _loginStatusLiveData;

    public void resetLogin() {
        _loginStatusLiveData.postValue(null);
    }

    public void login() {
        LoginStatus loginStatus = new LoginStatus();
        loginStatus.setDoing(true);
        _loginStatusLiveData.setValue(loginStatus);
        repository.checkLogin(new DefaultRepository.LoginCallback() {
            @Override
            public void onSuccess(User user) {
                loginStatus.setSuccess(true);
                loginStatus.setUser(user);
                loginStatus.setDoing(false);
                _userLiveData.postValue(user);
//                _loginStatusLiveData.postValue(loginStatus);
            }

            @Override
            public void onFail(String msg) {
                loginStatus.setSuccess(false);
                loginStatus.setUser(null);
                loginStatus.setDoing(false);
                _loginStatusLiveData.postValue(null);
//                _loginStatusLiveData.postValue(loginStatus);
            }
        });
    }
}
