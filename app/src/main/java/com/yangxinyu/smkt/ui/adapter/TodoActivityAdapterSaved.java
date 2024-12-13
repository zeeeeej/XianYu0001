package com.yangxinyu.smkt.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yangxinyu.smkt.R;
import com.yangxinyu.smkt.model.entity.MyActivity;

import java.util.List;

public class TodoActivityAdapterSaved extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<MyActivity> list;

    public TodoActivityAdapterSaved(List<MyActivity> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo_activity, parent, false);
        if (viewType == MyActivity.ActivityClass.Book.ordinal()) {
            return new TodoActivityAdapterSaved.BookVH(view);
        } else if (viewType == MyActivity.ActivityClass.Film.ordinal()) {
            return new TodoActivityAdapterSaved.FilmVH(view);
        } else if (viewType == MyActivity.ActivityClass.Tea.ordinal()) {
            return new TodoActivityAdapterSaved.TeaVH(view);
        } else if (viewType == MyActivity.ActivityClass.Magic.ordinal()) {
            return new TodoActivityAdapterSaved.MagicVH(view);
        } else {
            throw new IllegalStateException(("错误的viewType:" + viewType));
        }

    }

    @Override
    public int getItemViewType(int position) {
        MyActivity myActivity = list.get(position);
        MyActivity.ActivityClass clz = myActivity.getClz();
        return clz.ordinal();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyActivity data = list.get(position);
        View itemView = holder.itemView;
        if (holder instanceof BookVH) {

        } else if (holder instanceof FilmVH) {

        } else if (holder instanceof TeaVH) {

        } else if (holder instanceof MagicVH) {

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<MyActivity> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
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