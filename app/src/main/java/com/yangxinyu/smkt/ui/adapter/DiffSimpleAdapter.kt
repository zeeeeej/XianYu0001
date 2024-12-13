package com.yangxinyu.smkt.ui.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class DiffSimpleAdapter<T>(
    list: List<T>,
    private val onClick: (T, Int) -> Unit = { _, _ -> },
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    abstract val itemSameBlock: (T, T) -> Boolean
    abstract val contentSameBlock: (T, T) -> Boolean
    abstract val getChangePayload: (T, T) -> Bundle?

    abstract val resId: Int
    private val mList: MutableList<T> = mutableListOf()
    val list: List<T>
        get() = mList.toList()

    init {
        mList.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LayoutInflater.from(parent.context).inflate(resId, parent, false).run {
            VH(this)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClick.invoke(list[position], position)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(source: List<T>) {
        val diffCallback =
            MyDiffCallback(list, source, itemSameBlock, contentSameBlock, getChangePayload)
        mList.clear()
        mList.addAll(source)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)

    }

    fun addData(data: T) {
        mList.add(data)
        notifyDataSetChanged()
    }


    private inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView)


    private class MyDiffCallback<T>(
        private val oldData: List<T>,
        private val newData: List<T>,
        val itemSameBlock: (T, T) -> Boolean,
        val contentSameBlock: (T, T) -> Boolean,
        val getChangePayload: (T, T) -> Bundle?,
    ) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldData.size
        }

        override fun getNewListSize(): Int {
            return newData.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            if (oldData.isEmpty() || newData.isEmpty()) return false
            return itemSameBlock(oldData[oldItemPosition], newData[newItemPosition])
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            if (oldData.isEmpty() || newData.isEmpty()) return false
            return contentSameBlock(oldData[oldItemPosition], newData[newItemPosition])
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return getChangePayload(oldData[oldItemPosition], newData[newItemPosition])
        }
    }
}