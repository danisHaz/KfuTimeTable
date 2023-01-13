package com.kpfu.kfutimetable.presentation.mainscreen.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.kpfu.kfutimetable.R
import com.kpfu.kfutimetable.commonwidgets.BaseView
import com.kpfu.kfutimetable.commonwidgets.TimeLineView
import kotlin.math.roundToInt

class TimetableView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyleAttr), BaseView<Any> {

    override fun render(state: Any) {
        var prevMemberId = 0
        createTimeViewHolders(HOURS_PER_DAY_COUNT)
    }

    private fun createTimeViewHolders(count: Int) = mutableListOf<TimeViewHolder>().apply {
        for (i in 0 until count) {
            val timeVH = if (i == 0) {
                TimeViewHolder(this@TimetableView)
            } else {
                TimeViewHolder(this@TimetableView, this.last().id)
            }
            timeVH.render(i)
            add(timeVH)
        }
    }

    companion object {
        const val HOURS_PER_DAY_COUNT = 15
    }
}

private class TimeViewHolder(
    private val timeTable: TimetableView,
    private val prevMemberId: Int = timeTable.id
) : BaseView<Int> {

    private val ITEMS_MARGIN =
        timeTable.context.resources.getDimension(R.dimen.calendar_time_marginTop).roundToInt()
    private val HORIZONTAL_MARGIN =
        timeTable.context.resources.getDimension(R.dimen.calendar_time_marginEnd).roundToInt()

    var currentState: Int = -1
    val id
        get() = textView.id
    private val time: String
        get() = "${currentState + 7}:00"
    private lateinit var textView: TextView

    override fun render(state: Int) {
        textView = createTimeView()
        timeTable.addView(textView)
        if (state == 0) {
            textView.updateLayout(state, timeTable.id)
        } else {
            textView.updateLayout(state, prevMemberId)
        }
        currentState = state
        textView.text = time
    }

    private fun createTimeView(): TextView =
        TextView(timeTable.context, null, 0, R.style.TextStyle_Time)
            .apply { id = View.generateViewId() }

    private fun createTimeLine(): TimeLineView =
        TimeLineView(timeTable.context, null, 0, R.style.TimeLineViewStyle)

    private fun TextView.updateMargins() {
        layoutParams =
            (layoutParams as? ViewGroup.MarginLayoutParams ?: ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )).apply {
                setMargins(40, ITEMS_MARGIN, HORIZONTAL_MARGIN, 0)
            }
    }

    private fun TextView.updateConstraints(
        topViewId: Int,
        startViewId: Int,
        isFirst: Boolean = false
    ) {
        ConstraintSet().apply {
            clone(timeTable)
            if (isFirst) {
                connect(this@updateConstraints.id, ConstraintSet.TOP, topViewId, ConstraintSet.TOP)
            } else {
                connect(
                    this@updateConstraints.id,
                    ConstraintSet.TOP,
                    topViewId,
                    ConstraintSet.BOTTOM
                )
            }
            connect(
                this@updateConstraints.id,
                ConstraintSet.START,
                startViewId,
                ConstraintSet.START
            )
            applyTo(timeTable)
        }
    }

    private fun TextView.updateLayout(index: Int, prevMemberId: Int) {
        if (index != 0) {
            updateConstraints(prevMemberId, timeTable.id)
            updateMargins()
        } else {
            updateConstraints(prevMemberId, timeTable.id, true)
        }
    }
}