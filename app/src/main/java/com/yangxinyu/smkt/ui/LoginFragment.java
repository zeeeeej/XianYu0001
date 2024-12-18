package com.yangxinyu.smkt.ui;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;

import android.widget.Button;
import android.widget.ImageView;

import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.repository.DefaultRepository;
import com.yangxinyu.smkt.repository.Mock;
import com.yangxinyu.smkt.ui.base.BaseFragment;
import com.yangxinyu.smkt.ui.viewmodel.MainViewModel;
import com.yangxinyu.smkt.util.ToastUtil;

/**
 * 登录页面
 */
public class LoginFragment extends BaseFragment {

    private boolean checked = false;
    private MainViewModel viewModel;

    @Override
    public int layoutId() {
        return R.layout.fragment_login;
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        viewModel.loginEffect.observe(this, (effect) -> {
            switch (effect) {
                case Idle:
                    updateCommitButton(false);
                    break;
                case Start:
                    updateCommitButton(true);
                    break;
                case Success:
                    ToastUtil.show("登录成功");
                    viewModel.resetLoginEffect();
                    dismiss();
                    break;
                case Fail:
                    updateCommitButton(false);
                    ToastUtil.show("登录失败");
                    viewModel.resetLoginEffect();
                    break;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.login_back).setOnClickListener((v) -> {
            dismiss();
        });

        View commitView = view.findViewById(R.id.login_commit);
        View frameLayout = view.findViewById(R.id.main);
        frameLayout.setSystemUiVisibility(
                SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        ImageView checkView = view.findViewById(R.id.login_check);

        View agreementView = view.findViewById(R.id.login_agreement);
        View privacyView = view.findViewById(R.id.login_privacy);

        checkView.setOnClickListener((v) -> {
            checked = !checked;
            checkView.setImageResource(checkDrawable());
        });
        agreementView.setOnClickListener((v) -> {
            ToastUtil.show("TODO用户协议");
        });
        privacyView.setOnClickListener((v) -> {
            ToastUtil.show("TODO隐私协议");
        });
        commitView.setOnClickListener((v) -> {
            if (checked) {
                updateCommitButton(true);
                String username = Mock.DEFAULT_USERNAME;

                viewModel.login(username);
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