package com.kpfu.kfutimetable.commonwidgets

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.kpfu.kfutimetable.R

class DayItemView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr), BaseView<DayItemView.State> {

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DayItemView)
        try {

        } finally {
            typedArray.recycle()
        }
    }

    private fun setStyle(typedArray: TypedArray) {

    }

    override fun render(state: State) {

    }

    data class State(
        val date: String,
        val dayOfWeek: String,
    )

}