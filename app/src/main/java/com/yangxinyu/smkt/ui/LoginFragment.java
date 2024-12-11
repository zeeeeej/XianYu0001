package com.yangxinyu.smkt.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.base.BaseFragment;
import com.yangxinyu.smkt.model.DefaultRepository;
import com.yangxinyu.smkt.model.entity.User;
import com.yangxinyu.smkt.util.ToastUtil;

public class LoginFragment extends BaseFragment {

    //    private MainViewModel viewModel;
    private boolean checked = false;

    public LoginFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        // 获取和Activity同一个MainViewModel对象
//        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
//        viewModel.loginStatusLiveData.observe(this, (status) -> {
//            updateCommitButton(status != null && status.isDoing());
//            if (status == null) return;
//            if (status.isSuccess()) {
//                ToastUtil.show("登录成功");
//            } else {
//                ToastUtil.show("登录失败");
//            }
//            viewModel.resetLogin();
//        });
//
//        viewModel.userLiveData.observe(this, (user) -> {
//            if (user != null) {
//                dismiss();
//            }
//        });
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.login_back).setOnClickListener((v) -> {
            dismiss();
        });

        View commitView = view.findViewById(R.id.login_commit);
        ImageView checkView = view.findViewById(R.id.login_check);

        View agreementView = view.findViewById(R.id.login_agreement);
        View privacyView = view.findViewById(R.id.login_privacy);

        checkView.setOnClickListener((v) -> {
            checked = !checked;
            checkView.setImageResource(checkDrawable());
        });
        agreementView.setOnClickListener((v) -> {
            ToastUtil.show("TODO");
        });
        privacyView.setOnClickListener((v) -> {
            ToastUtil.show("TODO");
        });
        commitView.setOnClickListener((v) -> {
            if (checked) {
                updateCommitButton(true);
                DefaultRepository.getInstance().login(new DefaultRepository.LoginCallback() {
                    @Override
                    public void onSuccess(User user) {
                        runOnUiThread(() -> {
                            ToastUtil.show("登录成功");
                            dismiss();
                        });
                    }

                    @Override
                    public void onFail(String msg) {
                        runOnUiThread(() -> {
                            updateCommitButton(false);
                            ToastUtil.show(msg);
                        });
                    }
                });
            } else {
                ToastUtil.show("请阅读用户协议和隐私协议并勾选然后登录");
            }
        });
    }

    private void updateCommitButton(boolean doing) {
        View view = getView();
        if (view != null) {
            Button commitView = view.findViewById(R.id.login_commit);
            commitView.setEnabled(!doing);
            commitView.setAlpha(doing ? .5f : 1f);
            commitView.setText(doing ? getString(R.string.login_btn_doing) : getString(R.string.login_btn));
        }
    }

    private int checkDrawable() {
        return checked ? R.mipmap.ic_login_check_on : R.mipmap.ic_login_check_off;
    }

    private void dismiss() {
        try {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .remove(this)
                    .commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}