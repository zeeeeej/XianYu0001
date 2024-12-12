package com.yangxinyu.smkt.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.base.BaseFragment;
import com.yangxinyu.smkt.model.entity.MyActivity;
import com.yangxinyu.smkt.util.StringUtil;

public class DoneFragment extends BaseFragment {
    public static final String KEY_MY_ACTIVITY = "MyActivity";
    public static final String KEY_PROGRESS = "Progress";
    private static final String PATTERN_ACTIVITY = "MM.dd  HH:mm  EEEE";
    private static final String PROGRESS_AND = "/";

    @Override
    public int layoutId() {
        return R.layout.fragment_done;
    }

    public static DoneFragment newInstance(MyActivity myActivity, int progress, int max) {
        DoneFragment fragment = new DoneFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_MY_ACTIVITY, myActivity);
        args.putString(KEY_PROGRESS, (progress+1) + PROGRESS_AND + max);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void init(View view) {
        super.init(view);
        Bundle arguments = getArguments();
        if (arguments != null) {
            MyActivity myActivity = arguments.getParcelable(KEY_MY_ACTIVITY);
            String progress = arguments.getString(KEY_PROGRESS,"0/0");
            refreshActivity(myActivity);
            refreshProgress(progress);
        }
    }

    private void refreshProgress(String progress) {
        View view = getView();
        if (view == null) return;
        TextView progressView = view.findViewById(R.id.activity_progress);
        progressView.setText(progress);
    }

    private void refreshActivity(MyActivity myActivity) {
        View view = getView();
        if (view == null) return;
        ImageView iconView = view.findViewById(R.id.activity_icon);
        TextView typeView = view.findViewById(R.id.activity_type);
        TextView nameView = view.findViewById(R.id.activity_name);
        TextView datetimeView = view.findViewById(R.id.activity_datetime);
        TextView addressView = view.findViewById(R.id.activity_address);
        if (myActivity != null) {
            iconView.setImageResource(R.mipmap.mine_done_book_sample);
            String type = "-";
            switch (myActivity.getType()) {
                case Online:
                    type = "线上";
                    break;
                case Offline:
                    type = "线下";
                    break;
            }
            typeView.setText(type);
            nameView.setText(myActivity.getName());
            datetimeView.setText(StringUtil.datetime2str(myActivity.getDatetime(), PATTERN_ACTIVITY));
            addressView.setText(myActivity.getAddress());
        }

    }
}
