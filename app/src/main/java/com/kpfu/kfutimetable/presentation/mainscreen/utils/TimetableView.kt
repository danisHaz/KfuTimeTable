package com.kpfu.kfutimetable.presentation.mainscreen.utils

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.kpfu.kfutimetable.R
import com.kpfu.kfutimetable.commonwidgets.BaseView
import kotlin.math.roundToInt

class TimetableView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyleAttr), BaseView<Any> {

    private val ITEMS_MARGIN = resources.getDimension(R.dimen.calendar_time_marginTop).roundToInt()
    private val HORIZONTAL_MARGIN =
        resources.getDimension(R.dimen.calendar_time_marginEnd).roundToInt()

    override fun render(state: Any) {
//        generateTimeViews().forEach { addView(it) }
    }

    private fun generateTimeViews(): List<TextView> = mutableListOf<TextView>().apply {
        for (i in 0 until 2) {
            val textView = TextView(context, null, 0, R.style.TextStyle_Time).apply {
                text = "${i + 7}:00"
                id = i
                if (i != 0) {
                    updateConstraints(i - 1, this@TimetableView.id)
                    updateMargins()
                }
            }

            add(textView)
        }
    }

    private fun TextView.updateMargins() {
        layoutParams = (layoutParams as? MarginLayoutParams ?: MarginLayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )).apply {
            setMargins(0, ITEMS_MARGIN, HORIZONTAL_MARGIN, 0)
        }
    }

    private fun TextView.updateConstraints(topViewId: Int, startViewId: Int) {
        ConstraintSet().apply {
            clone(this@TimetableView)
            connect(this@updateConstraints.id, ConstraintSet.TOP, topViewId, ConstraintSet.BOTTOM)
            connect(this@updateConstraints.id, ConstraintSet.START, startViewId, ConstraintSet.START)
            applyTo(this@TimetableView)
        }
    }

    companion object {
        const val HOURS_PER_DAY_COUNT = 15
    }
}