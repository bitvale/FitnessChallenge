package com.bitvale.fitnesschallenge.ui.home

import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bitvale.fitnesschallenge.R
import com.bitvale.fitnesschallenge.commons.lerp
import com.bitvale.fitnesschallenge.commons.withEndActionOnce
import kotlin.math.abs


/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 27-Jan-19
 */
class AnimatedLayoutManager constructor(
    private val context: Context
) : LinearLayoutManager(context) {

    companion object {
        const val DY_COEFFICIENT = 2
        const val ANIMATION_OFFSET = 50L
    }

    private var animator: ValueAnimator? = null
    private var previousDy = 0
    private var currentDy = 0
    private var animatedDy = 0
    private var isAnimated = false

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State?): Int {
        if (childCount == 0) return 0
        val legal = super.scrollVerticallyBy(dy, recycler, state)
        calculateDy(dy)
        updateViews()
        return legal
    }

    private fun calculateDy(dy: Int) {
        if (findLastCompletelyVisibleItemPosition() == itemCount - 1 && dy < 0) {
            if (currentDy > 0) currentDy += dy
            else currentDy = 0
        }
        if (findFirstVisibleItemPosition() == 0 && dy > 0) {
            if (currentDy > 0) currentDy -= dy
            else currentDy = 0
        }
        val topView = getChildAt(0) as View
        val bottomView = getChildAt(childCount - 1) as View
        if (getPosition(topView) == 0 && dy <= 0 && topView.top >= topView.marginTop + paddingTop) {
            currentDy += abs(dy / DY_COEFFICIENT)
            offsetChildrenVertical(-dy / DY_COEFFICIENT)
        } else {
            if (getPosition(bottomView) == itemCount - 1 && dy >= 0 && bottomView.bottom <= height - bottomView.marginBottom - paddingBottom) {
                offsetChildrenVertical(-dy / DY_COEFFICIENT)
                currentDy += abs(dy / DY_COEFFICIENT)
            }
        }
    }

    private fun updateViews() {
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            view?.let {
                val value = ((paddingTop - it.top) / it.height.toFloat())
                it.alpha = 1f - value
                var scale = 1f - value / 20f
                if (scale > 1) scale = 1f
                it.scaleX = scale
                it.scaleY = scale
            }
        }
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        if (state == RecyclerView.SCROLL_STATE_IDLE) {

            if (getPosition(getChildAt(childCount - 1) as View) == itemCount - 1 && currentDy > 0) {
                animateOffset(false)
            } else {
                if (getPosition(getChildAt(0) as View) == 0 && currentDy > 0) {
                    animateOffset(true)
                }
            }
        }
    }

    private fun animateOffset(top: Boolean) {
        val coefficient = if (top) -1 else 1
        if (animator?.isRunning == true) {
            animatedDy += currentDy - previousDy
            animator?.cancel()
        } else {
            animatedDy = currentDy
        }

        animator = ValueAnimator.ofInt(0, animatedDy).apply {
            addUpdateListener {
                val value = it.animatedValue as Int
                val offset = value - previousDy
                previousDy = value
                offsetChildrenVertical(offset * coefficient)
                updateViews()
            }
            doOnStart {
                isAnimated = true
                currentDy = 0
            }
            doOnEnd {
                isAnimated = false
                previousDy = 0
            }
            duration = (lerp(800f, 400f, currentDy.toFloat() / 1000)).toLong()
            start()
        }
    }

    fun animateItemsOut() = animate(R.anim.item_animation_out)

    fun animateItemsIn() = animate(R.anim.item_animation_in)

    private fun animate(@AnimRes animationId: Int) {
        var startOffset = 0L
        for (i in childCount - 1 downTo 0) {
            val set = AnimationUtils.loadAnimation(context, animationId)
            set.startOffset = startOffset
            getChildAt(i)?.startAnimation(set)
            startOffset += ANIMATION_OFFSET
            set.withEndActionOnce { updateViews() }
        }
    }
}