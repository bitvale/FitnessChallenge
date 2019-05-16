package com.bitvale.fitnesschallenge.widget

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat
import com.bitvale.fitnesschallenge.R


/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 14-Feb-19
 */
class ArcView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        const val KEY_EXPANDED = "expanded"
        const val STATE = "state"
        const val ANIMATION_DURATION = 450L
    }

    private val backgroundStartColor = ContextCompat.getColor(context, R.color.bg_circle_view)
    private val backgroundEndColor = ContextCompat.getColor(context, R.color.bg_circle_view_animated)

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.bg_circle_view)
    }

    private val foregroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.TRANSPARENT
    }

    private var foregroundColor = 0
        set(value) {
            field = value
            foregroundPaint.color = value
            invalidate()
        }

    private var animatorSet: AnimatorSet? = AnimatorSet()
    private var isExpanded = false

    private val backgroundRect = RectF(0f, 0f, 0f, 0f)
    private val animatedEndRect = RectF(0f, 0f, 0f, 0f)
    private val animatedStartRect = RectF(0f, 0f, 0f, 0f)
    private val animatedRect = AnimatedRectF(0f, 0f, 0f, 0f)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        backgroundRect.left = -width / 2f
        backgroundRect.top = -width.toFloat()
        backgroundRect.right = width * 1.5f
        backgroundRect.bottom = width / 1.5f

        animatedEndRect.left = -width / 5f
        animatedEndRect.top = -width.toFloat()
        animatedEndRect.right = width.toFloat() * 1.2f
        animatedEndRect.bottom = width / 1.55f

        animatedStartRect.left = width / 2f
        animatedStartRect.top = 0f
        animatedStartRect.right = width / 2f
        animatedStartRect.bottom = 0f

        animatedRect.set(animatedStartRect)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawOval(backgroundRect, backgroundPaint)
        canvas?.drawOval(animatedRect, foregroundPaint)
    }

    fun startAnimation() {
        animatorSet?.cancel()
        animatorSet = AnimatorSet()

        var toRect = animatedEndRect
        var toForegroundColor = Color.WHITE
        var toBackgroundColor = backgroundEndColor

        if (isExpanded) {
            toRect = backgroundRect
            toForegroundColor = Color.TRANSPARENT
            toBackgroundColor = backgroundStartColor
        }

        val animateLeft = ObjectAnimator.ofFloat(animatedRect, "left", animatedRect.left, toRect.left)
        val animateTop = ObjectAnimator.ofFloat(animatedRect, "top", animatedRect.top, toRect.top)
        val animateRight = ObjectAnimator.ofFloat(animatedRect, "right", animatedRect.right, toRect.right)
        val animateBottom = ObjectAnimator.ofFloat(animatedRect, "bottom", animatedRect.bottom, toRect.bottom)

        val foregroundColorAnimator = ValueAnimator().apply {
            addUpdateListener {
                foregroundColor = it.animatedValue as Int
            }

            setIntValues(foregroundPaint.color, toForegroundColor)
            setEvaluator(ArgbEvaluator())
        }

        val backgroundColorAnimator = ValueAnimator().apply {
            addUpdateListener {
                backgroundPaint.color = it.animatedValue as Int
            }

            setIntValues(backgroundPaint.color, toBackgroundColor)
            setEvaluator(ArgbEvaluator())
        }

        animatorSet?.apply {
            doOnStart {
                isExpanded = !isExpanded
            }
            doOnEnd {
                if (!isExpanded) {
                    animatedRect.set(animatedStartRect)
                }
            }
            playTogether(
                animateLeft, animateTop, animateRight, animateBottom, foregroundColorAnimator,
                backgroundColorAnimator
            )

            duration = ANIMATION_DURATION
            start()
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        return Bundle().apply {
            putBoolean(KEY_EXPANDED, isExpanded)
            putParcelable(STATE, super.onSaveInstanceState())
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            super.onRestoreInstanceState(state.getParcelable(STATE))
            isExpanded = state.getBoolean(KEY_EXPANDED)
            if (isExpanded) forceExpand()
        }
    }

    private fun forceExpand() {
        backgroundPaint.color = Color.WHITE
        animatedRect.set(animatedEndRect)
        isExpanded = true
        invalidate()
    }

    class AnimatedRectF(left: Float, top: Float, right: Float, bottom: Float) : RectF(left, top, right, bottom) {
        fun setTop(top: Float) {
            this.top = top
        }

        fun setBottom(bottom: Float) {
            this.bottom = bottom
        }

        fun setRight(right: Float) {
            this.right = right
        }

        fun setLeft(left: Float) {
            this.left = left
        }
    }
}