package com.bitvale.fitnesschallenge.widget

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.bitvale.fitnesschallenge.R
import com.bitvale.fitnesschallenge.commons.getStatusBarHeight
import timber.log.Timber

/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 28-Jan-19
 */
class MenuImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    companion object {
        const val KEY_STATE = "menu_state"
        const val STATE = "state"
    }

    private var forward: AnimatedVectorDrawableCompat? = null
    private var reverse: AnimatedVectorDrawableCompat? = null
    private var showingMenu: Boolean = true

    init {
        showingMenu = true
        forward = AnimatedVectorDrawableCompat.create(context, R.drawable.menu_avd_anim)
        reverse = AnimatedVectorDrawableCompat.create(context, R.drawable.menu_avd_anim_reverse)
        setImageDrawable(forward)
    }

    private fun forceBack() {
        setImageDrawable(forward)
        showingMenu = true
    }

    fun morph() {
        val drawable = if (showingMenu) forward else reverse
        setImageDrawable(drawable)
        showingMenu = !showingMenu
        drawable?.start()
    }

    override fun onSaveInstanceState(): Parcelable? {
        return Bundle().apply {
            putBoolean(KEY_STATE, showingMenu)
            putParcelable(STATE, super.onSaveInstanceState())
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            super.onRestoreInstanceState(state.getParcelable(STATE))
            showingMenu = state.getBoolean(KEY_STATE)
            if(!showingMenu) forceBack()
        }
    }
}