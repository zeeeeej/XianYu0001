package com.yangxinyu.smkt.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.repository.entity.ReaderActivity;
import com.yangxinyu.smkt.util.StringUtil;

import java.util.List;

/**
 * 未完成 活动Adapter
 */
public class TodoActivityAdapter extends DiffAdapter<ReaderActivity> {

    public interface OnSignedClickListener {
        void onSigned(ReaderActivity activity);

    }

    public interface OnNavigationClickListener {
        void onNavigation(ReaderActivity activity);
    }

    private OnSignedClickListener onSignedClickListener;
    private OnNavigationClickListener onNavigationClickListener;

    public TodoActivityAdapter(List<ReaderActivity> list, OnSignedClickListener onSignedClickListener, OnNavigationClickListener onNavigationClickListener) {
        super(list);
        this.onSignedClickListener = onSignedClickListener;
        this.onNavigationClickListener = onNavigationClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo_activity, parent, false);
        return new TodoActivityAdapter.VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i("TodoActivityAdapter", "onBindViewHolder : " + getList().size());
        ReaderActivity data = getList().get(position);
        View itemView = holder.itemView;
        TextView dateTimeView = itemView.findViewById(R.id.activity_datetime);
        TextView nameView = itemView.findViewById(R.id.activity_name);
        TextView addressView = itemView.findViewById(R.id.activity_address);
        TextView nicknameView = itemView.findViewById(R.id.activity_nickname);
        TextView signActionView = itemView.findViewById(R.id.activity_action_sign);
        View navigationView = itemView.findViewById(R.id.todo_nav);
        dateTimeView.setText(StringUtil.datetime2str(data.getDatetime(), StringUtil.PATTERN_ACTIVITY));
        nameView.setText(data.getName());
        addressView.setText(data.getAddress());
        nicknameView.setText(data.getPublisher().getNickname());
        ReaderActivity.ActivityStatus status = data.getStatus();
        ReaderActivity.ActivitySigned signed = data.getSigned();

        String statusText = "";
        boolean enable = false;
        int background = 0;
        switch (status) {
            case Done:
                statusText = "已结束";
                background = R.drawable.activity_action_sign_bg_disable;
                break;
            case Todo:
                statusText = "活动未开始";
                background = R.drawable.activity_action_sign_bg_disable;
                break;
            case Doing:
                boolean done = signed == ReaderActivity.ActivitySigned.Done;
                statusText = done ? "已签到" : "签到";
                enable = !done;
                background = done ? R.drawable.activity_action_sign_bg_disable : R.drawable.activity_action_sign_bg;
                break;
        }

        signActionView.setText(statusText);
        signActionView.setEnabled(enable);
        signActionView.setBackgroundResource(background);
        signActionView.setOnClickListener((v)->{
            if (this.onSignedClickListener!=null){
                this.onSignedClickListener.onSigned(data);
            }
        });
        navigationView.setOnClickListener((v)->{
            if (this.onNavigationClickListener!=null){
                this.onNavigationClickListener.onNavigation(data);
            }
        });
    }


    public static class VH extends RecyclerView.ViewHolder {
        public VH(@NonNull View itemView) {
            super(itemView);
        }
    }
}