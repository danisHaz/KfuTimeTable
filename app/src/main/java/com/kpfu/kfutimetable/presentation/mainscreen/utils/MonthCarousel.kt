package com.kpfu.kfutimetable.presentation.mainscreen.utils

import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.core.view.children

class MonthCarousel(
    private val monthNames: List<String>,
    container: LinearLayout,
    private val animationInterpolator: TimeInterpolator = AccelerateDecelerateInterpolator()
) {
    val hasNextMonth
        get() = currentMonthPos + 1 < monthNames.size
    val hasPrevMonth
        get() = currentMonthPos != 0

    private var monthHolder: TextView
    private var currentMonthPos = 0

    private val animTransitionToLeft: Float
        get() = -monthHolder.width.toFloat()
    private val animTransitionToRight: Float
        get() = monthHolder.width.toFloat()

    init {
        container.children.map { it as TextView }.let {
            monthHolder = it.first()
        }
        monthHolder.text = monthNames.first()
    }

    fun nextMonth() {
        if (!hasNextMonth) {
            return
        }

        currentMonthPos++
        animateText(0f, animTransitionToLeft) {
            monthHolder.text = monthNames[currentMonthPos]
            animateText(animTransitionToRight, 0f)
        }
    }

    fun prevMonth() {
        if (!hasPrevMonth) {
            return
        }

        currentMonthPos--
        animateText(0f, animTransitionToRight) {
            monthHolder.text = monthNames[currentMonthPos]
            animateText(animTransitionToLeft, 0f)
        }
    }

    private fun animateText(transitionFrom: Float, transitionTo: Float, doOnEndCallback: () -> Unit = {}) {
        ObjectAnimator.ofFloat(
            monthHolder,
            "translationX", transitionFrom, transitionTo
        ).apply {
            duration = 500
            interpolator = animationInterpolator
            doOnEnd { doOnEndCallback() }
            start()
        }
    }
}