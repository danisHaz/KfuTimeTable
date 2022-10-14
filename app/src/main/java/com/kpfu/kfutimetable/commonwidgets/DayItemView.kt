package com.kpfu.kfutimetable.commonwidgets

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.kpfu.kfutimetable.R
import com.kpfu.kfutimetable.databinding.ViewDayItemBinding
import com.kpfu.kfutimetable.utils.dpToPxF

class DayItemView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr), BaseView<DayItemView.State> {

    private val binding = ViewDayItemBinding.inflate(LayoutInflater.from(context), this)

    private lateinit var currentState: State

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DayItemView)
        try {
            setStyle(typedArray)
        } finally {
            typedArray.recycle()
        }
    }

    private fun setStyle(styleArray: TypedArray) = with(binding) {
        val dateNumberColor = styleArray.getColor(
            R.styleable.DayItemView_dayItemDateNumberColor,
            DATE_NUMBER_COLOR
        )

        val dayOfWeekColor = styleArray.getColor(
            R.styleable.DayItemView_dayItemDayOfWeekColor,
            DAY_OF_WEEK_COLOR
        )

        val background = styleArray.getColor(
            R.styleable.DayItemView_dayItemBackgroundColor,
            BACKGROUND_COLOR
        )

        val cornerRadius = styleArray.getDimension(
            R.styleable.DayItemView_dayItemCornerRadius,
            CORNER_RADIUS
        )

        date.setTextColor(dateNumberColor)
        dayOfWeek.setTextColor(dayOfWeekColor)

        // TODO: pass here different color states when pressed or not
        root.background = GradientDrawable().apply {
            setCornerRadius(cornerRadius)
            color = ColorStateList.valueOf(background)
        }
    }

    override fun render(state: State) {

        currentState = state
    }

    fun render(update: State.() -> State) = render(update(currentState))

    data class State(
        val date: String,
        val dayOfWeek: String,
    )

    private companion object {
        const val DATE_NUMBER_COLOR = Color.WHITE
        const val DAY_OF_WEEK_COLOR = Color.WHITE
        val BACKGROUND_COLOR = Color.parseColor("#004F8C")
        val CORNER_RADIUS = 16.dpToPxF
    }
}