package com.kpfu.kfutimetable.commonwidgets

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.kpfu.kfutimetable.R
import com.kpfu.kfutimetable.databinding.ViewDayItemBinding
import com.kpfu.kfutimetable.utils.dpToPxF

class DayItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr), BaseView<DayItemView.State> {

    private val binding = ViewDayItemBinding.inflate(LayoutInflater.from(context), this)
    lateinit var currentState: State

    var onClick: OnClickListener? = null
    fun updateOnClick(newOnClick: () -> Unit) {
        onClick = OnClickListener { newOnClick.invoke() }
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DayItemView)
        try {
            setStyle(typedArray)
        } finally {
            typedArray.recycle()
        }

        setOnClickListener(onClick)
    }

    private fun setStyle(styleArray: TypedArray) = with(binding) {

        val backgroundColor = BACKGROUND_COLOR
        // TODO: pass here different color states when pressed or not
        background = (ContextCompat.getDrawable(
            context,
            R.drawable.day_item_view_background
        ) as GradientDrawable).apply {
            color = ColorStateList.valueOf(backgroundColor)
        }
        val dateNumberColor = styleArray.getColor(
            R.styleable.DayItemView_dayItemDateNumberColor,
            DATE_NUMBER_COLOR
        )

        val dayOfWeekColor = styleArray.getColor(
            R.styleable.DayItemView_dayItemDayOfWeekColor,
            DAY_OF_WEEK_COLOR
        )

        date.setTextColor(dateNumberColor)
        dayOfWeek.setTextColor(dayOfWeekColor)
    }

    override fun render(state: State) {
        with(binding) {
            dayOfWeek.text = state.dayOfWeek
            date.text = state.date
        }
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