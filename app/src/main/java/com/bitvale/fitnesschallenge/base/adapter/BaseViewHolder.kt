package com.skycodetech.codingquiz.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

/**
 * Created by Alexander Kolpakov on 03.08.2018
 */
abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {

    override val containerView: View?
        get() = itemView

    abstract fun bind(item: T)
}