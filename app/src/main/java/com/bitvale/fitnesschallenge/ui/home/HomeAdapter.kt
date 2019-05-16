package com.bitvale.fitnesschallenge.ui.home

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import androidx.core.content.ContextCompat
import com.bitvale.fitnesschallenge.R
import com.bitvale.fitnesschallenge.model.MainItem
import com.skycodetech.codingquiz.ui.adapter.BaseAdapter
import com.skycodetech.codingquiz.ui.adapter.BaseViewHolder
import kotlinx.android.synthetic.main.item_main.*

/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 26-Jan-19
 */
class HomeAdapter(
    override var dataSet: ArrayList<MainItem>
) : BaseAdapter<MainItem, HomeAdapter.MenuViewHolder>() {

    override fun createViewHolder(view: View): MenuViewHolder = MenuViewHolder(view)

    override fun getLayoutIdForPosition(position: Int): Int = R.layout.item_main

    inner class MenuViewHolder(view: View) : BaseViewHolder<MainItem>(view) {

        override fun bind(item: MainItem) {
            container.setBackgroundResource(item.background)
            icon.setImageDrawable(ContextCompat.getDrawable(icon.context, item.icon))
            title.setText(item.title)
            container.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline?) {
                    outline?.setRoundRect(
                        0,
                        0,
                        view.width,
                        view.height,
                        container.context.resources.getDimension(R.dimen.space_normal)
                    )
                }
            }
            container.clipToOutline = true
        }
    }
}