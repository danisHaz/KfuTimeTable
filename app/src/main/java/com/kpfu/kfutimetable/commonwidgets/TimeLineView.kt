package com.kpfu.kfutimetable.commonwidgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.kpfu.kfutimetable.R
import com.kpfu.kfutimetable.utils.dpToPxF
import com.kpfu.kfutimetable.utils.fromAttr

class TimeLineView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttrs: Int = 0,
    defStyleRes: Int = 0,
) : View(context, attributeSet, defStyleAttrs, defStyleRes), BaseView<TimeLineView.State> {

    private val paint: Paint = Paint()

    init {
        setStyle()
    }

    override fun render(state: State) {
        val color = context.fromAttr(state.colorAttr) ?: BASE_COLOR
        paint.color = color
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawLine(
            0f,
            height.toFloat() / 2,
            width.toFloat(),
            height.toFloat() / 2,
            paint,
        )
        canvas?.drawOval(
            0f,
            height.toFloat() / 2 - DEFAULT_CIRCLE_DIAMETER.dpToPxF,
            DEFAULT_CIRCLE_DIAMETER.dpToPxF * 2,
            height.toFloat() / 2 + DEFAULT_CIRCLE_DIAMETER.dpToPxF,
            paint
        )
    }

    private fun setStyle() {
        paint.color = ContextCompat.getColor(context, BASE_COLOR)
        paint.style = Paint.Style.FILL_AND_STROKE
    }

    enum class State(val colorAttr: Int) {
        CURRENT_TIME(R.attr.timeLineViewAccentColor),
        BASE_TIME(R.attr.timeLineViewBaseColor)
    }

    companion object {
        const val BASE_COLOR = R.color.timeLineColor_base
        const val DEFAULT_CIRCLE_DIAMETER = 2
    }
}