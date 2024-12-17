package com.yangxinyu.smkt.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.ui.base.BaseFragment;
import com.yangxinyu.smkt.repository.entity.ReaderActivity;
import com.yangxinyu.smkt.util.StringUtil;

/**
 * 我的-已参加活动
 */
public class MineDoneFragment extends BaseFragment {
    public static final String KEY_MY_ACTIVITY = "MyActivity";
    public static final String KEY_MAX = "Max";
    public static final String KEY_PROGRESS = "Progress";
    private static final String PROGRESS_AND = "/";

    @Override
    public int layoutId() {
        return R.layout.fragment_done;
    }

    public static MineDoneFragment newInstance(ReaderActivity myActivity, int progress, int max) {
        MineDoneFragment fragment = new MineDoneFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_MY_ACTIVITY, myActivity);
        args.putInt(KEY_PROGRESS, progress);
        args.putInt(KEY_MAX, max);
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
            ReaderActivity myActivity = arguments.getParcelable(KEY_MY_ACTIVITY);
            int progress = arguments.getInt(KEY_PROGRESS, 0);
            int max = arguments.getInt(KEY_MAX, 0);
            refreshActivity(myActivity);
            refreshProgress(progress, max);
        }


    }

    private void refreshProgress(int progress, int max) {
        View view = getView();
        if (view == null) return;
        TextView progressView = view.findViewById(R.id.activity_progress);
        progressView.setText((progress + 1) + PROGRESS_AND + max);
    }

    private void refreshActivity(ReaderActivity myActivity) {
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
            datetimeView.setText(StringUtil.datetime2str(myActivity.getDatetime(), StringUtil.PATTERN_ACTIVITY));
            addressView.setText(myActivity.getAddress());
        }

    }
}
