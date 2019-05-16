package com.skycodetech.codingquiz.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bitvale.fitnesschallenge.commons.inflate


abstract class BaseAdapter<T, VH : BaseViewHolder<T>> : RecyclerView.Adapter<VH>() {

    abstract var dataSet: ArrayList<T>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = parent.inflate(viewType)
        return createViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        val obj = getObjectForPosition(position)
        viewHolder.bind(obj)
    }

    override fun getItemViewType(position: Int) = getLayoutIdForPosition(position)

    override fun getItemCount() = dataSet.size

    private fun getObjectForPosition(position: Int): T = dataSet[position]

    abstract fun createViewHolder(view: View): VH

    @LayoutRes
    abstract fun getLayoutIdForPosition(position: Int): Int

    open fun replaceData(data: List<T>) {
        dataSet.clear()
        dataSet.addAll(data)
        this.notifyDataSetChanged()
    }
}
