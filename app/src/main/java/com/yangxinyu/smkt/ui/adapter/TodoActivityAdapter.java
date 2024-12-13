package com.yangxinyu.smkt.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.model.entity.MyActivity;
import com.yangxinyu.smkt.ui.MineDoneFragment;
import com.yangxinyu.smkt.util.StringUtil;

import java.util.List;

public class TodoActivityAdapter extends DiffAdapter<MyActivity> {


    public TodoActivityAdapter(List<MyActivity> list) {
        super(list);
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
        MyActivity data = getList().get(position);
        View itemView = holder.itemView;
        TextView dateTimeView = itemView.findViewById(R.id.activity_datetime);
        TextView nameView = itemView.findViewById(R.id.activity_name);
        TextView addressView = itemView.findViewById(R.id.activity_address);
        TextView nicknameView = itemView.findViewById(R.id.activity_nickname);
        TextView signActionView = itemView.findViewById(R.id.activity_action_sign);
        TextView signView = itemView.findViewById(R.id.activity_sign);
        dateTimeView.setText(StringUtil.datetime2str(data.getDatetime(), MineDoneFragment.PATTERN_ACTIVITY));
        nameView.setText(data.getName());
        addressView.setText(data.getAddress());
        nicknameView.setText(data.getPublisher().getNickname());
        MyActivity.ActivityStatus status = data.getStatus();
        MyActivity.ActivitySigned signed = data.getSigned();

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
                boolean done = signed == MyActivity.ActivitySigned.Done;
                statusText = done ? "已签到" : "签到";
                enable = !done;
                background = done ? R.drawable.activity_action_sign_bg_disable : R.drawable.activity_action_sign_bg;
                break;
        }

        signActionView.setText(statusText);
        signActionView.setEnabled(enable);
        signActionView.setBackgroundResource(background);
    }


    public static class VH extends RecyclerView.ViewHolder {
        public VH(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class BookVH extends RecyclerView.ViewHolder {
        public BookVH(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class TeaVH extends RecyclerView.ViewHolder {
        public TeaVH(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class MagicVH extends RecyclerView.ViewHolder {
        public MagicVH(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class FilmVH extends RecyclerView.ViewHolder {
        public FilmVH(@NonNull View itemView) {
            super(itemView);
        }
    }

}