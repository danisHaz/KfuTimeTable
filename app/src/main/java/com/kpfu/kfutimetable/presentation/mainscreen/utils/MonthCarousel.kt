package com.kpfu.kfutimetable.presentation.mainscreen.utils

import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.core.animation.doOnEnd
import com.kpfu.kfutimetable.utils.toString
import java.time.Month

class MonthCarousel(
    var monthNames: List<Month>,
    monthHolder: TextView,
    private val monthNamesLocalized: List<String>?,
    private val animationInterpolator: TimeInterpolator = AccelerateDecelerateInterpolator()
) {
    private val hasNextMonth
        get() = currentMonthPos + 1 < monthNames.size
    private val hasPrevMonth
        get() = currentMonthPos != 0

    var onMonthChangeListener: (Month) -> Unit = {}
    val currentMonth: Month
        get() = monthNames[currentMonthPos]

    var monthHolder: TextView = monthHolder
        set(value) {
            field = value
            monthHolder.text = currentMonth.toString(monthNamesLocalized)
        }

    private var currentMonthPos = 0

    private val animTransitionToLeft: Float
        get() = -monthHolder.width.toFloat()
    private val animTransitionToRight: Float
        get() = monthHolder.width.toFloat()

    init {
        if (monthNames.isNotEmpty()) {
            this.monthHolder.text = monthNames.first().toString(monthNamesLocalized)
        }
    }

    fun nextMonth() {
        if (!hasNextMonth) {
            return
        }

        currentMonthPos++
        onMonthChangeListener(monthNames[currentMonthPos])

        animateText(0f, animTransitionToLeft) {
            monthHolder.text = monthNames[currentMonthPos].toString(monthNamesLocalized)
            animateText(animTransitionToRight, 0f)
        }
    }

    fun prevMonth() {
        if (!hasPrevMonth) {
            return
        }

        currentMonthPos--
        onMonthChangeListener(monthNames[currentMonthPos])

        animateText(0f, animTransitionToRight) {
            monthHolder.text = monthNames[currentMonthPos].toString(monthNamesLocalized)
            animateText(animTransitionToLeft, 0f)
        }
    }

    private fun animateText(
        transitionFrom: Float,
        transitionTo: Float,
        doOnEndCallback: () -> Unit = {}
    ) {
        ObjectAnimator.ofFloat(
            monthHolder,
            "translationX", transitionFrom, transitionTo
        ).apply {
            duration = ANIMATION_DURATION
            interpolator = animationInterpolator
            doOnEnd { doOnEndCallback() }
            start()
        }
    }

    companion object {
        const val ANIMATION_DURATION: Long = 500
    }
}