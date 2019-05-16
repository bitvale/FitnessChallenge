package com.bitvale.fitnesschallenge.widget

import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.getColorOrThrow
import androidx.core.content.res.getDimensionOrThrow
import androidx.core.content.res.getDimensionPixelSizeOrThrow
import androidx.core.content.res.getTextOrThrow
import androidx.core.graphics.withSave
import androidx.core.graphics.withTranslation
import com.bitvale.fitnesschallenge.R
import com.bitvale.fitnesschallenge.commons.lerp
import com.bitvale.fitnesschallenge.commons.textWidth


/**
 * Created by Alexander Kolpakov on 03.09.2018
 */
class ColoredChip @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val textPaint: TextPaint

    private val outlinePaint: Paint
    private val outlineRect = RectF()

    private val padding: Int
    private val rounding: Float

    private val backgroundPaint: Paint

    private val clippingPath = Path()
    private val clippingRect = RectF()
    private val clippingPaint: Paint

    private lateinit var textLayout: StaticLayout
    private var text: CharSequence

    private var progress = 0f
        set(value) {
            if (field != value) {
                field = value
                postInvalidateOnAnimation()
            }
        }

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.ColoredChip,
            0,
            R.style.ColoredChip
        )

        outlinePaint = Paint(ANTI_ALIAS_FLAG).apply {
            color = a.getColorOrThrow(R.styleable.ColoredChip_strokeColor)
            strokeWidth = a.getDimensionOrThrow(R.styleable.ColoredChip_outlineWidth)
            style = Paint.Style.STROKE
        }

        textPaint = TextPaint(ANTI_ALIAS_FLAG).apply {
            color = a.getColorOrThrow(R.styleable.ColoredChip_android_textColor)
            textSize = a.getDimensionOrThrow(R.styleable.ColoredChip_android_textSize)
            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }

        clippingPaint = Paint(ANTI_ALIAS_FLAG).apply {
            color = a.getColorOrThrow(R.styleable.ColoredChip_checked_background)
        }

        backgroundPaint = Paint(ANTI_ALIAS_FLAG).apply {
            color = a.getColor(R.styleable.ColoredChip_default_background, 0)
        }

        padding = a.getDimensionPixelSizeOrThrow(R.styleable.ColoredChip_android_padding)
        rounding = a.getDimensionOrThrow(R.styleable.ColoredChip_radius)
        text = a.getTextOrThrow(R.styleable.ColoredChip_android_text)

        val isChecked = a.getBoolean(R.styleable.ColoredChip_isChecked, false)
        if (isChecked) progress = 1f

        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val nonTextWidth = padding * 2 + outlinePaint.strokeWidth.toInt() * 2

        createTextLayout(MeasureSpec.getSize(widthMeasureSpec))

        val w = nonTextWidth + textLayout.textWidth()
        val h = textLayout.height + padding / 4

        setMeasuredDimension(w, h)

        val strokeWidth = outlinePaint.strokeWidth
        val halfStroke = strokeWidth / 2f

        clippingRect.set(
            strokeWidth,
            strokeWidth,
            w - strokeWidth,
            h - strokeWidth
        )

        clippingPath.addRoundRect(clippingRect, rounding, rounding, Path.Direction.CW)

        outlineRect.set(
            halfStroke,
            halfStroke,
            w - halfStroke,
            h - halfStroke
        )
    }

    private fun createTextLayout(textWidth: Int) {
        textLayout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder.obtain(text, 0, text.length, textPaint, textWidth).build()
        } else {
            StaticLayout(
                text, textPaint, textWidth, Layout.Alignment.ALIGN_NORMAL,
                1f, 0f, true
            )
        }
    }

    override fun onDraw(canvas: Canvas?) {
        val radius = lerp(0f, width.toFloat(), progress)

        canvas?.withSave {
            clipPath(clippingPath)
            drawCircle(width / 2f, height / 2f, radius, clippingPaint)
            drawRect(clippingRect, backgroundPaint)
        }

        canvas?.withTranslation(
            x = outlinePaint.strokeWidth + padding,
            y = (height - textLayout.height) / 2f - padding / 6
        ) {
            textLayout.draw(canvas)
        }
    }
}