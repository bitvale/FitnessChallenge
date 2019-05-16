package com.bitvale.fitnesschallenge.widget

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat
import androidx.core.content.res.getDimensionOrThrow
import com.bitvale.fitnesschallenge.R

/**
 * Created by Alexander Kolpakov (jquickapp@gmail.com) on 18-Feb-19
 */
class PulseView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val circleColor = ContextCompat.getColor(context, R.color.pulse_bg_color)
    private val pulseColor = ContextCompat.getColor(context, R.color.pulse_color)
    private val pulseColor2 = ContextCompat.getColor(context, R.color.pulse_color2)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = pulseColor
    }

    private var radius = 0f
    private var centerX = 0f
    private var centerY = 0f

    private var animatorSet: AnimatorSet? = null

    private var isPulsing = false

    private var animatedRadius = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.PulseView)
        animatedRadius = a.getDimensionOrThrow(R.styleable.PulseView_radius) / 2f
        a.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        var r = radius
        if (isPulsing) {
            r = animatedRadius
            canvas?.drawCircle(centerX, centerY, r, paint)
        } else {
            paint.color = pulseColor
            canvas?.drawCircle(centerX, centerY, r, paint)
            paint.color = pulseColor2
            r = radius - (radius - animatedRadius) / 1.8f
            canvas?.drawCircle(centerX, centerY, r, paint)

        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w / 2f
        centerY = h / 2f
        radius = Math.min(w, h) / 2f
    }

    fun startPulse() {
        if (animatorSet?.isRunning == true) return
        animatorSet?.cancel()

        val radiusAnimator = ValueAnimator.ofFloat(animatedRadius, radius).apply {
            addUpdateListener {
                animatedRadius = it.animatedValue as Float
            }
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
        }

        val colorAnimator = ValueAnimator().apply {
            addUpdateListener {
                paint.color = it.animatedValue as Int
            }
            setIntValues(pulseColor, circleColor)
            setEvaluator(ArgbEvaluator())

            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
        }

        animatorSet = AnimatorSet().apply {
            playTogether(radiusAnimator, colorAnimator)
            doOnStart { isPulsing = true }
            doOnEnd { isPulsing = false }
            startDelay = 1200
            duration = 2000
            start()
        }

    }
}