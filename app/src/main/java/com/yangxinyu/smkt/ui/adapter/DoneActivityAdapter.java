package com.yangxinyu.smkt.ui.adapter;

import static java.lang.String.*;

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
import java.util.Locale;

/**
 * 已完成（线上/线下） 活动Adapter
 */
public class DoneActivityAdapter extends DiffAdapter<ReaderActivity> {

    public interface OnClickListener {
        void onDetail(ReaderActivity activity);
    }

    private OnClickListener listener;

    public DoneActivityAdapter(List<ReaderActivity> list, OnClickListener onClickListener) {
        super(list);
        this.listener = onClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_done_activity, parent, false);
        return new DoneActivityAdapter.VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ReaderActivity data = getList().get(position);
        View itemView = holder.itemView;
        TextView dateTimeView = itemView.findViewById(R.id.activity_datetime);
        TextView dateTimeWeekView = itemView.findViewById(R.id.activity_datetime_week);
        TextView nameView = itemView.findViewById(R.id.activity_name);
        TextView detailDatetimeView = itemView.findViewById(R.id.activity_detail_datetime);
        TextView detailAddressView = itemView.findViewById(R.id.activity_detail_address);
        TextView detailUserinfoView = itemView.findViewById(R.id.activity_detail_userinfo);

        TextView nicknameView = itemView.findViewById(R.id.activity_nickname);
        TextView publishDatetime = itemView.findViewById(R.id.activity_publish_datetime);
        View actionView = itemView.findViewById(R.id.activity_action);

        dateTimeView.setText(StringUtil.datetime2str(data.getDatetime(), StringUtil.PATTERN_MM_DD));
        dateTimeWeekView.setText(StringUtil.datetime2str(data.getDatetime(), StringUtil.PATTERN_EEEE));
        nameView.setText(data.getName());
        detailDatetimeView.setText(StringUtil.datetime2str(data.getDatetime(), StringUtil.PATTERN_ACTIVITY));
        detailAddressView.setText(data.getAddress());
        nicknameView.setText(data.getPublisher().getNickname());
        publishDatetime.setText(StringUtil.datetime2str(data.getDatetime(), StringUtil.PATTERN_ACTIVITY_PUBLISH));
        detailUserinfoView.setText(format(Locale.getDefault(), "共%d人，已报%d人", data.getPublisherTotalNumber(), data.getPublisherSignedNumber()));
        actionView.setOnClickListener((v) -> {
            if (this.listener != null) {
                this.listener.onDetail(data);
            }
        });

    }


    public static class VH extends RecyclerView.ViewHolder {
        public VH(@NonNull View itemView) {
            super(itemView);
        }
    }
}