package com.bitvale.fitnesschallenge.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.bitvale.fitnesschallenge.R

/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 26-Jan-19
 */
data class MainItem(@StringRes val title: Int, @DrawableRes val background: Int, @DrawableRes val icon: Int) {
    companion object {
        fun getItems(): ArrayList<MainItem> {
            val items = arrayListOf<MainItem>()
            items.add(MainItem(R.string.menu_1, R.drawable.bg_menu_orange, R.drawable.ic_menu_barbell))
            items.add(MainItem(R.string.menu_2, R.drawable.bg_menu_blue, R.drawable.ic_menu_chest))
            items.add(MainItem(R.string.menu_3, R.drawable.bg_menu_pink, R.drawable.ic_menu_arms))
            items.add(MainItem(R.string.menu_4, R.drawable.bg_menu_purpule, R.drawable.ic_menu_bench_press))
            items.add(MainItem(R.string.menu_5, R.drawable.bg_menu_orange, R.drawable.ic_menu_running))
            items.add(MainItem(R.string.menu_6, R.drawable.bg_menu_blue, R.drawable.ic_menu_abs))
            return items
        }
    }
}