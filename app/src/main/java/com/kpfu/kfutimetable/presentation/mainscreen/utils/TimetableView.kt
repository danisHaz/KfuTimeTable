package com.kpfu.kfutimetable.presentation.mainscreen.utils

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.forEach
import com.kpfu.kfutimetable.R
import com.kpfu.kfutimetable.commonwidgets.BaseView
import com.kpfu.kfutimetable.commonwidgets.SubjectView
import com.kpfu.kfutimetable.commonwidgets.TimeLineView
import kotlinx.android.synthetic.main.fragment_calendar.view.*
import java.util.*
import kotlin.math.roundToInt

class TimetableView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyleAttr), BaseView<List<SubjectView.State>> {

    private var timeViewHoldersList: List<TimeViewHolder> = listOf()

    init {
        initialize()
    }

    override fun render(state: List<SubjectView.State>) {
        state.forEach { subject ->
            val startTime = subject.startTime.clone()
            val endHour: Calendar
            with(subject.startTime.clone() as Calendar) {
                add(Calendar.HOUR_OF_DAY, 1)
                add(Calendar.MINUTE, 30)
                endHour = this
            }


        }
    }

    private fun initialize() {
        timeViewHoldersList = createTimeViewHolders(HOURS_PER_DAY_COUNT).also {
            it.forEachIndexed { index, timeVH ->
                timeVH.bindToTimeTable()
                timeVH.render(index)
            }
        }
    }

    private fun createTimeViewHolders(count: Int) = mutableListOf<TimeViewHolder>().apply {
        for (i in 0 until count) {
            val timeVH = if (i == 0) {
                TimeViewHolder(this@TimetableView)
            } else {
                TimeViewHolder(this@TimetableView, this.last().id)
            }
            add(timeVH)
        }
    }

    private fun addSubject(
        startTime: Calendar,
        endTime: Calendar,
        subjectState: SubjectView.State
    ) {
        val startIndex = startTime.get(Calendar.HOUR_OF_DAY) - HOURS_START_DAY_FROM
        val endIndex = endTime.get(Calendar.HOUR_OF_DAY) - HOURS_START_DAY_FROM

        (startIndex..endIndex).forEach {
            timeViewHoldersList[it]
        }

        val hourHeightInDp = resources.getDimension(R.dimen.timeTableView_marginTop)

        val subjectView = SubjectView(context, null, 0).apply {
            layoutParams = ViewGroup.LayoutParams(
                LayoutParams.MATCH_PARENT,
                (hourHeightInDp * LESSON_DURATION_HOURS).roundToInt()
            )
        }
    }

    companion object {
        const val HOURS_START_DAY_FROM = 7
        const val HOURS_PER_DAY_COUNT = 15
        const val LESSON_DURATION_HOURS = 1.5
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
    val id get() = textView.id
    private val textView: TextView = createTimeView()
    private val timeLineView: TimeLineView = createTimeLine()

    private val time: String get() = "${currentState + 7}:00"
    private val isCurrentTime
        get() = time.split(":")[0] ==
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString()

    override fun render(state: Int) {
        currentState = state

        textView.apply {
            updateLayout(state, timeTable.id, prevMemberId)
            text = time
        }
        timeLineView.updateLayout()

        if (isCurrentTime) {
            TimeLineView.State.CURRENT_TIME
        } else {
            TimeLineView.State.BASE_TIME
        }.also { timeLineView.render(it) }
    }

    fun bindToTimeTable() {
        timeTable.addView(textView)
        timeTable.addView(timeLineView)
    }

    private fun createTimeView(): TextView =
        TextView(timeTable.context, null, 0, R.style.TextStyle_Time)
            .apply {
                id = View.generateViewId()
                layoutParams = ViewGroup.LayoutParams(
                    timeTable.context.resources.getDimension(
                        R.dimen.timeTableView_textView_layoutWidth
                    ).roundToInt(),
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                gravity = Gravity.CENTER_HORIZONTAL
            }

    private fun createTimeLine(): TimeLineView =
        TimeLineView(timeTable.context, null, 0, R.style.TimeLineViewStyle)
            .apply {
                id = View.generateViewId()
                layoutParams = ViewGroup.LayoutParams(
                    0,
                    timeTable.context.resources.getDimension(
                        R.dimen.timeLineView_layoutHeight_default
                    ).roundToInt()
                )
            }

    private fun TextView.updateMargins() {
        layoutParams =
            (layoutParams as? ViewGroup.MarginLayoutParams ?: ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )).apply {
                setMargins(0, ITEMS_MARGIN, 0, 0)
            }
    }

    private fun TimeLineView.updateMargins() {
        layoutParams =
            (layoutParams as? ViewGroup.MarginLayoutParams ?: ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )).apply {
                marginStart = HORIZONTAL_MARGIN
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

    private fun TimeLineView.updateConstraints(
        topViewId: Int,
        startViewId: Int,
    ) {
        ConstraintSet().apply {
            clone(timeTable)
            connect(
                this@updateConstraints.id,
                ConstraintSet.TOP,
                topViewId,
                ConstraintSet.TOP
            )

            connect(
                this@updateConstraints.id,
                ConstraintSet.BOTTOM,
                topViewId,
                ConstraintSet.BOTTOM
            )

            connect(
                this@updateConstraints.id,
                ConstraintSet.START,
                startViewId,
                ConstraintSet.END
            )

            applyTo(timeTable)
        }
    }

    private fun TextView.updateLayout(index: Int, parentId: Int, prevMemberId: Int) {
        if (index != 0) {
            updateConstraints(prevMemberId, parentId)
            updateMargins()
        } else {
            updateConstraints(prevMemberId, parentId, true)
        }
    }

    private fun TimeLineView.updateLayout() {
        updateMargins()
        updateConstraints(textView.id, textView.id)
    }
}