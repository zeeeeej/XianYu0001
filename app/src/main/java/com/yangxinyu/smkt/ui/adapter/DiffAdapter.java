package com.yangxinyu.smkt.ui.adapter;


import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

abstract class DiffAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<T> list;

    public List<T> getList() {
        return list;
    }

    public DiffAdapter(List<T> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<T> source) {
        MyDiffCallback diffCallback = new MyDiffCallback(list, source);
        list.clear();
        list.addAll(source);
//        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
//        diffResult.dispatchUpdatesTo(this);

        notifyDataSetChanged();
    }

    public void addData(T data) {
        list.add(data);
        notifyDataSetChanged();
    }

    private class MyDiffCallback extends DiffUtil.Callback {
        private final List<T> oldList;
        private final List<T> newList;

        private MyDiffCallback(List<T> oldList, List<T> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList == null ? 0 : oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList == null ? 0 : newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            if (oldList != null && newList != null && !oldList.isEmpty() && !newList.isEmpty()) {
                T t1 = oldList.get(oldItemPosition);
                T t2 = newList.get(newItemPosition);
                return t1 == t2;
            }
            return false;
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            if (oldList != null && newList != null && !oldList.isEmpty() && !newList.isEmpty()) {
                T t1 = oldList.get(oldItemPosition);
                T t2 = newList.get(newItemPosition);
                return t1.equals(t2);
            }
            return false;
        }
    }
}
