package com.kpfu.kfutimetable.commonwidgets

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.kpfu.kfutimetable.R
import com.kpfu.kfutimetable.databinding.ViewDayItemBinding

class DayItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr), BaseView<DayItemView.State> {

    private val binding = ViewDayItemBinding.inflate(LayoutInflater.from(context), this)
    lateinit var currentState: State

    fun updateOnClick(newOnClick: () -> Unit) = setOnClickListener {
        newOnClick.invoke()
    }

    var isChecked: Boolean = false
        set(value) {
            field = value
            changeCheckedState()
        }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DayItemView)
        try {
            setStyle(typedArray)
        } finally {
            typedArray.recycle()
        }
    }

    private fun changeCheckedState() {
        binding.root.background = if (isChecked) {
            ContextCompat.getDrawable(
                context,
                R.drawable.day_item_view_background_checked
            )
        } else {
            ContextCompat.getDrawable(
                context,
                R.drawable.day_item_view_background
            )
        }
    }

    private fun setStyle(styleArray: TypedArray) = with(binding) {

        background = ContextCompat.getDrawable(
            context,
            R.drawable.day_item_view_background
        )
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
        state.isChecked?.let { isChecked = it }

        currentState = state
    }

    fun render(update: State.() -> State) = render(update(currentState))

    data class State(
        val date: String,
        val dayOfWeek: String,
        val isChecked: Boolean? = null
    )

    private companion object {
        const val DATE_NUMBER_COLOR = Color.WHITE
        const val DAY_OF_WEEK_COLOR = Color.WHITE
    }
}